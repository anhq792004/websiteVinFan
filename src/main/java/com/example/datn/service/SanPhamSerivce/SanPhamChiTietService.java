package com.example.datn.service.SanPhamSerivce;

import com.example.datn.entity.SanPham.SanPhamChiTiet;
import org.springframework.web.multipart.MultipartFile;

public interface SanPhamChiTietService {
    SanPhamChiTiet findById(Long id);
    
    void update(Long id, Long mauSacId, Long congSuatId, Long hangId, Long nutBamId,
                Integer soLuong, Double gia, Double canNang, Boolean trangThai, 
                String moTa, MultipartFile hinhAnh);
    
    void updateInline(Long id, Integer soLuong, Double gia);
    
    SanPhamChiTiet add(Long sanPhamId, Long mauSacId, Long congSuatId, Long hangId, Long nutBamId,
                       Integer soLuong, Double gia, Double canNang, Boolean trangThai, 
                       String moTa, MultipartFile hinhAnh);
                
    void delete(Long id);
} 