package com.example.datn.controller.BanHang;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ban-hang-tai-quay")
@RequiredArgsConstructor
public class BanHangTaiQuayController {
    @GetMapping("index")
    public String index(Model model) {
        model.addAttribute("message","hêlaoos");
        return "admin/sale/index";
    }
}

