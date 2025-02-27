package com.example.datn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/khach-hang")
public class KhachHangController {
    @GetMapping("index")
    public String index(Model model) {
        model.addAttribute("message","KH");
        return "admin/phieu_giam_gia/index";
    }
}
