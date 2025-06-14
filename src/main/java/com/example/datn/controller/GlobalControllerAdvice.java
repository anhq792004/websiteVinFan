package com.example.datn.controller;

import com.example.datn.entity.TaiKhoan;
import com.example.datn.repository.TaiKhoanRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private TaiKhoanRepo taiKhoanRepo;

    @ModelAttribute
    public void addUserInfo(HttpSession session, Model model) {
        TaiKhoan currentUser = null;
        boolean isLoggedIn = false;

        // Kiểm tra từ session trước
        TaiKhoan userFromSession = (TaiKhoan) session.getAttribute("loggedUser");
        if (userFromSession != null) {
            currentUser = userFromSession;
            isLoggedIn = true;
        } else {
            // Nếu không có trong session, kiểm tra từ Security Context
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null &&
                    authentication.isAuthenticated() &&
                    !"anonymousUser".equals(authentication.getName())) {

                try {
                    TaiKhoan taiKhoan = taiKhoanRepo.findByEmail(authentication.getName());
                    if (taiKhoan != null) {
                        currentUser = taiKhoan;
                        isLoggedIn = true;
                        // Lưu vào session để lần sau không phải query lại
                        session.setAttribute("loggedUser", taiKhoan);
                        session.setAttribute("isLoggedIn", true);
                    }
                } catch (Exception e) {
                    System.err.println("Error loading user: " + e.getMessage());
                }
            }
        }

        // Thêm vào model
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("isLoggedIn", isLoggedIn);

        // Debug log
        System.out.println("GlobalControllerAdvice - isLoggedIn: " + isLoggedIn +
                ", user: " + (currentUser != null ? currentUser.getEmail() : "null"));
    }
}
