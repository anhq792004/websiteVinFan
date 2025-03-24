package com.example.datn.service.Implements;

import com.example.datn.entity.ThuocTinh.KieuQuat;
import com.example.datn.repository.ThuocTinhRepo.KieuQuatRepo;
import com.example.datn.service.KieuQuatService.KieuQuatService;
import lombok.RequiredArgsConstructor;
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
    public KieuQuat findById(Long id) {
        return kieuQuatRepo.findById(id).orElse(null);
    }

    @Override
    public void save(KieuQuat kieuQuat) {
        kieuQuatRepo.save(kieuQuat);
    }
}
