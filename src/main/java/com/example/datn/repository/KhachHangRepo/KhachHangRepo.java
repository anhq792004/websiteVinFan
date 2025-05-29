package com.example.datn.repository.KhachHangRepo;

import com.example.datn.entity.KhachHang;
import com.example.datn.entity.TaiKhoan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KhachHangRepo extends JpaRepository<KhachHang,Long> {

    Page<KhachHang> findByTenContaining(String ten,Pageable pageable);
    Page<KhachHang> findByTrangThai(Boolean trangThai, Pageable pageable);
    Page<KhachHang> findByTenContainingAndTrangThai(String ten, Boolean trangThai, Pageable pageable);
    Page<KhachHang> findByTenContainingIgnoreCaseAndTrangThai(String search, Boolean trangThai, Pageable pageable);

    // Tìm khách hàng theo tài khoản
    Optional<KhachHang> findByTaiKhoan(TaiKhoan taiKhoan);

    // Tìm khách hàng theo email (thông qua tài khoản)
    Optional<KhachHang> findByTaiKhoan_Email(String email);

    // Tìm khách hàng theo số điện thoại
    Optional<KhachHang> findBySoDienThoai(String soDienThoai);

    // Tìm khách hàng theo mã
    Optional<KhachHang> findByMa(String ma);
}
