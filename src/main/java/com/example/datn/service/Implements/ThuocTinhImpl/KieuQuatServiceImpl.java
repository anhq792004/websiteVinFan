package com.example.datn.service.Implements.ThuocTinhImpl;

import com.example.datn.entity.ThuocTinh.KieuQuat;
import com.example.datn.repository.ThuocTinhRepo.KieuQuatRepo;
import com.example.datn.service.ThuocTinhService.KieuQuatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KieuQuatServiceImpl implements KieuQuatService {

    private final KieuQuatRepo kieuQuatRepo;

    @Override
    public List<KieuQuat> findAllKieuQuat() {
        return kieuQuatRepo.findAll();
    }

    @Override
    public KieuQuat findById(Integer id) {
        return kieuQuatRepo.findById(id).orElse(null);
    }

    @Override
    public void save(KieuQuat kieuQuat) {
        kieuQuatRepo.save(kieuQuat);
    }

    @Override
    public Page<KieuQuat> search(String query, Boolean trangThai, Pageable pageable) {
        if (trangThai == null) {
            return kieuQuatRepo.searchOnlyTen(query, pageable);
        }
        return kieuQuatRepo.search(query, trangThai, pageable);
    }
}
