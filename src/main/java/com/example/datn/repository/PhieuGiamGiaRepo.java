package com.example.datn.repository;

import com.example.datn.entity.PhieuGiamGia;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PhieuGiamGiaRepo extends JpaRepository<PhieuGiamGia,Long> {
    @Query("SELECT p FROM PhieuGiamGia p WHERE p.ma LIKE 'PGG%' ORDER BY p.ma DESC")
    List<PhieuGiamGia> findTopByOrderByMaDesc(Sort sort);
    /**
     * Lấy phiếu giảm giá theo mã
     */
    Optional<PhieuGiamGia> findByMa(String ma);

    List<PhieuGiamGia> findByLoaiPhieuAndTrangThaiAndNgayBatDauBeforeAndNgayKetThucAfter(
            Boolean loaiPhieu,
            Boolean trangThai,
            LocalDateTime ngayBatDau,
            LocalDateTime ngayKetThuc
    );



}
