package com.example.datn.service.taiKhoanService;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void sendEmail(String to, String subject, String content) {
        // Giả lập gửi email bằng cách in ra console
        System.out.println("\n=== THÔNG BÁO EMAIL ===");
        System.out.println("Gửi đến: " + to);
        System.out.println("Tiêu đề: " + subject);
        System.out.println("Nội dung: Thông báo đổi mật khẩu thành công");
        System.out.println("=========================\n");
    }
}
