package com.example.datn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/nhan-vien")
public class NhanVienController {
    @GetMapping("index")
    public String index(Model model) {
        model.addAttribute("message","nV");
        return "admin/nhan_vien/index";
    }
}
