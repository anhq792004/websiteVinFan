package com.example.datn.controller.BanHang;

import com.example.datn.dto.request.AddKHToHDCTRequest;
import com.example.datn.dto.request.AddSPToHDCTRequest;
import com.example.datn.dto.request.ThanhToanRequest;
import com.example.datn.dto.request.UpdateSoLuongRequest;
import com.example.datn.entity.HoaDon.HoaDon;
import com.example.datn.entity.HoaDon.HoaDonChiTiet;
import com.example.datn.entity.KhachHang;
import com.example.datn.entity.SanPham.SanPhamChiTiet;
import com.example.datn.repository.KhachHangRepo.KhachHangRepo;
import com.example.datn.service.BanHang.BanHangService;
import com.example.datn.service.HoaDonService.HoaDonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/sale")
@RequiredArgsConstructor
public class BanHangTaiQuayController {

    private final BanHangService banHangService;
    private final HoaDonService hoaDonService;
    private final KhachHangRepo khachHangRepo;


    @GetMapping("index")
    public String index(Model model) {
        //hiển thị danh sách hóa đơn chờ
        List<HoaDon> listHoaDon = banHangService.findHoaDon();
        model.addAttribute("listHoaDon", listHoaDon);

        return "admin/sale/index";
    }

    @GetMapping("/hdct")
    public String viewHDCT(Model model, @RequestParam("idHD") Long idHD) {
        Optional<HoaDon> hoaDonOptional = hoaDonService.findHoaDonById(idHD);
        HoaDon hoaDon = hoaDonOptional.orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy hóa đơn"));
        model.addAttribute("hoaDon", hoaDon);
        // Hiển thị thông tin khách hàng
        KhachHang khachHang = hoaDon.getKhachHang();
        model.addAttribute("khachHang", khachHang);
        //List khách hàng
        model.addAttribute("listsKhachhang", khachHangRepo.findAll());
        // Hiển thị danh sách hóa đơn chờ
        List<HoaDon> listHoaDon = banHangService.findHoaDon();
        model.addAttribute("listHoaDon", listHoaDon);
        // Hiển thị danh sách sản phẩm
        List<SanPhamChiTiet> findSPCTByIdSanPham = hoaDonService.findSPCTByIdSanPham();
        model.addAttribute("findSPCTByIdSanPham", findSPCTByIdSanPham);
        //Hiển thị danh sách hdct theo id hóa đơn
        List<HoaDonChiTiet> listHDCT = hoaDonService.listHoaDonChiTiets(idHD);
        model.addAttribute("listHDCT", listHDCT);
        // Thêm ID hóa đơn được chọn vào model
        model.addAttribute("idHD", idHD);

        return "admin/sale/index";
    }

    @PostMapping("/tao-hoa-don")
    public ResponseEntity<String> taoHoaDon() {
        try {
            HoaDon hoaDon = new HoaDon();
            banHangService.taoHoaDonCho(hoaDon);
            return ResponseEntity.ok("Tạo hóa đơn thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Số lượng hóa đơn chờ < 10");
        }
    }

    @PostMapping("/huy")
    @ResponseBody
    public ResponseEntity<String> huy(@RequestParam("id") Long id) {
        hoaDonService.huy(id);
        return ResponseEntity.ok("Hóa đơn đã được hủy !");
    }

    @PostMapping("/addSP")
    public ResponseEntity<String> addSP(@RequestBody AddSPToHDCTRequest addSPToHDCTRequest) {
        try {
            hoaDonService.addSPToHDCT(addSPToHDCTRequest);
            banHangService.updateTongTienHoaDon(addSPToHDCTRequest.getIdHD());
            return ResponseEntity.ok("Thêm sản phẩm thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi: " + e.getMessage());
        }
    }

    @PostMapping("/addKH")
    public ResponseEntity<String> addKH(@RequestBody AddKHToHDCTRequest addKHToHDCTRequest) {
        try {
            hoaDonService.addKHToHDCT(addKHToHDCTRequest);
            return ResponseEntity.ok("Thêm khách hàng thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi: " + e.getMessage());
        }
    }

    @PostMapping("/xoa")
    @ResponseBody
    public ResponseEntity<String> deleteChiTiet(@RequestParam("idSP") Long idSP,
                                                @RequestParam("idHD") Long idHD) {
        try {
            hoaDonService.deleteSPInHD(idSP);
            banHangService.updateTongTienHoaDon(idHD);
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
            hoaDonService.tangSoLuong(idHD, idSP);
            banHangService.updateTongTienHoaDon(idHD);
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
            hoaDonService.giamSoLuong(idHD, idSP);
            banHangService.updateTongTienHoaDon(idHD);

            return ResponseEntity.ok("Giảm số lượng thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/updateSoLuong")
    @ResponseBody
    public ResponseEntity<String> updateSoLuong(UpdateSoLuongRequest request) {
        hoaDonService.updateSoluong(request);
        banHangService.updateTongTienHoaDon(request.getIdHD());

        return ResponseEntity.ok("Cập nhật thành công");
    }

    @PostMapping("/thanh-toan")
    @ResponseBody
    public ResponseEntity<String> thanhToan(@RequestParam("idHD") Long idHD) {
        try {
            banHangService.thanhToan(idHD);
            return ResponseEntity.ok("Thanh toán thành công!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}

