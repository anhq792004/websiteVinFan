package com.example.datn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String registered, Model model) {
        if (registered != null) {
            model.addAttribute("registrationSuccess", "Đăng ký tài khoản thành công! Vui lòng đăng nhập.");
        }
        return "admin/user/login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/phieu_giam_gia/index";
    }

    @GetMapping("/")
    public String home() {
        return "admin/user/home";
    }

    @Controller
    public class AccessDeniedController {

        @GetMapping("/access-denied")
        public String accessDenied() {
            return "admin/error/access-denied"; // Trả về template access-denied.html
        }
    }
}
