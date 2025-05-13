package com.example.datn.service.ThuocTinhService;

import com.example.datn.entity.ThuocTinh.MauSac;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MauSacService {
    List<MauSac> findAllMauSac();
    MauSac findById(Integer id);
    void save(MauSac mauSac);
    Page<MauSac> search(String query, Boolean trangThai, Pageable pageable);
    ResponseEntity<?> add(String name);
    ResponseEntity<?> update(Integer id, String name);
    ResponseEntity<?> changeStatus(Integer id);
}