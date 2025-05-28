package com.example.datn.service.ChucVuService;

import com.example.datn.entity.ChucVu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ChucVuService {
    List<ChucVu> findAllChucVu();
    ChucVu findById(Integer id);
    void save(ChucVu chucVu);
    Page<ChucVu> search(String query, Pageable pageable);
    ResponseEntity<?> add(String viTri);
    ResponseEntity<?> changeStatus(Integer id);
    ChucVu findChucVuByViTri(String viTri);
}
