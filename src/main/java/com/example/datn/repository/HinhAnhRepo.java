package com.example.datn.repository;

import com.example.datn.entity.HinhAnh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HinhAnhRepo extends JpaRepository<HinhAnh, Long> {
    @Query("SELECT h FROM HinhAnh h WHERE h.id IN (SELECT spct.hinhAnh.id FROM SanPhamChiTiet spct WHERE spct.sanPham.id = :sanPhamId)")
    List<HinhAnh> findAllBySanPhamId(@Param("sanPhamId") Long sanPhamId);
    
    @Query("SELECT h FROM HinhAnh h WHERE h.id IN (SELECT spct.hinhAnh.id FROM SanPhamChiTiet spct WHERE spct.id = :sanPhamChiTietId)")
    List<HinhAnh> findAllBySanPhamChiTietId(@Param("sanPhamChiTietId") Long sanPhamChiTietId);
}
