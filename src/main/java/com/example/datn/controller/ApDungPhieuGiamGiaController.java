package com.example.datn.controller;

import com.example.datn.entity.PhieuGiamGia;
import com.example.datn.entity.SanPham.SanPham;
import com.example.datn.entity.SanPham.SanPhamChiTiet;
import com.example.datn.repository.PhieuGiamGiaRepo;
import com.example.datn.repository.SanPhamRepo.SanPhamChiTietRepo;
import com.example.datn.repository.SanPhamRepo.SanPhamRepo;
import com.example.datn.service.ApDungPhieuGiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ap-dung-pgg")
public class ApDungPhieuGiamGiaController {
    @Autowired
    private ApDungPhieuGiamGiaService apDungPhieuGiamGiaService;

    @Autowired
    private PhieuGiamGiaRepo phieuGiamGiaRepo;

    @Autowired
    private SanPhamChiTietRepo sanPhamChiTietRepo;

    // Trang chính hiển thị form chọn phiếu giảm giá
    @GetMapping("/ap-dung")
    public String trangApDungPhieuGiamGia(Model model) {
        model.addAttribute("dsPhieuGiamGia", phieuGiamGiaRepo.findAll());
        return "admin/phieu_giam_gia/ap-dung";
    }

    // Xử lý khi chọn phiếu giảm giá
    @GetMapping("/chon-phieu")
    public String chonPhieuGiamGia(@RequestParam Long phieuGiamGiaId, Model model, RedirectAttributes redirectAttributes) {
        // Lấy thông tin phiếu giảm giá
        Optional<PhieuGiamGia> phieuGiamGiaOpt = phieuGiamGiaRepo.findById(phieuGiamGiaId);

        if (!phieuGiamGiaOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Phiếu giảm giá không tồn tại");
            return "redirect:/ap-dung-pgg/ap-dung";
        }

        PhieuGiamGia phieuGiamGia = phieuGiamGiaOpt.get();
        model.addAttribute("phieuGiamGia", phieuGiamGia);

        // Lấy danh sách sản phẩm chi tiết đang hoạt động
        List<SanPhamChiTiet> dsSanPhamChiTiet = sanPhamChiTietRepo.findByTrangThaiTrue();
        model.addAttribute("dsSanPhamChiTiet", dsSanPhamChiTiet);

        // Lấy danh sách sản phẩm chi tiết đã áp dụng phiếu giảm giá này
        List<Long> dsSanPhamChiTietDaApDung = apDungPhieuGiamGiaService.getSanPhamChiTietIdsApDungPhieuGiamGia(phieuGiamGiaId);
        model.addAttribute("dsSanPhamChiTietIdsDaApDung", dsSanPhamChiTietDaApDung);

        // Lấy danh sách tất cả phiếu giảm giá để hiển thị dropdown
        model.addAttribute("dsPhieuGiamGia", phieuGiamGiaRepo.findAll());

        return "admin/phieu_giam_gia/ap-dung";
    }

    // Xử lý áp dụng phiếu giảm giá
    @PostMapping("/ap-dung")
    public String xuLyApDung(
            @RequestParam Long phieuGiamGiaId,
            @RequestParam(required = false) List<Long> sanPhamChiTietIds,
            RedirectAttributes redirectAttributes) {

        if (sanPhamChiTietIds == null) {
            sanPhamChiTietIds = new ArrayList<>(); // Khởi tạo danh sách rỗng thay vì null
        }

        try {
            // Nếu danh sách rỗng và người dùng gửi form, có thể họ muốn hủy áp dụng cho tất cả
            if (sanPhamChiTietIds.isEmpty()) {
                apDungPhieuGiamGiaService.huyTatCaApDungTheoPhieuGiamGia(phieuGiamGiaId);
                redirectAttributes.addFlashAttribute("success", "Đã hủy áp dụng phiếu giảm giá cho tất cả sản phẩm!");
            } else {
                // Áp dụng phiếu giảm giá cho các sản phẩm được chọn
                apDungPhieuGiamGiaService.apDungPhieuGiamGiaChoNhieuSanPham(phieuGiamGiaId, sanPhamChiTietIds);
                redirectAttributes.addFlashAttribute("success", "Áp dụng phiếu giảm giá thành công!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xử lý phiếu giảm giá: " + e.getMessage());
        }

        return "redirect:/ap-dung-pgg/chon-phieu?phieuGiamGiaId=" + phieuGiamGiaId;
    }

    // Hủy áp dụng tất cả sản phẩm
    @GetMapping("/huy-ap-dung/{id}")
    public String huyApDungTatCa(@PathVariable("id") Long phieuGiamGiaId, RedirectAttributes redirectAttributes) {
        try {
            apDungPhieuGiamGiaService.huyTatCaApDungTheoPhieuGiamGia(phieuGiamGiaId);
            redirectAttributes.addFlashAttribute("success", "Đã hủy áp dụng phiếu giảm giá cho tất cả sản phẩm!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi hủy áp dụng: " + e.getMessage());
        }
        return "redirect:/ap-dung-pgg/chon-phieu?phieuGiamGiaId=" + phieuGiamGiaId;
    }

    // Quay lại danh sách phiếu giảm giá
    @GetMapping("/danh-sach-pgg")
    public String danhSachPhieuGiamGia() {
        return "redirect:/phieu-giam-gia/index";
    }
}
