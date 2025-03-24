package com.example.datn.service.Implements.ThuocTinhImpl;

import com.example.datn.entity.ThuocTinh.ChatLieuCanh;
import com.example.datn.repository.ThuocTinhRepo.ChatLieuCanhRepo;
import com.example.datn.service.ThuocTinhService.ChatLieuCanhService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatLieuCanhServiceImpl implements ChatLieuCanhService {

    private final ChatLieuCanhRepo chatLieuCanhRepo;

    @Override
    public Page<ChatLieuCanh> search(String query, Boolean trangThai, Pageable pageable) {
        if (trangThai == null) {
            return chatLieuCanhRepo.searchOnlyTen(query, pageable);
        }
        return chatLieuCanhRepo.search(query, trangThai, pageable);
    }

    @Override
    public ResponseEntity<String> add(String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Tên chất liệu cánh không được để trống.");
        }

        Optional<ChatLieuCanh> checkTonTai = chatLieuCanhRepo.findByTen(name.trim());
        if (checkTonTai.isPresent()) {
            return ResponseEntity.badRequest().body("Đã tồn tại chất liệu cánh.");
        }

        ChatLieuCanh chatLieuCanh = new ChatLieuCanh();
        chatLieuCanh.setTen(name.trim());
        chatLieuCanh.setTrangThai(true);
        chatLieuCanhRepo.save(chatLieuCanh);

        return ResponseEntity.ok("Chất liệu cánh thêm mới thành công.");
    }
}
