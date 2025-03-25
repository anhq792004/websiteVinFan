package com.example.datn.service.ThuocTinhService;

import com.example.datn.entity.ThuocTinh.DuongKinhCanh;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DuongKinhCanhService {
    List<DuongKinhCanh> findAllDuongKinhCanh();
    DuongKinhCanh findById(Integer id);
    void save(DuongKinhCanh duongKinhCanh);
    Page<DuongKinhCanh> search(String query, Boolean trangThai, Pageable pageable);
    ResponseEntity<?> add(String name);
    ResponseEntity<?> update(Integer id, String name);
    ResponseEntity<?> changeStatus(Integer id);
}
