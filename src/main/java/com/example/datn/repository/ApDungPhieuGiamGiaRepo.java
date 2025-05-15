package com.example.datn.repository;

import com.example.datn.entity.ApDungPhieuGiamGia;
import com.example.datn.entity.PhieuGiamGia;
import com.example.datn.entity.SanPham.SanPhamChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApDungPhieuGiamGiaRepo extends JpaRepository<ApDungPhieuGiamGia, Long> {

    // Tìm tất cả áp dụng phiếu giảm giá theo PGG
    List<ApDungPhieuGiamGia> findByPhieuGiamGia(PhieuGiamGia phieuGiamGia);

    // Tìm tất cả áp dụng phiếu giảm giá theo ID của PGG
    List<ApDungPhieuGiamGia> findByPhieuGiamGiaId(Long phieuGiamGiaId);

    // Kiểm tra sản phẩm đã áp dụng phiếu giảm giá nào chưa và đang còn hiệu lực
    boolean existsBySanPhamChiTietAndTrangThaiTrue(SanPhamChiTiet sanPhamChiTiet);

    // Lấy áp dụng PGG đang hoạt động của sản phẩm chi tiết
    ApDungPhieuGiamGia findBySanPhamChiTietAndTrangThaiTrue(SanPhamChiTiet sanPhamChiTiet);

    // Lấy theo ID của sản phẩm chi tiết và trạng thái
    ApDungPhieuGiamGia findBySanPhamChiTietIdAndTrangThaiTrue(Long sanPhamChiTietId);

    // Lấy danh sách ID sản phẩm chi tiết đã áp dụng phiếu giảm giá cụ thể
    @Query("SELECT a.sanPhamChiTiet.id FROM ApDungPhieuGiamGia a WHERE a.phieuGiamGia.id = :pgId AND a.trangThai = true")
    List<Long> findSanPhamChiTietIdsByPhieuGiamGiaId(@Param("pgId") Long phieuGiamGiaId);

    // Xóa tất cả áp dụng phiếu giảm giá theo ID phiếu giảm giá
    void deleteByPhieuGiamGiaId(Long phieuGiamGiaId);

    // Kiểm tra phiếu giảm giá đã được áp dụng cho sản phẩm chi tiết cụ thể chưa
    boolean existsByPhieuGiamGiaIdAndSanPhamChiTietIdAndTrangThaiTrue(Long phieuGiamGiaId, Long sanPhamChiTietId);
}
