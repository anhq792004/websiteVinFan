package com.example.datn.controller;

import com.example.datn.entity.PhieuGiam;
import com.example.datn.repository.PhieuGiamRepo;
import com.example.datn.service.PhieuGiamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;

@Controller
@RequestMapping("/admin/phieu-giam")
@RequiredArgsConstructor
public class PhieuGiamController {

    private final PhieuGiamRepo pggRepo;
    private final PhieuGiamService pggSV;

    //phieu giam gia
    @GetMapping("/index")
    public String phieuGiam(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            @RequestParam(name = "status", required = false) Boolean status
    ) {
        if (page < 0) {
            page = 0;
        }
        PageRequest pageable = PageRequest.of(page, size);
        String searchTerm = "%" + keyword + "%";

        // Xây dựng điều kiện tìm kiếm
        Page<PhieuGiam> phieuGiamGiaPage;
        if (status != null) {
            phieuGiamGiaPage = pggRepo.findByTenLikeAndTrangThai(searchTerm, status, pageable);
        } else {
            phieuGiamGiaPage = pggRepo.findByTenLike(searchTerm, pageable);
        }

        model.addAttribute("ListPGG", phieuGiamGiaPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", phieuGiamGiaPage.getTotalPages());
        model.addAttribute("pgg", new PhieuGiam());

        return "admin/phieu_giam/index";
    }

    @GetMapping("/store")
    public String store(

    ){
        return "/admin/phieu_giam/create";
    }

    @PostMapping("/add")
    public String add(PhieuGiam pgg) {
        LocalDate currentDate = LocalDate.now(); // Lấy ngày hiện tại
        java.sql.Date sqlDate = Date.valueOf(currentDate); // Chuyển đổi LocalDate sang Date
        pgg.setNgayTao(sqlDate); // Lưu ngày hiện tại vào đối tượng PhieuGiam
        String ma = pggSV.taoMaTuDong();
        // Thiết lập giá trị mặc định cho người tạo là "admin"
        pgg.setMa(ma);
        pgg.setNguoiTao("admin");

        pggRepo.save(pgg); // Lưu đối tượng PhieuGiam vào cơ sở dữ liệu
        return "redirect:/admin/phieu-giam/index"; // Chuyển hướng về trang danh sách phiếu giảm giá
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        PhieuGiam phieuGiam = pggRepo.findById(id).orElse(null);
        model.addAttribute("pgg", phieuGiam);
        return "admin/phieu_giam/update";
    }

    @PostMapping("/update")
    public String update(PhieuGiam phieuGiam) {
        LocalDate currentDate = LocalDate.now(); // Lấy ngày hiện tại
        java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate); // Chuyển đổi LocalDate sang Date
        phieuGiam.setNgaySua(sqlDate);
        pggRepo.save(phieuGiam);
        return "redirect:/admin/phieu-giam/index";
    }
}
