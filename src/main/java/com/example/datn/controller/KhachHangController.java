package com.example.datn.controller;


import com.example.datn.dto.request.AddKhachHangRequest;
import com.example.datn.entity.KhachHang;
import com.example.datn.service.KhachHangService.KhachHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/khach-hang")
public class KhachHangController {
    private final KhachHangService khachHangService;

    @GetMapping("/index")
    public String getAllKhachHang(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(name = "trangThai", defaultValue = "") Boolean trangThai,
            Model model) {
        if (page < 0) {
            page = 0;
        }
        PageRequest pageable = PageRequest.of(page, size);
        Page<KhachHang> listKH = khachHangService.findAll(search, trangThai, pageable);
        model.addAttribute("listKH", listKH);
        return "admin/khach_hang/index";
    }

    @GetMapping("/view-them")
    public String showThemKhachHangForm(Model model) {
        model.addAttribute("khachHang", new KhachHang());
        return "admin/khach_hang/view-them";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam Long id) {

        return "admin/khach_hang/detail";
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody AddKhachHangRequest request) {
        try {
            khachHangService.addKH(request);
            return ResponseEntity.ok().body("Thêm khách hàng thành công!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
