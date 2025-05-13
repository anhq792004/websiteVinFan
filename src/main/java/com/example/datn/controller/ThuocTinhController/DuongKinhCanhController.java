package com.example.datn.controller.ThuocTinhController;

import com.example.datn.entity.ThuocTinh.DuongKinhCanh;
import com.example.datn.repository.ThuocTinhRepo.DuongKinhCanhRepo;
import com.example.datn.service.ThuocTinhService.DuongKinhCanhService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/duong-kinh-canh")
@RequiredArgsConstructor
public class DuongKinhCanhController {
    private final DuongKinhCanhRepo duongKinhCanhRepo;
    private final DuongKinhCanhService duongKinhCanhService;

    @ModelAttribute("listDuongKinhCanh")
    public List<DuongKinhCanh> listDuongKinhCanh() {
        return duongKinhCanhRepo.findAll();
    }

    @GetMapping("/index")
    public String timKiem(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "status", defaultValue = "") Boolean status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model
    ){
        Page<DuongKinhCanh> searchPage = duongKinhCanhService.search(name.trim(), status, PageRequest.of(page, size));
        if (searchPage.isEmpty() && page < 0) {
            searchPage = duongKinhCanhService.search(name.trim(), status, PageRequest.of(0, size));
        }
        model.addAttribute("list", searchPage);
        model.addAttribute("name", name);
        model.addAttribute("status", status != null ? status : "");
        return "admin/thuoc_tinh/duong_kinh_canh";
    }

    @GetMapping("/find-by-id")
    @ResponseBody
    public DuongKinhCanh findById(@RequestParam("id") Integer id) {
        return duongKinhCanhService.findById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<?> themMoi(@RequestParam(value = "name", required = true) String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Tên không được để trống");
        }
        return duongKinhCanhService.add(name.trim());
    }

    @PostMapping("/update")
    public ResponseEntity<?> capNhat(
            @RequestParam(value = "id", required = true) Integer id,
            @RequestParam(value = "name", required = true) String name) {
        return duongKinhCanhService.update(id, name.trim());
    }

    @PostMapping("/change-status")
    public ResponseEntity<?> thayDoiTrangThai(@RequestParam(value = "id", required = true) Integer id) {
        return duongKinhCanhService.changeStatus(id);
    }
}
