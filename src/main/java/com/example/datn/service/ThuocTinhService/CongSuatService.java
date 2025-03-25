package com.example.datn.service.ThuocTinhService;

import com.example.datn.entity.ThuocTinh.CongSuat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CongSuatService {
    List<CongSuat> findAllCongSuat();
    CongSuat findById(Integer id);
    void save(CongSuat congSuat);
    Page<CongSuat> search(String query, Boolean trangThai, Pageable pageable);
    ResponseEntity<?> add(String name);
    ResponseEntity<?> update(Integer id, String name);
    ResponseEntity<?> changeStatus(Integer id);
}