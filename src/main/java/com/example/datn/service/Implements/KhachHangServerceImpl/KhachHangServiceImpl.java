package com.example.datn.service.Implements.KhachHangServerceImpl;

import com.example.datn.entity.KhachHang;
import com.example.datn.repository.KhachHangRepo.KhachHangRepo;
import com.example.datn.service.KhachHangService.KhachHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KhachHangServiceImpl implements KhachHangService {
    private final KhachHangRepo khachHangRepo;
    @Override
    public Page<KhachHang> findAllKhachHang(int page, int size, String search, Boolean trangThai) {
        Pageable pageable = PageRequest.of(page, size);
        // Logic tìm kiếm, ví dụ:
        if (search != null && !search.isEmpty()) {
            return khachHangRepo.findByTenContainingIgnoreCaseAndTrangThai(search, trangThai, pageable);
        }
        return khachHangRepo.findByTrangThai(trangThai, pageable);
    }

    @Override
    public Optional<KhachHang> findKhachHangById(Long id) {
        return khachHangRepo.findById(id);
    }

    @Override
    public void saveKhachHang(KhachHang khachHang) {
        khachHangRepo.save(khachHang);
    }

    @Override
    public void deleteKhachHang(Long id) {
        khachHangRepo.deleteById(id);
    }

    @Override
    public void updateKhachHang(KhachHang khachHang) {
        khachHangRepo.save(khachHang);
    }

    @Override
    public boolean thayDoiTrangThaiKhachHang(Long id) {
        return false;
    }
}
