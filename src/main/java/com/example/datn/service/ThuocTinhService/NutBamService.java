package com.example.datn.service.ThuocTinhService;

import com.example.datn.entity.ThuocTinh.NutBam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NutBamService {
    List<NutBam> findAllNutBam();
    NutBam findById(Integer id);
    void save(NutBam nutBam);
    Page<NutBam> search(String query, Boolean trangThai, Pageable pageable);
    ResponseEntity<?> add(String name);
    ResponseEntity<?> update(Integer id, String name);
    ResponseEntity<?> changeStatus(Integer id);
}