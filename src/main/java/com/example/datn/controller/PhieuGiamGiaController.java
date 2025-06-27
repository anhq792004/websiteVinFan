package com.example.datn.controller;

import com.example.datn.entity.PhieuGiamGia;
import com.example.datn.repository.PhieuGiamGiaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/phieu-giam-gia")
public class PhieuGiamGiaController {
    @Autowired
    private PhieuGiamGiaRepo phieuGiamGiaRepo;

    @GetMapping("/index")
    public String hienThiDanhSach(Model model) {
        model.addAttribute("dsPhieuGiamGia", phieuGiamGiaRepo.findAll());
        return "admin/phieu_giam_gia/index";
    }

    @GetMapping("/create")
    public String hienThiFormTao(Model model) {
        // Tạo đối tượng mới và thiết lập mã tự tăng
        PhieuGiamGia phieuGiamGia = new PhieuGiamGia();
        phieuGiamGia.setMa(generateNextCode());

        model.addAttribute("phieuGiamGia", phieuGiamGia);
        return "admin/phieu_giam_gia/create";
    }

    @PostMapping("/save")
    public String themMoi(@ModelAttribute PhieuGiamGia phieuGiamGia) {
        // Nếu không có mã hoặc mã rỗng, tạo mã mới
        if (phieuGiamGia.getMa() == null || phieuGiamGia.getMa().trim().isEmpty()) {
            phieuGiamGia.setMa(generateNextCode());
        }

        phieuGiamGia.setNgayTao(new Date());
        phieuGiamGiaRepo.save(phieuGiamGia);
        return "redirect:/admin/phieu-giam-gia/index";
    }

    @GetMapping("/edit/{id}")
    public String sua(@PathVariable Long id, Model model) {
        Optional<PhieuGiamGia> pgg = phieuGiamGiaRepo.findById(id);
        if (pgg.isPresent()) {
            model.addAttribute("phieuGiamGia", pgg.get());
            return "admin/phieu_giam_gia/create"; // Sử dụng lại template create
        } else {
            return "redirect:/admin/phieu-giam-gia/index";
        }
    }

    @PostMapping("/update")
    public String capNhat(@ModelAttribute PhieuGiamGia phieuGiamGia) {
        // Lấy thông tin cũ từ cơ sở dữ liệu để giữ nguyên ngày tạo
        Optional<PhieuGiamGia> pggCu = phieuGiamGiaRepo.findById(phieuGiamGia.getId());
        if (pggCu.isPresent()) {
            phieuGiamGia.setNgayTao(pggCu.get().getNgayTao());

            // Nếu mã bị xóa trống, giữ lại mã cũ
            if (phieuGiamGia.getMa() == null || phieuGiamGia.getMa().trim().isEmpty()) {
                phieuGiamGia.setMa(pggCu.get().getMa());
            }
        } else {
            // Nếu không tìm thấy, đặt ngày tạo là ngày hiện tại
            phieuGiamGia.setNgayTao(new Date());

            // Nếu mã trống, tạo mã mới
            if (phieuGiamGia.getMa() == null || phieuGiamGia.getMa().trim().isEmpty()) {
                phieuGiamGia.setMa(generateNextCode());
            }
        }

        phieuGiamGiaRepo.save(phieuGiamGia);
        return "redirect:/admin/phieu-giam-gia/index";
    }

    @GetMapping("/delete/{id}")
    public String xoa(@PathVariable Long id) {
        try {
            if (phieuGiamGiaRepo.existsById(id)) {
                phieuGiamGiaRepo.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/phieu-giam-gia/index";
    }

    /**
     * Phương thức tạo mã tự tăng cho phiếu giảm giá
     * Format: PGG + số thứ tự (3 chữ số)
     * Ví dụ: PGG001, PGG002, v.v.
     */
    private String generateNextCode() {
        // Lấy phiếu giảm giá có mã lớn nhất (theo thứ tự giảm dần)
        List<PhieuGiamGia> phieuGiamGias = phieuGiamGiaRepo.findAll(Sort.by(Sort.Direction.DESC, "ma"));

        int nextNumber = 1; // Giá trị mặc định nếu không có phiếu nào

        if (!phieuGiamGias.isEmpty()) {
            // Lấy mã lớn nhất hiện tại
            String maxCode = phieuGiamGias.get(0).getMa();

            try {
                // Trích xuất phần số từ mã (bỏ qua prefix 'PGG')
                if (maxCode != null && maxCode.length() > 3 && maxCode.startsWith("PGG")) {
                    String numberPart = maxCode.substring(3);
                    nextNumber = Integer.parseInt(numberPart) + 1;
                }
            } catch (NumberFormatException e) {
                // Nếu định dạng không đúng, sử dụng giá trị mặc định
                nextNumber = 1;
            }
        }

        // Định dạng số với độ dài 3 chữ số (001, 002, etc.)
        return String.format("PGG%03d", nextNumber);
    }
}
