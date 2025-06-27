package com.example.datn.controller.Payment;

import com.example.datn.entity.DiaChi;
import com.example.datn.entity.HoaDon.HoaDon;
import com.example.datn.entity.HoaDon.HoaDonChiTiet;
import com.example.datn.entity.HoaDon.LichSuHoaDon;
import com.example.datn.entity.KhachHang;
import com.example.datn.entity.SanPham.SanPhamChiTiet;
import com.example.datn.entity.TaiKhoan;
import com.example.datn.repository.DiaChiRepo;
import com.example.datn.repository.HoaDonRepo.HoaDonChiTietRepo;
import com.example.datn.repository.HoaDonRepo.HoaDonRepo;
import com.example.datn.repository.HoaDonRepo.LichSuHoaDonRepo;
import com.example.datn.repository.KhachHangRepo.KhachHangRepo;
import com.example.datn.repository.SanPhamRepo.SanPhamChiTietRepo;
import com.example.datn.service.gioHangService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/checkout")
public class ThanhToanOnlController {
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
    public ResponseEntity<Map<String, Object>> processOrder(
            @RequestParam String hoTen,
            @RequestParam String soDienThoai,
            @RequestParam(required = false) String email,
            @RequestParam String tinhThanh,
            @RequestParam String quanHuyen,
            @RequestParam String phuongXa,
            @RequestParam String diaChiChiTiet,
            @RequestParam String phuongThucThanhToan,
            @RequestParam(required = false) String ghiChu,
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
            hoaDon.setDiaChi(savedDiaChi);
            hoaDon.setPhuongThucThanhToan(phuongThucThanhToan);
            hoaDon.setGhiChu(ghiChu);
            hoaDon.setLoaiHoaDon(false);
            hoaDon.setTrangThai(1);
            hoaDon.setNgayTao(LocalDateTime.now());
            hoaDon.setNguoiTao(khachHang.getTen());
            hoaDon.setTongTien((BigDecimal) cartInfo.get("totalAmount"));
            hoaDon.setTongTienSauGiamGia(hoaDon.getTongTien());
            hoaDon.setPhiVanChuyen(BigDecimal.ZERO);

            HoaDon savedHoaDon = hoaDonRepo.save(hoaDon);
            if (savedHoaDon.getId() == null) {
                throw new RuntimeException("Không thể lưu hóa đơn");
            }

            List<Map<String, Object>> cartItems = (List<Map<String, Object>>) cartInfo.get("items");
            for (Map<String, Object> item : cartItems) {
                Long sanPhamChiTietId = Long.valueOf(item.get("sanPhamChiTietId").toString());
                Integer soLuong = (Integer) item.get("soLuong");
                BigDecimal gia = (BigDecimal) item.get("gia");
                BigDecimal tongTienItem = (BigDecimal) item.get("tongTien");

                Optional<SanPhamChiTiet> sanPhamChiTietOpt = sanPhamChiTietRepo.findById(sanPhamChiTietId);
                if (!sanPhamChiTietOpt.isPresent()) {
                    throw new RuntimeException("Sản phẩm không tồn tại");
                }

                SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietOpt.get();
                if (sanPhamChiTiet.getSoLuong() < soLuong) {
                    throw new RuntimeException("Sản phẩm " + sanPhamChiTiet.getSanPham().getTen() + " không đủ số lượng");
                }

                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setHoaDon(savedHoaDon);
                hoaDonChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                hoaDonChiTiet.setSoLuong(soLuong);
                hoaDonChiTiet.setGia(gia);
                hoaDonChiTiet.setGiaSauGiam(gia);
                hoaDonChiTiet.setThanhTien(tongTienItem);
                hoaDonChiTiet.setTrangThai(1);
                hoaDonChiTietRepo.save(hoaDonChiTiet);

                sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() - soLuong);
                sanPhamChiTietRepo.save(sanPhamChiTiet);
            }

            LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
            lichSuHoaDon.setHoaDon(savedHoaDon);
            lichSuHoaDon.setTrangThai(1);
            lichSuHoaDon.setNgayTao(LocalDateTime.now());
            lichSuHoaDon.setMoTa("Đơn hàng được tạo bởi khách hàng: " + khachHang.getTen());
            lichSuHoaDon.setNguoiTao(khachHang.getTen());
            lichSuHoaDonRepo.save(lichSuHoaDon);

            gioHangService.clearCart(session);

            response.put("success", true);
            response.put("message", "Đặt hàng thành công!");
            response.put("orderId", savedHoaDon.getId());
            response.put("orderCode", savedHoaDon.getMa());
            response.put("redirectUrl", "/checkout/success?id=" + savedHoaDon.getId());

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

    // Tạo mã đơn hàng
    private String generateOrderCode() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String random = String.format("%04d", new Random().nextInt(10000));
        return "DH" + timestamp + random;
    }
}
