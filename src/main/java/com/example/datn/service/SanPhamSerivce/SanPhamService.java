package com.example.datn.service.SanPhamSerivce;

import com.example.datn.entity.SanPham.SanPham;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface SanPhamService {
    Page<SanPham> findAllSanPham(int page, int size, String search, Long kieuQuatId, Boolean trangThai);

    Optional<SanPham> findSanPhamById(Long id);

    void saveSanPham(SanPham sanPham);

    //        void deleteSanPham(Long id);
    void updateSanPham(SanPham sanPham);

    // Thêm thay đổi trạng thái
    boolean thayDoiTrangThaiSanPham(Long id);
}
