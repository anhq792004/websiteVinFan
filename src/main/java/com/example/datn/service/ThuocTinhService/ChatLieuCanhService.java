package com.example.datn.service.ThuocTinhService;

import com.example.datn.entity.ThuocTinh.ChatLieuCanh;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatLieuCanhService {
    Page<ChatLieuCanh> search(String query, Boolean trangThai, Pageable pageable);
}
