package com.example.datn.controller.SanPhamController;

import com.example.datn.entity.SanPham.SanPham;
import com.example.datn.entity.SanPham.SanPhamChiTiet;
import com.example.datn.entity.ThuocTinh.KieuQuat;
import com.example.datn.service.ThuocTinhService.KieuQuatService;
import com.example.datn.service.ThuocTinhService.MauSacService;
import com.example.datn.service.ThuocTinhService.CongSuatService;
import com.example.datn.service.ThuocTinhService.HangService;
import com.example.datn.service.ThuocTinhService.NutBamService;
import com.example.datn.service.SanPhamSerivce.SanPhamService;
import com.example.datn.service.SanPhamSerivce.SanPhamChiTietService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/san-pham")
public class SanPhamController {
    private final SanPhamService sanPhamService;
    private final KieuQuatService kieuQuatService;
    private final SanPhamChiTietService sanPhamChiTietService;
    private final MauSacService mauSacService;
    private final CongSuatService congSuatService;
    private final HangService hangService;
    private final NutBamService nutBamService;


    @GetMapping("/list")
    public String getAllSanPham(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @RequestParam(value = "search", required = false) String search,
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
    
    @GetMapping("/add")
    public String showAddSanPhamForm(Model model) {
        model.addAttribute("sanPham", new SanPham());
        model.addAttribute("kieuQuat", kieuQuatService.findAllKieuQuat());
        return "admin/san_pham/add";
    }
    
    @PostMapping("/add")
    public String addSanPham(
            @ModelAttribute SanPham sanPham,
            @RequestParam(value = "hinhAnh", required = false) MultipartFile hinhAnh) {
        
        // Set ngày tạo cho sản phẩm -> Lấy ngày hiện tại
        sanPham.setNgayTao(LocalDateTime.now());
        
        // Lưu hình ảnh nếu có
        if (hinhAnh != null && !hinhAnh.isEmpty()) {
            // Xử lý lưu hình ảnh trong service
            sanPhamService.saveSanPhamWithImage(sanPham, hinhAnh);
        } else {
            sanPhamService.saveSanPham(sanPham);
        }
        
        return "redirect:/admin/san-pham/list";
    }
    
    @GetMapping("/edit")
    public String showEditSanPhamForm(@RequestParam(value = "id") Long id, Model model) {
        Optional<SanPham> sanPhamOptional = sanPhamService.findSanPhamById(id);
        if (sanPhamOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm");
        }
        model.addAttribute("sanPham", sanPhamOptional.get());
        model.addAttribute("kieuQuat", kieuQuatService.findAllKieuQuat());
        return "admin/san_pham/edit";
    }
    
    @PostMapping("/update")
    public String updateSanPham(
            @ModelAttribute SanPham sanPham,
            @RequestParam(value = "hinhAnh", required = false) MultipartFile hinhAnh) {
        
        // Lưu hình ảnh nếu có
        if (hinhAnh != null && !hinhAnh.isEmpty()) {
            // Xử lý lưu hình ảnh trong service
            sanPhamService.updateSanPhamWithImage(sanPham, hinhAnh);
        } else {
            sanPhamService.updateSanPham(sanPham);
        }
        
        return "redirect:/admin/san-pham/list";
    }

    @PostMapping("/them")
    @ResponseBody
    public ResponseEntity<String> addSanPhamAPI(@RequestBody SanPham sanPham) {
        // Set ngày tạo cho sản phẩm -> Lấy ngày hiện tại
        sanPham.setNgayTao(LocalDateTime.now());
        sanPhamService.saveSanPham(sanPham);
        return ResponseEntity.ok("Thêm sản phẩm thành công");
    }

    @PostMapping("/sua")
    @ResponseBody
    public ResponseEntity<String> updateSanPhamAPI(@RequestBody SanPham sanPham) {
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
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<KieuQuat>> getKieuQuat() {
        List<KieuQuat> kieuQuatList = kieuQuatService.findAllKieuQuat();
        return ResponseEntity.ok(kieuQuatList);
    }

    // Thêm biến thể sản phẩm
    @GetMapping("/chi-tiet/add")
    public String showAddVariantForm(@RequestParam(value = "productId") Long productId, Model model) {
        Optional<SanPham> sanPhamOptional = sanPhamService.findSanPhamById(productId);
        if (sanPhamOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm");
        }
        
        model.addAttribute("sanPham", sanPhamOptional.get());
        model.addAttribute("sanPhamChiTiet", new SanPhamChiTiet());
        model.addAttribute("mauSacList", mauSacService.findAllMauSac());
        model.addAttribute("congSuatList", congSuatService.findAllCongSuat());
        model.addAttribute("hangList", hangService.findAllHang());
        model.addAttribute("nutBamList", nutBamService.findAll());
        
        return "admin/san_pham/add-variant";
    }

    // Sửa biến thể sản phẩm
    @GetMapping("/chi-tiet/edit")
    public String showEditVariantForm(@RequestParam(value = "id") Long id, Model model) {
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietService.findById(id);
        if (sanPhamChiTiet == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy biến thể sản phẩm");
        }
        
        model.addAttribute("sanPham", sanPhamChiTiet.getSanPham());
        model.addAttribute("sanPhamChiTiet", sanPhamChiTiet);
        model.addAttribute("mauSacList", mauSacService.findAllMauSac());
        model.addAttribute("congSuatList", congSuatService.findAllCongSuat());
        model.addAttribute("hangList", hangService.findAllHang());
        model.addAttribute("nutBamList", nutBamService.findAll());
        
        return "admin/san_pham/edit-variant";
    }
}