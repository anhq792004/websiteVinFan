package com.example.datn.repository;

import com.example.datn.entity.NhanVien.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NhanVienRepo extends JpaRepository<NhanVien,Long> {
    Page<NhanVien> findByTenContaining(String ten, Pageable pageable);
    Page<NhanVien> findByChucVuId(Long chucVuId, Pageable pageable);
    Page<NhanVien> findByTrangThai(Boolean trangThai, Pageable pageable);
    Page<NhanVien> findByTenContainingAndChucVuId(String ten, Long chucVuId,Pageable pageable);
    Page<NhanVien> findByTenContainingAndTrangThai(String ten, Boolean trangThai, Pageable pageable);
    Page<NhanVien> findByChucVuIdAndTrangThai(Long chucVuId,Boolean trangThai,Pageable pageable);
    Page<NhanVien> findByTenContainingAndChucVuIdAndTrangThai(String ten,Long chucVuId,Boolean trangThai,Pageable pageable);
}
