package com.example.datn.repository;

import com.example.datn.entity.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaiKhoanRepo extends JpaRepository<TaiKhoan,Long> {
    TaiKhoan findByEmail(String email);
    TaiKhoan findByResetToken(String resetToken);
}
