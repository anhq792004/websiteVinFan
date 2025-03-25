package com.example.datn.controller;

import com.example.datn.entity.SanPham.SanPham;
import com.example.datn.entity.ThuocTinh.KieuQuat;
import com.example.datn.service.ThuocTinhService.KieuQuatService;
import com.example.datn.service.SanPhamSerivce.SanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/san-pham")
public class SanPhamController {
    private final SanPhamService sanPhamService;
    private final KieuQuatService kieuQuatService;


    @GetMapping("/list")
    public String getAllSanPham(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @RequestParam(value = "search", required = false) String search, // Thêm search
            @RequestParam(value = "kieuQuat", required = false) Long kieuQuatId,
            @RequestParam(value = "trangThai", required = false) Boolean trangThai,
            Model model
    ) {
        Page<SanPham> sanPhamPage = sanPhamService.findAllSanPham(page, size, search, kieuQuatId, trangThai);
        System.out.println("Số lượng sản phẩm: " + sanPhamPage.getTotalElements());
        System.out.println("Tổng số trang: " + sanPhamPage.getContent());
        model.addAttribute("sanPhamPage", sanPhamPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", sanPhamPage.getTotalPages());
        model.addAttribute("search", search);
        model.addAttribute("kieuQuat", kieuQuatId);
        model.addAttribute("trangThai", trangThai);
        if (!sanPhamPage.getContent().isEmpty()) {
            System.out.println("Sản phẩm đầu tiên: " + sanPhamPage.getContent().get(0));
        }
        return "admin/san_pham/index";
    }

    @GetMapping("/detail")
    public String getSanPhamDetail(@RequestParam(value = "id") Long id, Model model) {
        Optional<SanPham> sanPhamOptional = sanPhamService.findSanPhamById(id);
        if (sanPhamOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm");
        }
        model.addAttribute("sanPham", sanPhamOptional.get());
        return "admin/san_pham/detail";
    }

    @PostMapping("/them")
    @ResponseBody
    public ResponseEntity<String> addSanPham(@RequestBody SanPham sanPham) {
        // Set ngày tạo cho sản phẩm -> Lấy ngày hiện tại
        sanPham.setNgayTao(LocalDateTime.now());
        sanPhamService.saveSanPham(sanPham);
        return ResponseEntity.ok("Thêm sản phẩm thành công");
    }

    @PostMapping("/sua")
    @ResponseBody
    public ResponseEntity<String> updateSanPham(@RequestBody SanPham sanPham) {
        sanPhamService.updateSanPham(sanPham);
        return ResponseEntity.ok("Sửa sản phẩm thành công");
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseEntity<SanPham> getSanPhamById(@PathVariable("id") Long id) {
        Optional<SanPham> sanPhamOptional = sanPhamService.findSanPhamById(id);
        return sanPhamOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/thay-doi-trang-thai/{id}")
    @ResponseBody
    public ResponseEntity<String> thayDoiTrangThaiSanPham(@PathVariable("id") Long id) {
        boolean result = sanPhamService.thayDoiTrangThaiSanPham(id);
        if (result) {
            return ResponseEntity.ok("Thay đổi trạng thái sản phẩm thành công");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy sản phẩm");
        }
    }

    @GetMapping("/api/kieu-quat")
    @ResponseBody
    public ResponseEntity<List<KieuQuat>> getKieuQuat() {
        List<KieuQuat> kieuQuatList = kieuQuatService.findAllKieuQuat();
        return ResponseEntity.ok(kieuQuatList);
    }
}