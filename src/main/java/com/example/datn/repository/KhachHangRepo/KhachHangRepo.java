package com.example.datn.repository.KhachHangRepo;

import com.example.datn.entity.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KhachHangRepo extends JpaRepository<KhachHang,Long> {

    Page<KhachHang> findByTenContaining(String ten,Pageable pageable);
    Page<KhachHang> findByTrangThai(Boolean trangThai, Pageable pageable);
    Page<KhachHang> findByTenContainingAndTrangThai(String ten, Boolean trangThai, Pageable pageable);
    Page<KhachHang> findByTenContainingIgnoreCaseAndTrangThai(String search, Boolean trangThai, Pageable pageable);
}
