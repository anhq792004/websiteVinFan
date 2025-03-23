package com.example.datn.controller.HoaDonController;

import com.example.datn.dto.response.LichSuThanhToanResponse;
import com.example.datn.entity.HoaDon.HoaDon;
import com.example.datn.entity.HoaDon.HoaDonChiTiet;
import com.example.datn.entity.HoaDon.LichSuHoaDon;
import com.example.datn.service.HoaDonService.HoaDonSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

        LichSuThanhToanResponse lichSuThanhToanResponse = hoaDonSerivce.getLSTTByHoaDonId(id);
        model.addAttribute("listLSTT", lichSuThanhToanResponse);

        List<HoaDonChiTiet> listHDCT = hoaDonSerivce.listHoaDonChiTiets(id);
        model.addAttribute("listHDCT", listHDCT);

        return "admin/hoa_don/detail";
    }

    @PostMapping("/xac-nhan")
    @ResponseBody
    public ResponseEntity<String> xacNhanHoaDon(@RequestParam("id") Long id) {
        try {
            hoaDonSerivce.xacNhan(id);
            return ResponseEntity.ok("Đơn hàng đã được xác nhận !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Số lượng sản phẩm không đủ");
        }
    }

    @PostMapping("/giao-hang")
    @ResponseBody
    public ResponseEntity<String> giaoHang(@RequestParam("id") Long id) {
        hoaDonSerivce.giaoHang(id);
        return ResponseEntity.ok("Đơn hàng đã được giao thành công !");
    }

    @PostMapping("/hoan-thanh")
    @ResponseBody
    public ResponseEntity<String> hoanThanh(@RequestParam("id") Long id) {
        hoaDonSerivce.hoanThanh(id);
        return ResponseEntity.ok("Đơn hàng đã hoàn thành !");
    }

    @PostMapping("/huy")
    @ResponseBody
    public ResponseEntity<String> huy(@RequestParam("id") Long id) {
        hoaDonSerivce.huy(id);
        return ResponseEntity.ok("Hóa đơn đã được hủy !");
    }
}
