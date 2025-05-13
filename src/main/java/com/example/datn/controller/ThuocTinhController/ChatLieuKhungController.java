package com.example.datn.controller.ThuocTinhController;

import com.example.datn.entity.ThuocTinh.ChatLieuKhung;
import com.example.datn.repository.ThuocTinhRepo.ChatLieuKhungRepo;
import com.example.datn.service.ThuocTinhService.ChatLieuKhungService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/chat-lieu-khung")
@RequiredArgsConstructor
public class ChatLieuKhungController {
    private final ChatLieuKhungRepo chatLieuKhungRepo;
    private final ChatLieuKhungService chatLieuKhungService;

    @ModelAttribute("listChatLieuKhung")
    public List<ChatLieuKhung> listChatLieuKhung() {
        return chatLieuKhungRepo.findAll();
    }

    @GetMapping("/index")
    public String timKiem(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "status", defaultValue = "") Boolean status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model
    ){
        Page<ChatLieuKhung> searchPage = chatLieuKhungService.search(name.trim(), status, PageRequest.of(page, size));
        if (searchPage.isEmpty() && page < 0) {
            searchPage = chatLieuKhungService.search(name.trim(), status, PageRequest.of(0, size));
        }
        model.addAttribute("list", searchPage);
        model.addAttribute("name", name);
        model.addAttribute("status", status != null ? status : "");
        return "admin/thuoc_tinh/chat_lieu_khung";
    }

    @PostMapping("/add")
    public ResponseEntity<?> themMoi(@RequestParam(value = "name", required = true) String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Tên không được để trống");
        }
        return chatLieuKhungService.add(name.trim());
    }
@PostMapping("/update")
public ResponseEntity<String> capNhat(
        @RequestParam(value = "id", required = true) Long id,
        @RequestParam(value = "nameUpdate", required = true) String nameUpdate) {
    if (id == null) {
        return ResponseEntity.badRequest().body("Không tìm thấy id!");
    }
    return chatLieuKhungService.update(id, nameUpdate.trim());
}

    @PostMapping("/change-status")
    public ResponseEntity<?> thayDoiTrangThai(@RequestParam(value = "id", required = true) Long id) {
        return chatLieuKhungService.changeStatus(id);
    }
}
