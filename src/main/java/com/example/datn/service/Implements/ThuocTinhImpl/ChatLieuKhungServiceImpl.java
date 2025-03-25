package com.example.datn.service.Implements.ThuocTinhImpl;

import com.example.datn.entity.ThuocTinh.ChatLieuKhung;
import com.example.datn.repository.ThuocTinhRepo.ChatLieuKhungRepo;
import com.example.datn.service.ThuocTinhService.ChatLieuKhungService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatLieuKhungServiceImpl implements ChatLieuKhungService {
    private final ChatLieuKhungRepo chatLieuKhungRepo;

    @Override
    public List<ChatLieuKhung> findAllChatLieuKhung() {
        return chatLieuKhungRepo.findAll();
    }

    @Override
    public ChatLieuKhung findById(Integer id) {
        return chatLieuKhungRepo.findById(id).orElse(null);
    }

    @Override
    public void save(ChatLieuKhung chatLieuKhung) {
        chatLieuKhungRepo.save(chatLieuKhung);
    }

    @Override
    public Page<ChatLieuKhung> search(String query, Boolean trangThai, Pageable pageable) {
        if (trangThai == null) {
            return chatLieuKhungRepo.searchOnlyTen(query, pageable);
        }
        return chatLieuKhungRepo.search(query, trangThai, pageable);
    }

    @Override
    public ResponseEntity<String> add(String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Tên chất liệu khung không được để trống.");
        }
        Optional<ChatLieuKhung> checkTonTai = chatLieuKhungRepo.findByTen(name.trim());
        if (checkTonTai.isPresent()) {
            return ResponseEntity.badRequest().body("Đã tồn tại chất liệu khung.");
        }
        ChatLieuKhung chatLieuKhung = new ChatLieuKhung();
        chatLieuKhung.setTen(name.trim());
        chatLieuKhung.setTrangThai(true);
        chatLieuKhungRepo.save(chatLieuKhung);

        return ResponseEntity.ok("Chất liệu khung thêm mới thành công.");
    }

    @Override
    public ResponseEntity<?> update(Integer id, String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Tên chất liệu khung không được để trống.");
        }
        Optional<ChatLieuKhung> checkTonTai = chatLieuKhungRepo.findByTen(name.trim());
        if (checkTonTai.isPresent() && !checkTonTai.get().getId().equals(id)) {
            return ResponseEntity.badRequest().body("Đã tồn tại chất liệu khung.");
        }
        ChatLieuKhung chatLieuKhung = chatLieuKhungRepo.findById(id).orElse(null);
        if (chatLieuKhung == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy chất liệu khung.");
        }
        chatLieuKhung.setTen(name.trim());
        chatLieuKhungRepo.save(chatLieuKhung);
        return ResponseEntity.ok("Cập nhật chất liệu khung thành công.");
    }

    @Override
    public ResponseEntity<?> changeStatus(Integer id) {
        ChatLieuKhung chatLieuKhung = chatLieuKhungRepo.findById(id).orElse(null);
        if (chatLieuKhung == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy chất liệu khung.");
        }
        chatLieuKhung.setTrangThai(!chatLieuKhung.getTrangThai());
        chatLieuKhungRepo.save(chatLieuKhung);
        return ResponseEntity.ok("Cập nhật trạng thái thành công.");
    }
}
