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

    @Query("SELECT p FROM PhieuGiamGia p WHERE p.trangThai = true AND (p.ngayKetThuc < :currentDate OR p.ngayBatDau > :currentDate)")
    List<PhieuGiamGia> findExpiredCoupons(@Param("currentDate") Date currentDate);

    @Query("SELECT pgg FROM PhieuGiamGia pgg WHERE " +
            "(:search IS NULL OR :search = '' OR LOWER(pgg.ma) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(pgg.ten) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
            "(:trangThai IS NULL OR pgg.trangThai = :trangThai) AND " +
            "(:ngayBatDau IS NULL OR pgg.ngayBatDau >= :ngayBatDau) AND " +
            "(:ngayKetThuc IS NULL OR pgg.ngayKetThuc <= :ngayKetThuc) " +
            "ORDER BY pgg.ngayTao DESC")
    List<PhieuGiamGia> findWithFilters(@Param("search") String search,
                                       @Param("trangThai") Boolean trangThai,
                                       @Param("ngayBatDau") Date ngayBatDau,
                                       @Param("ngayKetThuc") Date ngayKetThuc);

}
