package com.example.datn.service.ThuocTinhService;

import com.example.datn.entity.ThuocTinh.Hang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HangService {
    List<Hang> findAllHang();
    Hang findById(Integer id);
    void save(Hang hang);
    Page<Hang> search(String query, Boolean trangThai, Pageable pageable);
    ResponseEntity<?> add(String name);
    ResponseEntity<?> update(Integer id, String name);
    ResponseEntity<?> changeStatus(Integer id);
}