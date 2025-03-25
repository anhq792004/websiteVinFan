package com.example.datn.controller.ThuocTinhController;

import com.example.datn.entity.NhanVien;
import com.example.datn.entity.ThuocTinh.ChatLieuCanh;
import com.example.datn.repository.ThuocTinhRepo.ChatLieuCanhRepo;
import com.example.datn.service.ThuocTinhService.ChatLieuCanhService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<?> add(@RequestParam("nameAdd") String nameAdd) {
        return chatLieuCanhService.add(nameAdd);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> thongTin(@RequestParam("id") Long id) {
        Optional<ChatLieuCanh> chatLieuCanh = chatLieuCanhService.getAll(id);
        if (chatLieuCanh.isPresent()) {
            return ResponseEntity.ok(chatLieuCanh.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tồn tại chất liệu cánh.");
        }
    }
    @PostMapping("/update")
    public ResponseEntity<String> updateChatLieuCanh(
            @RequestParam("id") Long id,
            @RequestParam("nameUpdate") String nameUpdate) {
        return chatLieuCanhService.update(id, nameUpdate);
    }
}
