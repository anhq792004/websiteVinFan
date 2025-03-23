package com.example.datn.service.Implements;

import com.example.datn.entity.SanPham.SanPham;
import com.example.datn.repository.SanPhamRepo.SanPhamRepo;
import com.example.datn.service.SanPhamSerivce.SanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SanPhamServiceImpl implements SanPhamService {
    private final SanPhamRepo sanPhamRepo;

    @Override
    public Page<SanPham> findAllSanPham(int page, int size, String search, Long kieuQuatId, Boolean trangThai) {
        return sanPhamRepo.findAll(PageRequest.of(page , size));
    }

    @Override
    public void saveSanPham(SanPham sanPham) {
        sanPhamRepo.save(sanPham);
    }

//    @Override
//    public void deleteSanPham(Long id) {
//        sanPhamRepo.deleteById(id);
//    } Sản phẩm không được xóa, chỉ update

    @Override
    public void updateSanPham(SanPham sanPham) {
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
