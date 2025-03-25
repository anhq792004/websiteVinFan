package com.example.datn.controller.ThuocTinhController;

import com.example.datn.entity.ThuocTinh.CongSuat;
import com.example.datn.repository.ThuocTinhRepo.CongSuatRepo;
import com.example.datn.service.ThuocTinhService.CongSuatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cong-suat")
@RequiredArgsConstructor
public class CongSuatController {
    private final CongSuatRepo congSuatRepo;
    private final CongSuatService congSuatService;

    @ModelAttribute("listCongSuat")
    public List<CongSuat> listCongSuat() {
        return congSuatRepo.findAll();
    }

    @GetMapping("/index")
    public String timKiem(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "status", defaultValue = "") Boolean status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model
    ){
        Page<CongSuat> searchPage = congSuatService.search(name.trim(), status, PageRequest.of(page, size));
        if (searchPage.isEmpty() && page < 0) {
            searchPage = congSuatService.search(name.trim(), status, PageRequest.of(0, size));
        }
        model.addAttribute("list", searchPage);
        model.addAttribute("name", name);
        model.addAttribute("status", status != null ? status : "");
        return "admin/thuoc_tinh/cong_suat";
    }

    @GetMapping("/find-by-id")
    @ResponseBody
    public CongSuat findById(@RequestParam("id") Integer id) {
        return congSuatService.findById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<?> themMoi(@RequestParam(value = "name", required = true) String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Tên không được để trống");
        }
        return congSuatService.add(name.trim());
    }

    @PostMapping("/update")
    public ResponseEntity<?> capNhat(
            @RequestParam(value = "id", required = true) Integer id,
            @RequestParam(value = "name", required = true) String name) {
        return congSuatService.update(id, name.trim());
    }

    @PostMapping("/change-status")
    public ResponseEntity<?> thayDoiTrangThai(@RequestParam(value = "id", required = true) Integer id) {
        return congSuatService.changeStatus(id);
    }
}