package com.example.datn.controller.BanHang;

import com.example.datn.dto.request.*;
import com.example.datn.entity.HoaDon.HoaDon;
import com.example.datn.entity.HoaDon.HoaDonChiTiet;
import com.example.datn.entity.KhachHang;
import com.example.datn.entity.MomoTransaction;
import com.example.datn.entity.PhieuGiamGia;
import com.example.datn.entity.SanPham.SanPhamChiTiet;
import com.example.datn.repository.KhachHangRepo.KhachHangRepo;
import com.example.datn.repository.PhieuGiamGiaRepo;
import com.example.datn.repository.SanPhamRepo.SanPhamChiTietRepo;
import com.example.datn.service.BanHang.BanHangService;
import com.example.datn.service.HoaDonService.HoaDonService;
import com.example.datn.service.MomoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.HashMap;

@Controller
@RequestMapping("/sale")
@RequiredArgsConstructor
public class BanHangTaiQuayController {

    private final BanHangService banHangService;
    private final HoaDonService hoaDonService;
    private final KhachHangRepo khachHangRepo;
    private final SanPhamChiTietRepo sanPhamChiTietRepo;
    private final PhieuGiamGiaRepo phieuGiamGiaRepo;
    private final MomoService momoService;


    @GetMapping("index")
    public String index(Model model) {
        //hiển thị danh sách hóa đơn chờ
        List<HoaDon> listHoaDon = banHangService.findHoaDon();
        model.addAttribute("listHoaDon", listHoaDon);
        System.out.println();
        return "admin/sale/index";
    }

    @GetMapping("/hdct")
    public String viewHDCT(Model model, @RequestParam("idHD") Long idHD, 
                           @ModelAttribute("paymentStatus") String paymentStatus,
                           @ModelAttribute("paymentMessage") String paymentMessage) {
        // Thêm thông báo thanh toán vào model nếu có
        if (paymentStatus != null && !paymentStatus.isEmpty()) {
            model.addAttribute("paymentStatus", paymentStatus);
            model.addAttribute("paymentMessage", paymentMessage);
        }
        
        Optional<HoaDon> hoaDonOptional = hoaDonService.findHoaDonById(idHD);
        HoaDon hoaDon = hoaDonOptional.orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy hóa đơn"));
        model.addAttribute("hoaDon", hoaDon);
        // Hiển thị thông tin khách hàng
        KhachHang khachHang = hoaDon.getKhachHang();
        model.addAttribute("khachHang", khachHang);
        //List khách hàng
        model.addAttribute("listsKhachhang", khachHangRepo.findAll());
        // Hiển thị danh sách hóa đơn chờ
        List<HoaDon> listHoaDon = banHangService.findHoaDon();
        model.addAttribute("listHoaDon", listHoaDon);
        
        // Hiển thị danh sách sản phẩm
        List<SanPhamChiTiet> findSPCTByIdSanPham = sanPhamChiTietRepo.findByTrangThaiTrue();
        // Lọc các sản phẩm còn trong kho (số lượng > 0)
        findSPCTByIdSanPham = findSPCTByIdSanPham.stream()
            .filter(sp -> sp.getSoLuong() > 0)
            .collect(Collectors.toList());
        
        System.out.println("Số lượng sản phẩm có trạng thái true và còn hàng: " + findSPCTByIdSanPham.size());
        for (SanPhamChiTiet sp : findSPCTByIdSanPham) {
            System.out.println("ID: " + sp.getId() + 
                ", Tên: " + (sp.getSanPham() != null ? sp.getSanPham().getTen() : "null") + 
                ", Giá: " + sp.getGia() + 
                ", Số lượng: " + sp.getSoLuong());
        }
        model.addAttribute("findSPCTByIdSanPham", findSPCTByIdSanPham);
        
        //Hiển thị danh sách hdct theo id hóa đơn
        List<HoaDonChiTiet> listHDCT = hoaDonService.listHoaDonChiTiets(idHD);
        model.addAttribute("listHDCT", listHDCT);
        // Thêm ID hóa đơn được chọn vào model
        model.addAttribute("idHD", idHD);

        return "admin/sale/index";
    }

    @PostMapping("/tao-hoa-don")
    public ResponseEntity<String> taoHoaDon() {
        try {
            HoaDon hoaDon = new HoaDon();
            banHangService.taoHoaDonCho(hoaDon);
            return ResponseEntity.ok("Tạo hóa đơn thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Số lượng hóa đơn chờ < 10");
        }
    }

    @PostMapping("/huy")
    @ResponseBody
    public ResponseEntity<String> huy(@RequestParam("id") Long id,
                                      @RequestParam("ghiChu") String ghiChu) {
        hoaDonService.huy(id,ghiChu);
        return ResponseEntity.ok("Hóa đơn đã được hủy !");
    }

    @PostMapping("/addSP")
    public ResponseEntity<String> addSP(@RequestBody AddSPToHDCTRequest addSPToHDCTRequest) {
        try {
            hoaDonService.addSPToHDCT(addSPToHDCTRequest);
            banHangService.updateTongTienHoaDon(addSPToHDCTRequest.getIdHD());
            return ResponseEntity.ok("Thêm sản phẩm thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi: " + e.getMessage());
        }
    }

    @PostMapping("/addKH")
    public ResponseEntity<String> addKH(@RequestBody AddKHToHDCTRequest addKHToHDCTRequest) {
        try {
            hoaDonService.addKHToHDCT(addKHToHDCTRequest);
            return ResponseEntity.ok("Thêm khách hàng thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi: " + e.getMessage());
        }
    }

    @PostMapping("/xoa")
    @ResponseBody
    public ResponseEntity<String> deleteChiTiet(@RequestParam("idSP") Long idSP,
                                                @RequestParam("idHD") Long idHD) {
        try {
            hoaDonService.deleteSPInHD(idSP, idHD);
            banHangService.updateTongTienHoaDon(idHD);
            return ResponseEntity.ok("Xóa thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi xóa sản phẩm");
        }
    }

    @PostMapping("/tangSoLuong")
    @ResponseBody
    public ResponseEntity<String> tangSoLuong(@RequestParam("idSP") Long idSP,
                                              @RequestParam("idHD") Long idHD) {
        try {
            hoaDonService.tangSoLuong(idHD, idSP);
            banHangService.updateTongTienHoaDon(idHD);
            return ResponseEntity.ok("Tăng số lượng thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/giamSoLuong")
    @ResponseBody
    public ResponseEntity<String> giamSoLuong(@RequestParam("idSP") Long idSP,
                                              @RequestParam("idHD") Long idHD) {
        try {
            hoaDonService.giamSoLuong(idHD, idSP);
            banHangService.updateTongTienHoaDon(idHD);

            return ResponseEntity.ok("Giảm số lượng thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/updateSoLuong")
    @ResponseBody
    public ResponseEntity<String> updateSoLuong(UpdateSoLuongRequest request) {
        hoaDonService.updateSoluong(request);
        banHangService.updateTongTienHoaDon(request.getIdHD());

        return ResponseEntity.ok("Cập nhật thành công");
    }

    @PostMapping("/update-trang-thai")
    @ResponseBody
    public ResponseEntity<String> updateTrangThai(LoaiHoaDonRequest request) {
        try {
            banHangService.updateLoaiHoaDon(request);
            return ResponseEntity.ok("Cập nhật  thành công");
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi cho bạn xem
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi cập nhật ");
        }
    }

    @PostMapping("/thanh-toan")
    @ResponseBody
    public ResponseEntity<String> thanhToan(@RequestParam("idHD") Long idHD,
                                           @RequestParam("phuongThucThanhToan") String phuongThucThanhToan) {
        try {
            // Kiểm tra xem hóa đơn có chi tiết nào không
            List<HoaDonChiTiet> listHDCT = hoaDonService.listHoaDonChiTiets(idHD);
            if (listHDCT.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Hóa đơn chưa có sản phẩm nào!");
            }
            
            // Tìm hóa đơn
            Optional<HoaDon> hoaDonOpt = hoaDonService.findHoaDonById(idHD);
            if (!hoaDonOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy hóa đơn");
            }
            
            HoaDon hoaDon = hoaDonOpt.get();
            
            // Cập nhật phương thức thanh toán
            hoaDon.setPhuongThucThanhToan(phuongThucThanhToan);
            hoaDonService.saveHoaDon(hoaDon);

            // Xử lý thanh toán dựa trên phương thức
            if ("MOMO".equals(phuongThucThanhToan)) {
                // Tạo QR code thanh toán Momo
                return createMomoPayment(hoaDon);
            } else {
                // Thanh toán tiền mặt (mặc định)
                banHangService.thanhToan(idHD);
                return ResponseEntity.ok("Thanh toán thành công!");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    
    // Phương thức tạo thanh toán Momo
    private ResponseEntity<String> createMomoPayment(HoaDon hoaDon) {
        try {
            // Sử dụng MomoService để tạo giao dịch
            MomoTransaction transaction = momoService.createTransaction(hoaDon);
            
            // Kiểm tra nếu có lỗi
            if (transaction.getTrangThai() == 2) { // 2: Lỗi
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi tạo thanh toán Momo: " + transaction.getMessage());
            }
            
            // Kiểm tra xem có payUrl từ Momo không
            if (transaction.getPayUrl() != null && !transaction.getPayUrl().isEmpty()) {
                // Trả về URL thanh toán từ Momo
                return ResponseEntity.ok("MOMO_REDIRECT:" + transaction.getPayUrl());
            } else {
                // Trong trường hợp test hoặc không có payUrl, tạo QR code giả lập
                String qrCodeUrl = "https://test-payment.momo.vn/gw_payment/qr?id=" + transaction.getOrderId() + 
                                "&amount=" + transaction.getAmount() + 
                                "&partnerCode=" + transaction.getPartnerCode();
                
                // Trả về URL QR code để hiển thị
                return ResponseEntity.ok("MOMO_QR_CODE:" + qrCodeUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi khi tạo thanh toán Momo: " + e.getMessage());
        }
    }
    
    // API để lấy danh sách phiếu giảm giá đang hoạt động
    @GetMapping("/api/phieu-giam-gia/active")
    @ResponseBody
    public ResponseEntity<List<PhieuGiamGia>> getActivePhieuGiamGia() {
        // Lấy ngày hiện tại
        Date currentDate = new Date();
        
        // Lọc phiếu giảm giá còn hoạt động, còn hạn sử dụng và còn số lượng
        List<PhieuGiamGia> activePhieuGiamGia = phieuGiamGiaRepo.findAll().stream()
            .filter(pgg -> pgg.isTrangThai() && 
                          (pgg.getNgayBatDau().before(currentDate) || pgg.getNgayBatDau().equals(currentDate)) && 
                          (pgg.getNgayKetThuc().after(currentDate) || pgg.getNgayKetThuc().equals(currentDate)) &&
                          (pgg.getSoLuong() == null || pgg.getSoLuong() > 0))
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(activePhieuGiamGia);
    }
    
    // API để áp dụng phiếu giảm giá vào hóa đơn
    @PostMapping("/apply-discount")
    @ResponseBody
    public ResponseEntity<String> applyDiscount(@RequestParam("idHD") Long idHD, 
                                               @RequestParam("idPGG") Long idPGG) {
        try {
            // Tìm hóa đơn
            Optional<HoaDon> hoaDonOpt = hoaDonService.findHoaDonById(idHD);
            if (!hoaDonOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy hóa đơn");
            }
            
            // Tìm phiếu giảm giá
            Optional<PhieuGiamGia> phieuGiamGiaOpt = phieuGiamGiaRepo.findById(idPGG);
            if (!phieuGiamGiaOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy phiếu giảm giá");
            }
            
            HoaDon hoaDon = hoaDonOpt.get();
            PhieuGiamGia phieuGiamGia = phieuGiamGiaOpt.get();
            
            // Kiểm tra tính hợp lệ của phiếu giảm giá
            Date currentDate = new Date();
            if (!phieuGiamGia.isTrangThai() || 
                phieuGiamGia.getNgayBatDau().after(currentDate) || 
                phieuGiamGia.getNgayKetThuc().before(currentDate) ||
                (phieuGiamGia.getSoLuong() != null && phieuGiamGia.getSoLuong() <= 0)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phiếu giảm giá không hợp lệ hoặc đã hết hạn");
            }
            
            // Áp dụng phiếu giảm giá vào hóa đơn
            hoaDon.setPhieuGiamGia(phieuGiamGia);
            
            // Tính toán tổng tiền sau giảm giá
            BigDecimal tongTien = hoaDon.getTongTien();
            BigDecimal giaTriGiam;
            
            if (phieuGiamGia.getLoaiGiamGia()) {
                // Giảm theo phần trăm
                giaTriGiam = tongTien.multiply(phieuGiamGia.getGiaTriGiam())
                                    .divide(new BigDecimal(100), 0, BigDecimal.ROUND_HALF_UP);
            } else {
                // Giảm theo số tiền cố định
                giaTriGiam = phieuGiamGia.getGiaTriGiam();
                // Đảm bảo giá trị giảm không lớn hơn tổng tiền
                if (giaTriGiam.compareTo(tongTien) > 0) {
                    giaTriGiam = tongTien;
                }
            }
            
            BigDecimal tongTienSauGiam = tongTien.subtract(giaTriGiam);
            hoaDon.setTongTienSauGiamGia(tongTienSauGiam);
            
            // Lưu hóa đơn
            hoaDonService.saveHoaDon(hoaDon);
            
            // Giảm số lượng phiếu giảm giá nếu có giới hạn
            if (phieuGiamGia.getSoLuong() != null && phieuGiamGia.getSoLuong() > 0) {
                phieuGiamGia.setSoLuong(phieuGiamGia.getSoLuong() - 1);
                phieuGiamGiaRepo.save(phieuGiamGia);
            }
            
            return ResponseEntity.ok("Áp dụng phiếu giảm giá thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi áp dụng phiếu giảm giá: " + e.getMessage());
        }
    }

    // API để lấy danh sách sản phẩm
    @GetMapping("/api/san-pham")
    @ResponseBody
    public ResponseEntity<List<SanPhamChiTiet>> getSanPham() {
        try {
            // Lấy danh sách sản phẩm có trạng thái là true và còn hàng
            List<SanPhamChiTiet> danhSachSanPham = sanPhamChiTietRepo.findByTrangThaiTrue()
                .stream()
                .filter(sp -> sp.getSoLuong() > 0)
                .collect(Collectors.toList());
            
            // Đảm bảo các thuộc tính cần thiết được nạp đầy đủ
            for (SanPhamChiTiet sp : danhSachSanPham) {
                // Kích hoạt lazy loading cho các thuộc tính cần thiết
                if (sp.getSanPham() != null) {
                    sp.getSanPham().getTen();
                }
                if (sp.getMauSac() != null) {
                    sp.getMauSac().getTen();
                }
            }
            
            return ResponseEntity.ok(danhSachSanPham);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Xác nhận thanh toán Momo đã hoàn tất
    @PostMapping("/confirm-momo-payment")
    @ResponseBody
    public ResponseEntity<String> confirmMomoPayment(@RequestParam("idHD") Long idHD) {
        try {
            // Tìm giao dịch Momo
            Optional<HoaDon> hoaDonOpt = hoaDonService.findHoaDonById(idHD);
            if (!hoaDonOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy hóa đơn");
            }
            
            // Trong môi trường test, luôn xác nhận thành công
            boolean success = momoService.confirmTransaction(idHD);
            
            if (success) {
                return ResponseEntity.ok("Thanh toán Momo thành công!");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Không thể xác nhận thanh toán Momo");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi khi xác nhận thanh toán Momo: " + e.getMessage());
        }
    }
    
    // Hủy thanh toán Momo
    @PostMapping("/cancel-momo-payment")
    @ResponseBody
    public ResponseEntity<String> cancelMomoPayment(@RequestParam("idHD") Long idHD) {
        try {
            // Sử dụng MomoService để hủy giao dịch
            boolean success = momoService.cancelTransaction(idHD);
            
            if (success) {
                return ResponseEntity.ok("Đã hủy thanh toán Momo");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Không thể hủy thanh toán Momo");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi khi hủy thanh toán Momo: " + e.getMessage());
        }
    }
    
    // Kiểm tra trạng thái thanh toán Momo
    @GetMapping("/check-momo-payment-status")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkMomoPaymentStatus(@RequestParam("idHD") Long idHD) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Lấy thông tin giao dịch Momo
            MomoTransaction transaction = momoService.getTransactionByHoaDonId(idHD);
            
            if (transaction == null) {
                response.put("success", false);
                response.put("message", "Không tìm thấy giao dịch Momo cho hóa đơn này");
                return ResponseEntity.ok(response);
            }
            
            // Kiểm tra trạng thái giao dịch
            // 0: Chờ thanh toán, 1: Đã thanh toán, 2: Lỗi, 3: Đã hủy
            if (transaction.getTrangThai() == 1) {
                response.put("success", true);
                response.put("message", "Giao dịch đã được thanh toán");
            } else if (transaction.getTrangThai() == 0) {
                response.put("success", false);
                response.put("message", "Giao dịch đang chờ thanh toán");
            } else if (transaction.getTrangThai() == 2) {
                response.put("success", false);
                response.put("message", "Giao dịch bị lỗi: " + transaction.getMessage());
            } else if (transaction.getTrangThai() == 3) {
                response.put("success", false);
                response.put("message", "Giao dịch đã bị hủy");
            }
            
            response.put("status", transaction.getTrangThai());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Lỗi khi kiểm tra trạng thái thanh toán: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

