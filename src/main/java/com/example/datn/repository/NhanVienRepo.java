package com.example.datn.repository;

import com.example.datn.entity.KhachHang;
import com.example.datn.entity.NhanVien.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NhanVienRepo extends JpaRepository<NhanVien,Long> {
    @Query("SELECT nv FROM NhanVien nv " +
            "WHERE (LOWER(nv.ma) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(nv.ten) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
//            "OR LOWER(kh.taiKhoan.email) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(nv.soDienThoai) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:trangThai IS NULL OR nv.trangThai = :trangThai) ")
    Page<NhanVien> searchNhanVien(String keyword, Boolean trangThai, Pageable pageable);

    @Query("SELECT nv FROM NhanVien nv " +
            "WHERE (LOWER(nv.ma) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(nv.ten) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
//            "OR LOWER(kh.taiKhoan.email) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(nv.soDienThoai) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<NhanVien> searchNhanVienKhongCoTrangThai(String keyword, Pageable pageable);

}