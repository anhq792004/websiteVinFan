package com.example.datn.controller;

import com.example.datn.entity.ChucVu;
import com.example.datn.entity.DiaChi;
import com.example.datn.entity.NhanVien.NhanVien;
import com.example.datn.repository.DiaChiRepo;
import com.example.datn.service.ChucVuService.ChucVuService;
import com.example.datn.service.NhanVienService.NhanVienService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/nhan-vien")
public class NhanVienController {
    private final NhanVienService nhanVienService;
    private final ChucVuService chucVuService;
    private final DiaChiRepo diaChiRepo;

    @ModelAttribute("listDiaChi")
    List<DiaChi> getListDiaChi() {
        return diaChiRepo.findAll();
    }

    @GetMapping("/index")
    public String getAllNhanVien(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(name = "chucVu", required = false) Long chucVuId,
            @RequestParam(name = "trangThai", required = false) Boolean trangThai,
            Model model
    ) {
        Page<NhanVien> nhanVienPage = nhanVienService.findAllNhanVien(page, size, search, chucVuId, trangThai);
        System.out.println("Số lượng nhân viên: " + nhanVienPage.getTotalElements());
        System.out.println("Tổng số trang: " + nhanVienPage.getTotalPages());
        model.addAttribute("nhanVienPage", nhanVienPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", nhanVienPage.getTotalPages()); // Fixed: Use getTotalPages() instead of getTotalElements()
        model.addAttribute("search", search);
        model.addAttribute("chucVu", chucVuId);
        model.addAttribute("trangThai", trangThai);
        if (!nhanVienPage.getContent().isEmpty()) {
            System.out.println("Nhân viên đầu tiên: " + nhanVienPage.getContent().get(0));
        }
        return "admin/nhan_vien/index";
    }

    @GetMapping("/view-them")
    public String showThemNhanVienForm(Model model) {
        model.addAttribute("nhanVien", new NhanVien());
        model.addAttribute("listChucVu", chucVuService.findAllChucVu());
        return "admin/nhan_vien/view-them";
    }

    @PostMapping("/them")
    public String addNhanVien(
            @ModelAttribute("nhanVien") @Valid  NhanVien nhanVien,
            BindingResult bindingResult, Model model){
    if (bindingResult.hasErrors()) {
            return "admin/nhan_vien/view-them";
        }
        ChucVu defaultChucVu = chucVuService.findChucVuByViTri("Nhân viên"); // Assuming this method exists
         if (defaultChucVu == null) {
            // Handle case where "Nhân viên" role doesn't exist
            model.addAttribute("error", "Chức vụ 'Nhân viên' không tồn tại. Vui lòng tạo chức vụ này trước.");
            return "admin/nhan_vien/view-them";
        }
        nhanVien.setChucVu(defaultChucVu);
        nhanVienService.saveNhanVien(nhanVien);
        return "redirect:/admin/nhan_vien/index";
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<String> updateNhanVien(@RequestBody NhanVien nhanVien) {
        nhanVienService.updateNhanVien(nhanVien);
        return ResponseEntity.ok("Sửa nhân viên thành công");
    }

    @GetMapping("/detail/{id}")
    public String detailNhanVienById(@PathVariable("id") Long id, Model model) {
         model.addAttribute("nhanVien",nhanVienService.findNhanVienById(id));
        return "/admin/nhan_vien/detail";
    }

    @GetMapping("/api/chuc-vu")
    @ResponseBody
    public ResponseEntity<List<ChucVu>> getChucVu() {
        List<ChucVu> chucVuList = chucVuService.findAllChucVu();
        return ResponseEntity.ok(chucVuList);
    }
//    @GetMapping("/delete")
//    public String deletaNhanVien(@RequestParam("id")NhanVien nhanVien){
//        nhanVienService.deleteNhanVien(nhanVien.getId());
//        return "admin/nhan_vien/index";
//
//    }
}