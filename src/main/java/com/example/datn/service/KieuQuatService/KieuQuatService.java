package com.example.datn.service.KieuQuatService;

import com.example.datn.entity.ThuocTinh.KieuQuat;

import java.util.List;

public interface KieuQuatService {
    List<KieuQuat> findAllKieuQuat();
    KieuQuat findById(Long id);
    void save(KieuQuat kieuQuat);
}
