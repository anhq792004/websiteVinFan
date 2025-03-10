package com.example.datn.service.HoaDonService;

import com.example.datn.dto.request.TrangThaiHoaDonRequest;
import com.example.datn.dto.response.LichSuHoaDonResponse;
import com.example.datn.entity.HoaDon.HoaDon;
import com.example.datn.entity.HoaDon.LichSuHoaDon;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface HoaDonSerivce {
    //hoaDon
    List<HoaDon> findAll();

    Page<HoaDon> findAllHoaDonAndSortDay(int page, int size);

    Optional<HoaDon> findHoaDonById(Long id);

    String generateOrderCode();


    //hoaDonChiTiet

    void xacNhan(Long id);

    void giaoHang(Long id);

    void hoanThanh(Long id);

    void huy(Long id);

    //lichSuHoaDon
    List<LichSuHoaDon> lichSuHoaDonList(Long id);

    List<LichSuHoaDonResponse> lichSuHoaDonResponseList(Long id);

    //trang thai hoa don
    TrangThaiHoaDonRequest getTrangThaiHoaDon();
}
