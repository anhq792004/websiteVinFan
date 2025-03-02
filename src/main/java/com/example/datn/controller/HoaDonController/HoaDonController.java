package com.example.datn.controller.HoaDonController;

import com.example.datn.entity.HoaDon.HoaDon;
import com.example.datn.entity.HoaDon.LichSuHoaDon;
import com.example.datn.service.HoaDonService.HoaDonSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
@RequestMapping("/hoa-don")
public class HoaDonController {
    private final HoaDonSerivce hoaDonSerivce;

    @GetMapping("/index")
    public String getAllHoaDon(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model
    ) {
        Page<HoaDon> listHoaDon = hoaDonSerivce.findAllHoaDonAndSortDay(page, size);
        model.addAttribute("list", listHoaDon);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", listHoaDon.getTotalPages());
        return "admin/hoa_don/index";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam Long id, Model model) {
        Optional<HoaDon> hoaDonOptional = hoaDonSerivce.findHoaDonById(id);
        HoaDon hoaDon = hoaDonOptional.orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy hóa đơn"));
        model.addAttribute("hoaDon", hoaDon);

        List<LichSuHoaDon> lichSuHoaDonList = hoaDonSerivce.lichSuHoaDonList(id);
        model.addAttribute("lichSuHoaDonList", lichSuHoaDonList);

        return "admin/hoa_don/detail";
    }

    @PostMapping("/xac-nhan")
    public ResponseEntity<?> xacNhan(@RequestParam("id") Long id) {
        try {
            hoaDonSerivce.xacNhan(id);
            return ResponseEntity.ok("Xác nhận hóa đơn thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Số lượng sản phẩm không đủ, Vui lòng thử lại");
        }
    }

    @PostMapping("/giao-hang")
    public ResponseEntity<?> giaoHang(@RequestParam Long id) {
        hoaDonSerivce.giaoHang(id);
        return ResponseEntity.ok("Thành công");
    }

    @PostMapping("/hoan-thanh")
    public ResponseEntity<?> hoanThanh(@RequestParam Long id) {
        hoaDonSerivce.hoanThanh(id);
        return ResponseEntity.ok("Thành công");
    }

    @PostMapping("/huy")
    public ResponseEntity<?> huy(@RequestParam Long id) {
        hoaDonSerivce.huy(id);
        return ResponseEntity.ok("Thành công");

    }
}
