package com.example.datn.controller.BanHang;

import com.example.datn.entity.HoaDon.HoaDon;
import com.example.datn.entity.NhanVien;
import com.example.datn.service.BanHang.BanHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/sale")
@RequiredArgsConstructor
public class BanHangTaiQuayController {

    private final BanHangService banHangService;


    @GetMapping("index")
    public String index(Model model) {
        //hiển thị danh sách hóa đơn chờ
        List<HoaDon> listHoaDon = banHangService.findHoaDon();
        model.addAttribute("listHoaDon", listHoaDon);

        return "admin/sale/index";
    }

    @GetMapping("/hdct")
    public String viewHDCT(Model model, @RequestParam("idHD") Long idHD) {
        // Hiển thị danh sách hóa đơn chờ
        List<HoaDon> listHoaDon = banHangService.findHoaDon();
        model.addAttribute("listHoaDon", listHoaDon);
        // Thêm ID hóa đơn được chọn vào model
        model.addAttribute("idHD", idHD);
        return "admin/sale/index";
    }

    @PostMapping("/tao-hoa-don")
    public ResponseEntity<?> taoHoaDon() {
        try {
            HoaDon hoaDon = new HoaDon();
            banHangService.taoHoaDonCho(hoaDon);
            return ResponseEntity.ok("Tạo hóa đơn thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Số lượng hóa đơn chờ < 10");
        }
    }
}

