package com.example.datn.service.KhachHangService;

import com.example.datn.dto.request.AddKhachHangRequest;
import com.example.datn.entity.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface KhachHangService {
    Page<KhachHang> findAll(String keyword, Boolean trang_thai, Pageable pageable);

    KhachHang findById(Long id);

    KhachHang addKH(AddKhachHangRequest request);

    String generateCode();
}
