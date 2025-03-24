package com.example.datn.repository.HoaDonRepo;

import com.example.datn.entity.HoaDon.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HoaDonChiTietRepo extends JpaRepository<HoaDonChiTiet,Long> {
    List<HoaDonChiTiet> findByHoaDon_Id(Long idHD);



}
