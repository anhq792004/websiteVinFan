package com.example.datn.service.Implements;

import com.example.datn.entity.SanPham.SanPham;
import com.example.datn.repository.SanPhamRepo.SanPhamRepo;
import com.example.datn.service.SanPhamSerivce.SanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SanPhamServiceImpl implements SanPhamService {
    private final SanPhamRepo sanPhamRepo;

    @Override
    public Page<SanPham> findAllSanPham(int page, int size, String search, Long kieuQuatId, Boolean trangThai) {
        Pageable pageable = PageRequest.of(page, size);

        if (search != null && !search.isEmpty()) {
            if (kieuQuatId != null && trangThai != null) {
                return sanPhamRepo.findByTenContainingAndKieuQuatIdAndTrangThai(search, kieuQuatId, trangThai, pageable);
            } else if (kieuQuatId != null) {
                return sanPhamRepo.findByTenContainingAndKieuQuatId(search, kieuQuatId, pageable);
            } else if (trangThai != null) {
                return sanPhamRepo.findByTenContainingAndTrangThai(search, trangThai, pageable);
            } else {
                return sanPhamRepo.findByTenContaining(search, pageable);
            }
        } else {
            if (kieuQuatId != null && trangThai != null) {
                return sanPhamRepo.findByKieuQuatIdAndTrangThai(kieuQuatId, trangThai, pageable);
            } else if (kieuQuatId != null) {
                return sanPhamRepo.findByKieuQuatId(kieuQuatId, pageable);
            } else if (trangThai != null) {
                return sanPhamRepo.findByTrangThai(trangThai, pageable);
            } else {
                return sanPhamRepo.findAll(pageable);
            }
        }
    }

    @Override
    public void saveSanPham(SanPham sanPham) {
        // Đảm bảo ngày tạo được thiết lập
        if (sanPham.getNgayTao() == null) {
            sanPham.setNgayTao(LocalDateTime.now());
        }
        sanPhamRepo.save(sanPham);
    }

    @Override
    public void updateSanPham(SanPham sanPham) {
        // Lấy sản phẩm hiện tại để giữ ngày tạo
        Optional<SanPham> existingSanPham = sanPhamRepo.findById(sanPham.getId());
        if (existingSanPham.isPresent()) {
            // Giữ ngày tạo ban đầu
            sanPham.setNgayTao(existingSanPham.get().getNgayTao());
        }
        sanPhamRepo.save(sanPham);
    }

    @Override
    public Optional<SanPham> findSanPhamById(Long id) {
        return sanPhamRepo.findById(id);
    }

    @Override
    public boolean thayDoiTrangThaiSanPham(Long id) {
        Optional<SanPham> sanPhamOptional = sanPhamRepo.findById(id);
        if (sanPhamOptional.isPresent()) {
            SanPham sanPham = sanPhamOptional.get();
            // Đảo bit trạng thái
            sanPham.setTrangThai(sanPham.getTrangThai() == Boolean.FALSE ? Boolean.TRUE : Boolean.FALSE);
            sanPhamRepo.save(sanPham);
            return true;
        }
        return false;
    }
}
