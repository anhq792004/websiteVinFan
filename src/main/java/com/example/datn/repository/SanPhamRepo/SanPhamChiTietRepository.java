package com.example.datn.repository.SanPhamRepo;

import com.example.datn.entity.SanPham.SanPhamChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTiet, Long> {
    
    /**
     * Kiểm tra biến thể sản phẩm đã tồn tại với các thuộc tính cụ thể
     */
    boolean existsBySanPhamIdAndMauSacIdAndCongSuatIdAndHangIdAndNutBamId(
            Long sanPhamId, Long mauSacId, Long congSuatId, Long hangId, Long nutBamId);
} 