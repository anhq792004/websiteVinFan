package com.example.datn.service;

import com.example.datn.entity.DiaChi;

import java.util.List;

public interface DiaChiService {
    List<DiaChi> getDiaChiByIdKhachHang(Long idKH);
}
