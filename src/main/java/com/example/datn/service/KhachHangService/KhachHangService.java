package com.example.datn.service.KhachHangService;

import com.example.datn.dto.request.AddKhachHangRequest;
import com.example.datn.entity.KhachHang;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface KhachHangService {
    Page<KhachHang> findAllKhachHang(int page, int size,String search,Boolean trangThai);

    Optional<KhachHang> findKhachHangById(Long id);

    void saveKhachHang(KhachHang khachHang);

    KhachHang addKH(AddKhachHangRequest request);
}
