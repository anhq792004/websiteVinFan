package com.example.datn.controller;

import com.example.datn.service.taiKhoanService.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class QuenMatKhauController {
    @Autowired
    private TaiKhoanService taiKhoanService;

    // Hiển thị form đổi mật khẩu
    @GetMapping("/forgot-password")
    public String hienThiFormDoiMatKhau() {
        return "admin/user/quenMK";
    }
    // Xử lý đổi mật khẩu
    @PostMapping("/forgot-password")
    public String xuLyDoiMatKhau(@RequestParam("email") String email,
                                 @RequestParam("matKhauCu") String matKhauCu,
                                 @RequestParam("matKhauMoi") String matKhauMoi,
                                 @RequestParam("xacNhanMatKhau") String xacNhanMatKhau,
                                 RedirectAttributes redirectAttributes) {

        // Kiểm tra mật khẩu mới và xác nhận khớp nhau
        if (!matKhauMoi.equals(xacNhanMatKhau)) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu mới và xác nhận mật khẩu không khớp.");
            return "redirect:/forgot-password";
        }

        // Thực hiện đổi mật khẩu
        boolean ketQua = taiKhoanService.doiMatKhauVaThongBao(email, matKhauCu, matKhauMoi);

        if (ketQua) {
            redirectAttributes.addFlashAttribute("success",
                    "Mật khẩu đã được thay đổi thành công. Thông báo đã được gửi đến email của bạn.");
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("error",
                    "Email không tồn tại hoặc mật khẩu cũ không đúng.");
            return "redirect:/forgot-password";
        }
    }
}
