package com.example.datn.repository;

import com.example.datn.entity.PhieuGiamGia;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PhieuGiamGiaRepo extends JpaRepository<PhieuGiamGia,Long> {
    @Query("SELECT p FROM PhieuGiamGia p WHERE p.ma LIKE 'PGG%' ORDER BY p.ma DESC")
    List<PhieuGiamGia> findTopByOrderByMaDesc(Sort sort);
    // Kiểm tra xem mã đã tồn tại chưa
    boolean existsByMa(String ma);
}
