package com.example.datn.service.Implements.ThuocTinhImpl;

import com.example.datn.entity.ThuocTinh.ChatLieuCanh;
import com.example.datn.repository.ThuocTinhRepo.ChatLieuCanhRepo;
import com.example.datn.service.ThuocTinhService.ChatLieuCanhService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}
