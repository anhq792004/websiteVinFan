package com.example.datn.repository.HoaDonRepo;

import com.example.datn.dto.response.LichSuThanhToanResponse;
import com.example.datn.entity.HoaDon.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HoaDonRepo extends JpaRepository<HoaDon,Long> {
    @Query("SELECT hd from HoaDon hd order by hd.ngayTao desc ")
    Page<HoaDon> findHoaDonAndSortDay(Pageable pageable);

    @Query("SELECT new com.example.datn.dto.response.LichSuThanhToanResponse(" +
            "hd.tongTienSauGiamGia , hd.ngayTao, hd.loaiHoaDon,hd.hinhThucThanhToan, hd.trangThai) " +
            "FROM HoaDon hd " +
            "where hd.id =:hoaDonId")
    LichSuThanhToanResponse findThanhToanHoaDonId(@Param("hoaDonId") long id);


}
