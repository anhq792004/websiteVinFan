package com.example.datn.service.ThuocTinhService;

import com.example.datn.entity.ThuocTinh.ChatLieuKhung;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;


public interface ChatLieuKhungService {

    ChatLieuKhung findById(Long id);
    void save(ChatLieuKhung chatLieuKhung);
    Page<ChatLieuKhung> search(String query, Boolean trangThai, Pageable pageable);
    ResponseEntity<?> add(String name);
    ResponseEntity<String> update(Long id, String nameUpdate);
    ResponseEntity<?> changeStatus(Long id);
}
