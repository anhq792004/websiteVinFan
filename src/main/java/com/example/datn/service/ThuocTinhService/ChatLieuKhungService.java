package com.example.datn.service.ThuocTinhService;

import com.example.datn.entity.ThuocTinh.ChatLieuKhung;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ChatLieuKhungService {
    List<ChatLieuKhung> findAllChatLieuKhung();
    ChatLieuKhung findById(Integer id);
    void save(ChatLieuKhung chatLieuKhung);
    Page<ChatLieuKhung> search(String query, Boolean trangThai, Pageable pageable);
    ResponseEntity<?> add(String name);
    ResponseEntity<?> update(Integer id, String name);
    ResponseEntity<?> changeStatus(Integer id);
}
