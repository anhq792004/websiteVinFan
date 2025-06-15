package com.example.datn.controller.webController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fanTech")
public class giaoDienController {

    @GetMapping("/index")
    public String index() {
        return "user/index";
    }

    @GetMapping("/detail")
    public String detail() {
        return "user/detail";
    }

    @GetMapping("/cart")
    public String cart() {
        return "user/cart";
    }

}
