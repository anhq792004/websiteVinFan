package com.example.datn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/phieu-giam-gia")
public class PhieuGiamGiaController {
    @GetMapping("index")
    public String index(Model model) {
        model.addAttribute("message","helloo");
        return "admin/phieu_giam_gia/index";
    }
}
