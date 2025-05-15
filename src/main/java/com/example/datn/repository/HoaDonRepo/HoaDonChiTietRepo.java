package com.example.datn.repository.HoaDonRepo;

import com.example.datn.entity.HoaDon.HoaDon;
import com.example.datn.entity.HoaDon.HoaDonChiTiet;
import com.example.datn.entity.SanPham.SanPhamChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface HoaDonChiTietRepo extends JpaRepository<HoaDonChiTiet,Long> {
    List<HoaDonChiTiet> findByHoaDon_Id(Long idHD);

    @Query("SELECT spct FROM SanPhamChiTiet spct JOIN spct.sanPham sp WHERE spct.sanPham.id = sp.id")
    List<SanPhamChiTiet> findSanPhamChiTietByIdSanPham();

    HoaDonChiTiet findByHoaDon_IdAndSanPhamChiTiet_Id(Long idHoaDon, Long idSanPhamChiTiet);

    Optional<HoaDonChiTiet> findByHoaDonIdAndSanPhamChiTietId(Long hoaDonId, Long sanPhamChiTietId);

    Optional<HoaDonChiTiet> findByHoaDonAndSanPhamChiTiet(HoaDon hoaDon, SanPhamChiTiet sanPhamChiTiet);

    @Modifying
    @Transactional
    @Query("DELETE FROM HoaDonChiTiet h WHERE h.sanPhamChiTiet.id = :idSanPhamChiTiet")
    void deleteSanPhamChiTiet_Id(@Param("idSanPhamChiTiet") Long idSanPhamChiTiet);

    @Query("SELECT sum(hd.soLuong) from HoaDonChiTiet hd where hd.hoaDon.id = :idHD")
    Integer sumSoLuong(@Param("idHD") long idHD);

}
