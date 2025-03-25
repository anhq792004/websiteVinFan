package com.example.datn.service.Implements.ThuocTinhImpl;

import com.example.datn.entity.ThuocTinh.ChatLieuCanh;
import com.example.datn.repository.ThuocTinhRepo.ChatLieuCanhRepo;
import com.example.datn.service.ThuocTinhService.ChatLieuCanhService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> add(String nameAdd) {
        if (nameAdd == null || nameAdd.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Tên chất liệu cánh không được để trống.");
        }

        Optional<ChatLieuCanh> checkTonTai = chatLieuCanhRepo.findByTen(nameAdd.trim());
        if (checkTonTai.isPresent()) {
            return ResponseEntity.badRequest().body("Đã tồn tại chất liệu cánh.");
        }

        ChatLieuCanh chatLieuCanh = new ChatLieuCanh();
        chatLieuCanh.setTen(nameAdd.trim());
        chatLieuCanh.setTrangThai(true);
        chatLieuCanhRepo.save(chatLieuCanh);

        return ResponseEntity.ok("Chất liệu cánh thêm mới thành công.");
    }

    @Override
    public Optional<ChatLieuCanh> getAll(Long id) {
        return chatLieuCanhRepo.findById(id);
    }

    @Override
    public ResponseEntity<String> update(Long id, String nameUpdate) {
        if (nameUpdate == null || nameUpdate.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Tên chất liệu cánh không được để trống.");
        }

        Optional<ChatLieuCanh> checkTonTai = chatLieuCanhRepo.findByTen(nameUpdate.trim());
        if (checkTonTai.isPresent()) {
            return ResponseEntity.badRequest().body("Đã tồn tại chất liệu cánh.");
        }

        Optional<ChatLieuCanh> chatLieuCanhOptional = chatLieuCanhRepo.findById(id);
        if (chatLieuCanhOptional.isPresent()) {
            ChatLieuCanh chatLieuCanh = chatLieuCanhOptional.get();
            chatLieuCanh.setTen(nameUpdate.trim());
            chatLieuCanhRepo.save(chatLieuCanh);
            return ResponseEntity.ok("Cập nhật chất liệu cánh thành công.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy chất liệu cánh.");
        }
    }
}
