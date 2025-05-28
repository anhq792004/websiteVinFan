package com.example.datn.service.BanHang;

import com.example.datn.entity.HoaDon.HoaDon;
import com.example.datn.entity.KhachHang;

import java.util.List;

public interface BanHangService {
    void taoHoaDonCho(HoaDon hoaDon);

    List<HoaDon> findHoaDon();

    KhachHang getKhachHangLe(Long id);

    void thanhToan(Long idHD);

    void updateTongTienHoaDon(Long idHD);

}
