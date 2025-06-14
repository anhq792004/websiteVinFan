package com.example.datn.controller;

import com.example.datn.dto.DangKyDto;
import com.example.datn.repository.TaiKhoanRepo;
import com.example.datn.service.taiKhoanService.TaiKhoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DangKyController {
    @Autowired
    private TaiKhoanService taiKhoanService;
    @Autowired
    private TaiKhoanRepo taiKhoanRepo;

    @GetMapping("/register")
    public String showDangKyForm(Model model) {
        model.addAttribute("dangKyDto", new DangKyDto());
        return "admin/user/register";
    }

    @PostMapping("/register")
    public String xuLyDangKy(@Valid @ModelAttribute("dangKyDto") DangKyDto dangKyDto,
                             BindingResult result,
                             Model model) {

        if (!dangKyDto.getMatKhau().equals(dangKyDto.getXacNhanMatKhau())) {
            model.addAttribute("passwordError", "Mật khẩu xác nhận không khớp");
            return "register";
        }

        if (taiKhoanRepo.existsByEmail(dangKyDto.getEmail())) {
            model.addAttribute("emailError", "Email đã tồn tại");
            return "register";
        }

        if (result.hasErrors()) {
            return "register";
        }

        taiKhoanService.dangKyTaiKhoan(dangKyDto);
        return "redirect:/login?success";
    }
}
