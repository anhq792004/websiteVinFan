package com.example.datn.repository;

import com.example.datn.entity.SanPhamChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface SPCTRepo extends JpaRepository<SanPhamChiTiet, Long> {

    @Query("SELECT spct FROM SanPhamChiTiet spct JOIN spct.sanPham sp " +
            "WHERE (LOWER(sp.ten) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(sp.ma) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(sp.mo_ta) LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "AND spct.gia BETWEEN :minPrice AND :maxPrice")
    Page<SanPhamChiTiet> searchProducts(String query, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
//    @Query("SELECT spct FROM SanPhamChiTiet spct JOIN spct.sanPham sp " +
//            "WHERE (LOWER(sp.ten) LIKE LOWER(CONCAT('%', :query, '%')) " +
//            "OR LOWER(sp.ma) LIKE LOWER(CONCAT('%', :query, '%')) " +
//            "OR LOWER(sp.mo_ta) LIKE LOWER(CONCAT('%', :query, '%'))) " +
//            "AND (spct.gia BETWEEN :minPrice AND :maxPrice) " +
//            "AND sp.trang_thai = :trangThai")
//    Page<SanPhamChiTiet> searchProducts(String query, BigDecimal minPrice, BigDecimal maxPrice, Boolean trangThai, Pageable pageable);

}
