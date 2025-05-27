package com.example.datn.service.ThuocTinhService;

import com.example.datn.entity.ThuocTinh.ChatLieuCanh;
import com.example.datn.entity.ThuocTinh.KieuQuat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface KieuQuatService {
    List<KieuQuat> findAllKieuQuat();
    KieuQuat findById(Long id);
    void save(KieuQuat kieuQuat);
    Page<KieuQuat> search(String query, Boolean trangThai, Pageable pageable);
    ResponseEntity<?> add(String name);
    ResponseEntity<?> changeStatus(Long id);
}
