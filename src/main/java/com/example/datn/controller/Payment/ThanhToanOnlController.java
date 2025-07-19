package com.example.datn.controller.Payment;

import com.example.datn.entity.*;
import com.example.datn.entity.HoaDon.HoaDon;
import com.example.datn.entity.HoaDon.HoaDonChiTiet;
import com.example.datn.entity.HoaDon.LichSuHoaDon;
import com.example.datn.entity.SanPham.SanPhamChiTiet;
import com.example.datn.repository.DiaChiRepo;
import com.example.datn.repository.HoaDonRepo.HoaDonChiTietRepo;
import com.example.datn.repository.HoaDonRepo.HoaDonRepo;
import com.example.datn.repository.HoaDonRepo.LichSuHoaDonRepo;
import com.example.datn.repository.KhachHangRepo.KhachHangRepo;
import com.example.datn.repository.PhieuGiamGiaKhachHangRepo;
import com.example.datn.repository.PhieuGiamGiaRepo;
import com.example.datn.repository.SanPhamRepo.SanPhamChiTietRepo;
import com.example.datn.service.PhieuGiamGiaSercvice;
import com.example.datn.service.gioHangService;
import com.example.datn.service.MomoService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/checkout")
public class ThanhToanOnlController {
    private static final Logger logger = LoggerFactory.getLogger(ThanhToanOnlController.class);
    @Autowired
    private gioHangService gioHangService;

    @Autowired
    private HoaDonRepo hoaDonRepo;

    @Autowired
    private HoaDonChiTietRepo hoaDonChiTietRepo;

    @Autowired
    private LichSuHoaDonRepo lichSuHoaDonRepo;

    @Autowired
    private KhachHangRepo khachHangRepo;

    @Autowired
    private DiaChiRepo diaChiRepo;

    @Autowired
    private PhieuGiamGiaRepo phieuGiamGiaRepo;

    @Autowired
    private PhieuGiamGiaSercvice phieuGiamGiaService;
    @Autowired
    private PhieuGiamGiaKhachHangRepo phieuGiamGiaKhachHangRepo;
    
    @Autowired
    private MomoService momoService;
    
    @Autowired
    private SanPhamChiTietRepo sanPhamChiTietRepo;

    // Hiển thị trang checkout
    @GetMapping("")
    public String checkout(HttpSession session, Model model) {
        // ✅ Sửa: Kiểm tra đăng nhập với key đúng
        TaiKhoan currentUser = (TaiKhoan) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        // Kiểm tra giỏ hàng có sản phẩm không
        Map<String, Object> cartInfo = gioHangService.getCartInfo(session);

        if ((Boolean) cartInfo.get("isEmpty")) {
            return "redirect:/cart";
        }

        return "user/thongtinGiaoHang";
    }

    // API lấy thông tin giỏ hàng cho checkout
    @GetMapping("/cart-info")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getCartInfo(HttpSession session) {
        Map<String, Object> cartInfo = gioHangService.getCartInfo(session);
        return ResponseEntity.ok(cartInfo);
    }

    // Xử lý đặt hàng
    @PostMapping("/process")
    @ResponseBody
    @Transactional
    public ResponseEntity<Map<String, Object>> processOrder(
            @RequestParam String hoTen,
            @RequestParam String soDienThoai,
            @RequestParam String tinhThanh,
            @RequestParam String quanHuyen,
            @RequestParam String phuongXa,
            @RequestParam String diaChiChiTiet,
            @RequestParam String phuongThucThanhToan,
            @RequestParam(required = false) String ghiChu,
            @RequestParam(required = false) Long voucherId,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();
        try {
            TaiKhoan currentUser = (TaiKhoan) session.getAttribute("currentUser");
            if (currentUser == null) {
                response.put("success", false);
                response.put("message", "Vui lòng đăng nhập để đặt hàng");
                return ResponseEntity.ok(response);
            }

            Optional<KhachHang> khachHangOpt = khachHangRepo.findByTaiKhoan(currentUser.getId());
            if (!khachHangOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Không tìm thấy thông tin khách hàng");
                return ResponseEntity.ok(response);
            }

            KhachHang khachHang = khachHangOpt.get();
            Map<String, Object> cartInfo = gioHangService.getCartInfo(session);
            if ((Boolean) cartInfo.get("isEmpty")) {
                response.put("success", false);
                response.put("message", "Giỏ hàng trống");
                return ResponseEntity.ok(response);
            }

            BigDecimal totalAmount = (BigDecimal) cartInfo.get("totalAmount");
            BigDecimal finalAmount = totalAmount;
            BigDecimal discountAmount = BigDecimal.ZERO;
            PhieuGiamGia usedVoucher = null;

            // Xử lý phiếu giảm giá
            if (voucherId != null) {
                Optional<PhieuGiamGia> voucherOpt = phieuGiamGiaRepo.findById(voucherId);
                if (voucherOpt.isPresent()) {
                    PhieuGiamGia voucher = voucherOpt.get();
                    System.out.println("DEBUG: Voucher - ID: " + voucher.getId() +
                            ", loaiGiamGia: " + voucher.getLoaiGiamGia() +
                            ", giaTriGiam: " + voucher.getGiaTriGiam());
                    if (!voucher.getLoaiPhieu()) {
                        Optional<PhieuGiamGiaKhachHang> pggkhOpt = phieuGiamGiaKhachHangRepo
                                .findByPhieuGiamGiaAndKhachHang(voucher, khachHang);
                        if (!pggkhOpt.isPresent()) {
                            response.put("success", false);
                            response.put("message", "Phiếu giảm giá không hợp lệ hoặc không thuộc về bạn");
                            return ResponseEntity.ok(response);
                        }
                        // Cập nhật trạng thái đã sử dụng cho phiếu giảm giá cá nhân
                        PhieuGiamGiaKhachHang pggkh = pggkhOpt.get();
                        pggkh.setDaSuDung(true);
                        phieuGiamGiaKhachHangRepo.save(pggkh);
                    }
                    // Tăng số lượng đã sử dụng cho phiếu giảm giá
                    voucher.setSoLuongDaSuDung(voucher.getSoLuongDaSuDung() + 1);
                    phieuGiamGiaRepo.save(voucher);
                    usedVoucher = voucher;
                    discountAmount = phieuGiamGiaService.calculateDiscountAmount(voucher, totalAmount);
                    finalAmount = totalAmount.subtract(discountAmount);
                    System.out.println("DEBUG: Calculated - totalAmount: " + totalAmount +
                            ", discountAmount: " + discountAmount +
                            ", finalAmount: " + finalAmount);
                } else {
                    System.out.println("DEBUG: Voucher with ID " + voucherId + " not found");
                }
            }

            DiaChi diaChi = new DiaChi();
            diaChi.setKhachHang(khachHang);
            diaChi.setTinh(tinhThanh);
            diaChi.setHuyen(quanHuyen);
            diaChi.setXa(phuongXa);
            diaChi.setSoNhaNgoDuong(diaChiChiTiet);
            DiaChi savedDiaChi = diaChiRepo.save(diaChi);

            HoaDon hoaDon = new HoaDon();
            hoaDon.setMa(generateOrderCode());
            hoaDon.setKhachHang(khachHang);
            hoaDon.setTenNguoiNhan(hoTen);
            hoaDon.setSdtNguoiNhan(soDienThoai);
            hoaDon.setTinh(tinhThanh);
            hoaDon.setHuyen(quanHuyen);
            hoaDon.setXa(phuongXa);
            hoaDon.setSoNhaNgoDuong(diaChiChiTiet);
            hoaDon.setPhuongThucThanhToan(phuongThucThanhToan);
            hoaDon.setGhiChu(ghiChu);
            hoaDon.setLoaiHoaDon(false);
            hoaDon.setTrangThai(1);
            hoaDon.setNgayTao(LocalDateTime.now());
            hoaDon.setNguoiTao(khachHang.getTen());
            hoaDon.setTongTien(totalAmount);
            hoaDon.setTongTienSauGiamGia(finalAmount);
            hoaDon.setPhiVanChuyen(BigDecimal.ZERO);
            hoaDon.setPhieuGiamGia(usedVoucher); // Lưu phiếu giảm giá vào hóa đơn

            HoaDon savedHoaDon = hoaDonRepo.save(hoaDon);
            System.out.println("DEBUG: Saved HoaDon - ID: " + savedHoaDon.getId() +
                    ", tongTien: " + savedHoaDon.getTongTien() +
                    ", tongTienSauGiamGia: " + savedHoaDon.getTongTienSauGiamGia() +
                    ", phieuGiamGiaId: " + (savedHoaDon.getPhieuGiamGia() != null ? savedHoaDon.getPhieuGiamGia().getId() : "null"));

            List<Map<String, Object>> cartItems = (List<Map<String, Object>>) cartInfo.get("items");
            if (cartItems == null || cartItems.isEmpty()) {
                throw new RuntimeException("Giỏ hàng không chứa sản phẩm để lưu chi tiết hóa đơn");
            }

            for (Map<String, Object> item : cartItems) {
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setHoaDon(savedHoaDon);

                Long sanPhamChiTietId = ((Number) item.get("sanPhamChiTietId")).longValue();
                Optional<SanPhamChiTiet> sanPhamChiTietOpt = sanPhamChiTietRepo.findById(sanPhamChiTietId);
                if (!sanPhamChiTietOpt.isPresent()) {
                    throw new RuntimeException("Không tìm thấy sản phẩm với ID: " + sanPhamChiTietId);
                }
                SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietOpt.get();

                // Validate stock
                int soLuong = ((Number) item.get("soLuong")).intValue();
                if (sanPhamChiTiet.getSoLuong() < soLuong) {
                    throw new RuntimeException("Sản phẩm " + sanPhamChiTiet.getSanPham().getTen() + " không đủ số lượng tồn kho");
                }

                hoaDonChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                hoaDonChiTiet.setSoLuong(soLuong);
                hoaDonChiTiet.setGia(sanPhamChiTiet.getGia());
                hoaDonChiTiet.setGiaSauGiam(sanPhamChiTiet.getGia());
                hoaDonChiTiet.setThanhTien(sanPhamChiTiet.getGia().multiply(new BigDecimal(soLuong)));
                hoaDonChiTiet.setTrangThai(1);

                // Update stock
                sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() - soLuong);
                sanPhamChiTietRepo.save(sanPhamChiTiet);

                hoaDonChiTietRepo.save(hoaDonChiTiet);
            }

            LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
            lichSuHoaDon.setHoaDon(savedHoaDon);
            lichSuHoaDon.setTrangThai(1);
            lichSuHoaDon.setNgayTao(LocalDateTime.now());
            lichSuHoaDon.setMoTa("Đơn hàng được tạo bởi khách hàng: " + khachHang.getTen());
            lichSuHoaDon.setNguoiTao(khachHang.getTen());
            lichSuHoaDonRepo.save(lichSuHoaDon);

            // Xử lý thanh toán theo phương thức
            if ("MOMO".equals(phuongThucThanhToan)) {
                // Thanh toán MoMo
                try {
                    MomoTransaction transaction = momoService.createTransaction(savedHoaDon, "user");

                    if (transaction.getTrangThai() == 2) { // Lỗi
                        response.put("success", false);
                        response.put("message", "Lỗi khi tạo thanh toán MoMo: " + transaction.getMessage());
                        return ResponseEntity.ok(response);
                    }

                    // Trả về thông tin thanh toán MoMo
                    response.put("success", true);
                    response.put("orderId", savedHoaDon.getId());
                    response.put("finalAmount", finalAmount);
                    response.put("discountAmount", discountAmount);
                    response.put("paymentMethod", "MOMO");
                    response.put("payUrl", transaction.getPayUrl());
                    response.put("transactionId", transaction.getId());

                } catch (Exception e) {
                    logger.error("Error creating MoMo payment", e);
                    response.put("success", false);
                    response.put("message", "Lỗi khi tạo thanh toán MoMo: " + e.getMessage());
                    return ResponseEntity.ok(response);
                }
            } else {
                // Thanh toán COD
                // Clear cart for COD since no further payment confirmation is needed
                gioHangService.clearCart(session);
                response.put("success", true);
                response.put("orderId", savedHoaDon.getId());
                response.put("finalAmount", finalAmount);
                response.put("discountAmount", discountAmount);
                response.put("paymentMethod", "COD");
                response.put("redirectUrl", "/checkout/success?id=" + savedHoaDon.getId());
            }

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Có lỗi xảy ra: " + e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/success")
    public String orderSuccess(@RequestParam("id") Long orderId, Model model, HttpSession session) {
        try {
            TaiKhoan currentUser = (TaiKhoan) session.getAttribute("currentUser");
            if (currentUser == null) {
                System.out.println("currentUser is null in orderSuccess");
                return "redirect:/login";
            }

            Optional<HoaDon> hoaDonOpt = hoaDonRepo.findById(orderId);
            if (!hoaDonOpt.isPresent()) {
                System.out.println("Không tìm thấy hóa đơn với ID: " + orderId);
                model.addAttribute("errorMessage", "Không tìm thấy đơn hàng");
                return "user/errorPage";
            }

            HoaDon hoaDon = hoaDonOpt.get();
            if (hoaDon.getKhachHang() == null || !hoaDon.getKhachHang().getTaiKhoan().getId().equals(currentUser.getId())) {
                System.out.println("Khách hàng không có quyền truy cập đơn hàng: " + orderId);
                model.addAttribute("errorMessage", "Bạn không có quyền xem đơn hàng này");
                return "user/errorPage";
            }

            model.addAttribute("hoaDon", hoaDon);
            List<HoaDonChiTiet> chiTietList = hoaDonChiTietRepo.findByHoaDon_Id(orderId);
            if (chiTietList == null || chiTietList.isEmpty()) {
                System.out.println("Không tìm thấy chi tiết đơn hàng cho ID: " + orderId);
                model.addAttribute("errorMessage", "Không tìm thấy chi tiết đơn hàng");
            }
            model.addAttribute("chiTietList", chiTietList);

            return "user/datHangThanhCong";
        } catch (Exception e) {
            System.out.println("Lỗi trong orderSuccess: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi hiển thị trang thành công");
            return "user/errorPage";
        }
    }

    @GetMapping("/PGG")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> getAvailableVouchers(HttpSession session) {
        try {
            // Kiểm tra đăng nhập
            TaiKhoan currentUser = (TaiKhoan) session.getAttribute("currentUser");
            if (currentUser == null) {
                System.out.println("❌ DEBUG: Không tìm thấy currentUser trong session");
                return ResponseEntity.status(401).build();
            }

            System.out.println(" DEBUG: Current user ID: " + currentUser.getId());

            // Tìm khách hàng
            Optional<KhachHang> khachHangOpt = khachHangRepo.findByTaiKhoan(currentUser.getId());
            if (!khachHangOpt.isPresent()) {
                System.out.println(" DEBUG: Không tìm thấy khách hàng cho TaiKhoan ID: " + currentUser.getId());
                return ResponseEntity.status(404).build();
            }
            KhachHang khachHang = khachHangOpt.get();
            System.out.println(" DEBUG: Found KhachHang ID: " + khachHang.getId());

            // Lấy thông tin giỏ hàng
            Map<String, Object> cartInfo = gioHangService.getCartInfo(session);
            if ((Boolean) cartInfo.get("isEmpty")) {
                System.out.println(" DEBUG: Giỏ hàng trống");
                return ResponseEntity.ok(new ArrayList<>());
            }

            BigDecimal orderAmount = (BigDecimal) cartInfo.get("totalAmount");
            System.out.println(" DEBUG: Order amount: " + orderAmount);

            // Kiểm tra quan hệ phiếu giảm giá - khách hàng
            List<PhieuGiamGiaKhachHang> allRelations = phieuGiamGiaKhachHangRepo.findByKhachHang(khachHang);
            System.out.println(" DEBUG: Total PGG-KH relations: " + allRelations.size());

            for (PhieuGiamGiaKhachHang relation : allRelations) {
                System.out.println("   - Relation ID: " + relation.getId() +
                        ", PGG: " + relation.getPhieuGiamGia().getMa() +
                        ", TrangThai: " + relation.getTrangThai() +
                        ", DaSuDung: " + relation.getDaSuDung());
            }

            // Lấy phiếu giảm giá cá nhân CHƯA SỬ DỤNG
            List<PhieuGiamGiaKhachHang> privateVoucherRelations = phieuGiamGiaKhachHangRepo
                    .findByKhachHangAndTrangThaiAndDaSuDung(khachHang, true, false);

            System.out.println(" DEBUG: Active unused private voucher relations: " + privateVoucherRelations.size());

            // Lấy danh sách voucher từ service
            List<Map<String, Object>> vouchers = phieuGiamGiaService
                    .getAvailableVouchers(khachHang, orderAmount);

            System.out.println(" DEBUG: Total vouchers returned: " + vouchers.size());

            return ResponseEntity.ok(vouchers);

        } catch (Exception e) {
            System.err.println(" DEBUG: Error in getAvailableVouchers: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Endpoint để nhận IPN callback từ MoMo
     */
    @PostMapping("/momo-notify")
    @ResponseBody
    public ResponseEntity<String> momoNotify(@RequestBody Map<String, Object> payload) {
        logger.info("Received MoMo IPN notification: {}", payload);
        
        try {
            // Xử lý thông báo từ MoMo
            if (payload.containsKey("orderId") && payload.containsKey("resultCode")) {
                String orderId = payload.get("orderId").toString();
                int resultCode = Integer.parseInt(payload.get("resultCode").toString());
                
                // Kiểm tra kết quả giao dịch
                if (resultCode == 0) {
                    // Giao dịch thành công
                    MomoTransaction transaction = momoService.getTransactionByOrderId(orderId);
                    if (transaction != null) {
                        // Cập nhật trạng thái giao dịch
                        momoService.confirmTransaction(transaction.getHoaDon().getId());
                        return ResponseEntity.ok("Transaction processed successfully");
                    }
                }
            }
            
            return ResponseEntity.ok("Transaction processing failed");
        } catch (Exception e) {
            logger.error("Error processing MoMo IPN: {}", e.getMessage());
            return ResponseEntity.ok("Error processing transaction");
        }
    }
    
    /**
     * Endpoint khi người dùng được redirect từ MoMo về
     */
    @GetMapping("/momo-return")
    public String momoReturn(@RequestParam Map<String, String> params, RedirectAttributes redirectAttributes, HttpSession session) {
        logger.info("User returned from MoMo payment: {}", params);

        try {
            if (params.containsKey("orderId") && params.containsKey("resultCode")) {
                String orderId = params.get("orderId");
                int resultCode = Integer.parseInt(params.get("resultCode"));

                if (resultCode == 0) {
                    MomoTransaction transaction = momoService.getTransactionByOrderId(orderId);
                    if (transaction != null) {
                        // Cập nhật trạng thái giao dịch
                        momoService.confirmTransaction(transaction.getHoaDon().getId());
                        // Xóa giỏ hàng khi giao dịch thành công
                        gioHangService.clearCart(session);
                        redirectAttributes.addFlashAttribute("paymentStatus", "success");
                        redirectAttributes.addFlashAttribute("paymentMessage", "Thanh toán MoMo thành công!");
                        return "redirect:/checkout/success?id=" + transaction.getHoaDon().getId();
                    }
                } else {
                    // Giao dịch thất bại
                    redirectAttributes.addFlashAttribute("paymentStatus", "error");
                    redirectAttributes.addFlashAttribute("paymentMessage", "Thanh toán MoMo thất bại!");
                    return "redirect:/checkout";
                }
            }

            redirectAttributes.addFlashAttribute("paymentStatus", "error");
            redirectAttributes.addFlashAttribute("paymentMessage", "Có lỗi xảy ra trong quá trình thanh toán!");
            return "redirect:/checkout";

        } catch (Exception e) {
            logger.error("Error processing MoMo return: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("paymentStatus", "error");
            redirectAttributes.addFlashAttribute("paymentMessage", "Có lỗi xảy ra trong quá trình thanh toán!");
            return "redirect:/checkout";
        }
    }
    
    /**
     * Kiểm tra trạng thái thanh toán MoMo
     */
    @GetMapping("/check-momo-status")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkMomoStatus(@RequestParam("orderId") Long orderId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            MomoTransaction transaction = momoService.getTransactionByHoaDonId(orderId);
            
            if (transaction == null) {
                response.put("success", false);
                response.put("message", "Không tìm thấy giao dịch MoMo");
                return ResponseEntity.ok(response);
            }
            
            // Kiểm tra trạng thái giao dịch
            // 0: Chờ thanh toán, 1: Đã thanh toán, 2: Lỗi, 3: Đã hủy
            response.put("success", transaction.getTrangThai() == 1);
            response.put("status", transaction.getTrangThai());
            response.put("message", transaction.getMessage());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error checking MoMo status: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Lỗi khi kiểm tra trạng thái thanh toán");
            return ResponseEntity.ok(response);
        }
    }

    // Tạo mã đơn hàng
    private String generateOrderCode() {
        // Đếm tổng số hóa đơn hiện có và cộng thêm 1
        Long count = hoaDonRepo.count() + 1;
        return String.format("HD%03d", count);
    }
}
