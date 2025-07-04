package com.example.datn.service.SanPhamSerivce;

import com.example.datn.entity.SanPham.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface SanPhamService {
    Page<SanPham> findAllSanPham(int page, int size, String search, Long kieuQuatId, Boolean trangThai);

    Optional<SanPham> findSanPhamById(Long id);

    void saveSanPham(SanPham sanPham);

    void saveSanPhamWithImage(SanPham sanPham, MultipartFile image);

    void updateSanPham(SanPham sanPham);

    void updateSanPhamWithImage(SanPham sanPham, MultipartFile image);

    // Thêm thay đổi trạng thái
    boolean thayDoiTrangThaiSanPham(Long id);

    // Thêm method để lấy tất cả sản phẩm đang hoạt động
    List<SanPham> findAllActiveProducts();
}
