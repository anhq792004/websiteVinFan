package com.example.datn.controller;

import com.example.datn.dto.DangKyDto;
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

    @GetMapping("/register")
    public String showDangKyForm(Model model) {
        model.addAttribute("dangKyDto", new DangKyDto());
        return "admin/user/register";
    }

    @PostMapping("/register")
    public String dangKy(@Valid @ModelAttribute("dangKyDto") DangKyDto dangKyDto,
                         BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        // Kiểm tra lỗi validation
        if (result.hasErrors()) {
            return "admin/user/register";
        }

        // Kiểm tra xem mật khẩu có khớp không
        if (!dangKyDto.getMatKhau().equals(dangKyDto.getXacNhanMatKhau())) {
            model.addAttribute("passwordError", "Mật khẩu xác nhận không khớp");
            return "admin/user/register";
        }

        // Kiểm tra xem email đã tồn tại chưa
        if (taiKhoanService.emailDaTonTai(dangKyDto.getEmail())) {
            model.addAttribute("emailError", "Email đã được đăng ký");
            return "admin/user/register";
        }

        // Tạo tài khoản mới
        taiKhoanService.dangKyTaiKhoan(dangKyDto);

        // Truyền thông báo thành công qua RedirectAttributes
        redirectAttributes.addFlashAttribute("registrationSuccess",
                "Đăng ký tài khoản thành công! Vui lòng đăng nhập.");

        // Chuyển hướng đến trang đăng nhập
        return "redirect:/login";
    }
}
