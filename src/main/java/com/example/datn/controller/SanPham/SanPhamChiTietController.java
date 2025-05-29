package com.example.datn.controller.SanPham;

import com.example.datn.entity.SanPham.SanPhamChiTiet;
import com.example.datn.service.SanPhamSerivce.SanPhamChiTietService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/san-pham-chi-tiet")
public class SanPhamChiTietController {
    
    private final SanPhamChiTietService sanPhamChiTietService;

    @GetMapping("/{id}")
    public ResponseEntity<SanPhamChiTiet> getById(@PathVariable Long id) {
        SanPhamChiTiet spct = sanPhamChiTietService.findById(id);
        if (spct != null) {
            return ResponseEntity.ok(spct);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                  @RequestParam(required = false) Long mauSacId,
                                  @RequestParam(required = false) Long congSuatId,
                                  @RequestParam(required = false) Long hangId,
                                  @RequestParam(required = false) Long nutBamId,
                                  @RequestParam(required = false) Integer soLuong,
                                  @RequestParam(required = false) Double gia,
                                  @RequestParam(required = false) Double canNang,
                                  @RequestParam(required = false) Boolean trangThai,
                                  @RequestParam(required = false) String moTa,
                                  @RequestParam(required = false) MultipartFile hinhAnh) {
        try {
            sanPhamChiTietService.update(id, mauSacId, congSuatId, hangId, nutBamId, 
                    soLuong, gia, canNang, trangThai, moTa, hinhAnh);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            sanPhamChiTietService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 