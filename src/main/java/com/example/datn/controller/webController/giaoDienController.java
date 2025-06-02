package com.example.datn.controller.webController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vinfan")
public class giaoDienController {

    @GetMapping("/index")
    public String index() {
        return "admin/web_nguoiDung/index";
    }

    @GetMapping("/detail")
    public String detail() {
        return "admin/web_nguoiDung/detail_web";
    }

    @GetMapping("/cart")
    public String cart() {
        return "admin/web_nguoiDung/cart";
    }
}
