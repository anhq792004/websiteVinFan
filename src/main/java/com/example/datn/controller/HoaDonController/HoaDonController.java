package com.example.datn.controller.HoaDonController;

import com.example.datn.dto.request.AddSPToHDCTRequest;
import com.example.datn.dto.request.UpdateSoLuongRequest;
import com.example.datn.dto.response.LichSuThanhToanResponse;
import com.example.datn.entity.HoaDon.HoaDon;
import com.example.datn.entity.HoaDon.HoaDonChiTiet;
import com.example.datn.entity.HoaDon.LichSuHoaDon;
import com.example.datn.entity.SanPham.SanPhamChiTiet;
import com.example.datn.repository.HoaDonRepo.HoaDonChiTietRepo;
import com.example.datn.service.HoaDonService.HoaDonService;
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
    private final HoaDonService hoaDonService;
    private final HoaDonChiTietRepo hoaDonChiTietRepo;


    @GetMapping("/index")
    public String getAllHoaDon(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model
    ) {
        if (page < 0) {
            page = 0; // Tránh giá trị âm
        }
        Page<HoaDon> listHoaDon = hoaDonService.findAllHoaDonAndSortDay(page, size);
        model.addAttribute("list", listHoaDon);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", listHoaDon.getTotalPages());
        return "admin/hoa_don/index";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam Long id, Model model) {
        Optional<HoaDon> hoaDonOptional = hoaDonService.findHoaDonById(id);
        HoaDon hoaDon = hoaDonOptional.orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy hóa đơn"));
        model.addAttribute("hoaDon", hoaDon);

        List<LichSuHoaDon> lichSuHoaDonList = hoaDonService.lichSuHoaDonList(id);
        model.addAttribute("lichSuHoaDonList", lichSuHoaDonList);

        LichSuThanhToanResponse lichSuThanhToanResponse = hoaDonService.getLSTTByHoaDonId(id);
        model.addAttribute("listLSTT", lichSuThanhToanResponse);

        List<HoaDonChiTiet> listHDCT = hoaDonService.listHoaDonChiTiets(id);
        model.addAttribute("listHDCT", listHDCT);

        List<SanPhamChiTiet> findSPCTByIdSanPham = hoaDonService.findSPCTByIdSanPham();
        model.addAttribute("findSPCTByIdSanPham", findSPCTByIdSanPham);

        Integer tongSoLuong = hoaDonService.tongSoLuong(id);
        model.addAttribute("tongSoLuong",tongSoLuong);

        return "admin/hoa_don/detail";
    }

    @PostMapping("/xac-nhan")
    @ResponseBody
    public ResponseEntity<String> xacNhanHoaDon(@RequestParam("id") Long id) {
        try {
            hoaDonService.xacNhan(id);
            return ResponseEntity.ok("Đơn hàng đã được xác nhận !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Số lượng sản phẩm không đủ");
        }
    }

    @PostMapping("/giao-hang")
    @ResponseBody
    public ResponseEntity<String> giaoHang(@RequestParam("id") Long id) {
        hoaDonService.giaoHang(id);
        return ResponseEntity.ok("Đơn hàng đã được giao thành công !");
    }

    @PostMapping("/hoan-thanh")
    @ResponseBody
    public ResponseEntity<String> hoanThanh(@RequestParam("id") Long id) {
        hoaDonService.hoanThanh(id);
        return ResponseEntity.ok("Đơn hàng đã hoàn thành !");
    }

    @PostMapping("/huy")
    @ResponseBody
    public ResponseEntity<String> huy(@RequestParam("id") Long id) {
        hoaDonService.huy(id);
        return ResponseEntity.ok("Hóa đơn đã được hủy !");
    }

    @PostMapping("/addSP")
    public ResponseEntity<String> addSP(@RequestBody AddSPToHDCTRequest addSPToHDCTRequest){
        try {
            hoaDonService.addSPToHDCT(addSPToHDCTRequest);
            return ResponseEntity.ok("Thêm sản phẩm thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi: " + e.getMessage());
        }
    }
    @PostMapping("/xoa")
    @ResponseBody
    public ResponseEntity<String> deleteChiTiet(@RequestParam("idSP") Long idSP) {
        try {
            hoaDonService.deleteSPInHD(idSP);
            return ResponseEntity.ok("Xóa thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi xóa sản phẩm");
        }
    }
    @PostMapping("/tangSoLuong")
    @ResponseBody
    public ResponseEntity<String> tangSoLuong(@RequestParam("idSP") Long idSP,
                                              @RequestParam("idHD") Long idHD) {
        try {
            hoaDonService.tangSoLuong(idHD,idSP);
            return ResponseEntity.ok("Tăng số lượng thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/giamSoLuong")
    @ResponseBody
    public ResponseEntity<String> giamSoLuong(@RequestParam("idSP") Long idSP,
                                              @RequestParam("idHD") Long idHD) {
        try {
            hoaDonService.giamSoLuong(idHD,idSP);
            return ResponseEntity.ok("Giảm số lượng thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/updateSoLuong")
    @ResponseBody
    public ResponseEntity<?> updateSoLuong(UpdateSoLuongRequest request) {
        hoaDonService.updateSoluong(request);
        return ResponseEntity.ok("Cập nhật thành công");
    }
}
