package com.example.datn.service.ThuocTinhService;

import com.example.datn.entity.ThuocTinh.NutBam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NutBamService {
    List<NutBam> findAll();
    NutBam findById(Long id);
    void save(NutBam nutBam);
    void delete(Long id);
    void thayDoiTrangThai(Long id);
    Page<NutBam> search(String query, Boolean trangThai, Pageable pageable);
    ResponseEntity<?> add(String name);
    ResponseEntity<?> update(Integer id, String name);
    ResponseEntity<?> changeStatus(Integer id);
}