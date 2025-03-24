package com.example.datn.controller.ThuocTinhController;

import com.example.datn.entity.NhanVien;
import com.example.datn.entity.ThuocTinh.ChatLieuCanh;
import com.example.datn.repository.ThuocTinhRepo.ChatLieuCanhRepo;
import com.example.datn.service.ThuocTinhService.ChatLieuCanhService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/chat-lieu-canh")
@RequiredArgsConstructor
public class ChatLieuCanhController {

    private final ChatLieuCanhRepo chatLieuCanhRepo;

    private final ChatLieuCanhService chatLieuCanhService;

    @ModelAttribute("listChatLieuCanh")
    public List<ChatLieuCanh> listChatLieuCanh() {
        return chatLieuCanhRepo.findAll();
    }

    @GetMapping("/index")
    public String timKiem(@RequestParam(value = "name", defaultValue = "") String name,
                          @RequestParam(value = "status", defaultValue = "") Boolean status,
                          @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "5") int size,
                          Model model) {
        Page<ChatLieuCanh> searchPage = chatLieuCanhService.search(name.trim(), status, PageRequest.of(page, size));
        if (searchPage.isEmpty() && page < 0) {
            searchPage = chatLieuCanhService.search(name.trim(), status, PageRequest.of(0, size));
        }
        model.addAttribute("list", searchPage);
        model.addAttribute("name", name);
        model.addAttribute("status", status != null ? status : "");
        return "admin/thuoc_tinh/chat_lieu_canh";
    }

    @PostMapping("/add")
    public ResponseEntity<?> themMoi(@RequestParam("name") String name) {
        return chatLieuCanhService.add(name);
    }
}
