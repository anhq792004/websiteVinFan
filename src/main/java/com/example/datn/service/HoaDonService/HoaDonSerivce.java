package com.example.datn.service.HoaDonService;

import com.example.datn.entity.HoaDon.HoaDon;
import org.springframework.data.domain.Page;

public interface HoaDonSerivce {
    //hoaDon
    Page<HoaDon> findAllHoaDonAndSortDay(int page, int size);


    //hoaDonChiTiet
}
