package com.example.datn.controller.ThuocTinhController;

import com.example.datn.entity.ThuocTinh.KieuQuat;
import com.example.datn.repository.ThuocTinhRepo.KieuQuatRepo;
import com.example.datn.service.ThuocTinhService.KieuQuatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/kieu-quat")
@RequiredArgsConstructor
public class KieuQuatController {
    private final KieuQuatRepo kieuQuatRepo;
    private final KieuQuatService  kieuQuatService;

    @ModelAttribute("listKieuQuat")
    public List<KieuQuat> listKieuQuat() {
        return kieuQuatRepo.findAll();
    }


    @GetMapping("/index")
    public String timKiem(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "status", defaultValue = "") Boolean status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model
    ){
        Page<KieuQuat> searchPage = kieuQuatService.search(name.trim(), status, PageRequest.of(page, size));
        if (searchPage.isEmpty() && page < 0) {
            searchPage = kieuQuatService.search(name.trim(), status, PageRequest.of(0, size));
        }
        model.addAttribute("list", searchPage);
        model.addAttribute("name", name);
        model.addAttribute("status", status != null ? status : "");
        return "admin/thuoc_tinh/kieu_quat";
    }

    @PostMapping("/add")
    public ResponseEntity<?> themMoi(@RequestParam(value = "name", required = true) String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Tên không được để trống");
        }
        return kieuQuatService.add(name.trim());
    }
    
    @PostMapping("/update")
    public ResponseEntity<?> capNhat(
            @RequestParam(value = "id", required = true) Long id,
            @RequestParam(value = "name", required = true) String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Tên không được để trống");
        }
        KieuQuat kieuQuat = kieuQuatService.findById(id);
        if (kieuQuat == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy kiểu quạt");
        }
        kieuQuat.setTen(name.trim());
        kieuQuatService.save(kieuQuat);
        return ResponseEntity.ok("Cập nhật kiểu quạt thành công.");
    }
    
    @PostMapping("/change-status")
    public ResponseEntity<?> thayDoiTrangThai(@RequestParam(value = "id", required = true) Long id) {
        return kieuQuatService.changeStatus(id);
    }
}
