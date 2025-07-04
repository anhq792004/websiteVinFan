package com.example.datn.controller;

import com.example.datn.dto.request.AddNhanVienRequest;
import com.example.datn.entity.ChucVu;
import com.example.datn.entity.DiaChi;
import com.example.datn.entity.NhanVien.NhanVien;
import com.example.datn.repository.DiaChiRepo;
import com.example.datn.repository.NhanVienRepo;
import com.example.datn.service.ChucVuService.ChucVuService;
import com.example.datn.service.DiaChiService;
import com.example.datn.service.NhanVienService.NhanVienService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/nhan-vien")
public class NhanVienController {
    private final NhanVienService nhanVienService;
    private final NhanVienRepo nhanVienRepo;
    private final ChucVuService chucVuService;
    private final DiaChiRepo diaChiRepo;
    private final DiaChiService diaChiService;

    @ModelAttribute("listDiaChi")
    List<DiaChi> getListDiaChi() {
        return diaChiRepo.findAll();
    }

    @GetMapping("/index")
    public String getAllNhanVien(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(name = "chucVu", required = false) Long chucVuId,
            @RequestParam(name = "trangThai", required = false) Boolean trangThai,
            Model model

    ) {
//        if (page < 0){
//            page= 0;
//        }
//        PageRequest pageable= PageRequest.of(page,size);
//        Page<NhanVien> listNV= nhanVienService.findAllNhanVien(search,trangThai, pageable);
//        System.out.println("listnv" + listNV);
//        model.addAttribute("listNV",listNV);
////    {
////        Page<NhanVien> nhanVienPage = nhanVienService.findAllNhanVien(page, size, search, chucVuId, trangThai);
////        System.out.println("Số lượng nhân viên: " + nhanVienPage.getTotalElements());
////        System.out.println("Tổng số trang: " + nhanVienPage.getTotalPages());
////        model.addAttribute("nhanVienPage", nhanVienPage);
////        model.addAttribute("currentPage", page);
////        model.addAttribute("totalPage", nhanVienPage.getTotalPages()); // Fixed: Use getTotalPages() instead of getTotalElements()
////        model.addAttribute("search", search);
////        model.addAttribute("chucVu", chucVuId);
////        model.addAttribute("trangThai", trangThai);
////        if (!nhanVienPage.getContent().isEmpty()) {
////            System.out.println("Nhân viên đầu tiên: " + nhanVienPage.getContent().get(0));
////        }

        PageRequest pageRequest= PageRequest.of(page,size);
        model.addAttribute("listNV",nhanVienRepo.findAll(pageRequest).getContent());

        return "admin/nhan_vien/index";


    }

    @GetMapping("/view-them")
    public String showThemNhanVienForm(Model model) {
        model.addAttribute("nhanVien", new NhanVien());
//        model.addAttribute("diaChi",new DiaChi());
        model.addAttribute("listChucVu", chucVuService.findAllChucVu());
        return "admin/nhan_vien/view-them";
    }

    //    @PostMapping("/them")
//    public String addNhanVien(
//            @ModelAttribute("nhanVien") @Valid  NhanVien nhanVien,DiaChi diaChi,
//            BindingResult bindingResult, Model model){
////    if (bindingResult.hasErrors()) {
////            return "admin/nhan_vien/view-them";
////        }
////        ChucVu defaultChucVu = chucVuService.findChucVuByViTri("Employe"); // Assuming this method exists
////         if (defaultChucVu == null) {
////            // Handle case where "Nhân viên" role doesn't exist
////            model.addAttribute("error", "Chức vụ 'Employe' không tồn tại. Vui lòng tạo chức vụ này trước.");
////            return "admin/nhan_vien/view-them";
////        }
////        nhanVien.setChucVu(defaultChucVu);
//        nhanVienServies.saveNhanVien(nhanVien,diaChi);
//        return "/admin/nhan_vien/index";
//    }
    @PostMapping("/them")
    public ResponseEntity<?>add(@RequestBody AddNhanVienRequest nhanVien){
        try {
            nhanVienService.addNhanVien(nhanVien);
            return ResponseEntity.ok().body("Thêm thanh công");
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
//    @PostMapping("/them")
//    @ResponseBody
//    public Map<String, String>addNV(@RequestBody NhanVien nhanVien,BindingResult bindingResult){
//        ChucVu defaultChucVu = chucVuService.findChucVuByViTri("Employe");
//        nhanVien.setChucVu(defaultChucVu);
//        Map<String, String> response=new HashMap<>();
//            if (nhanVien.getDiaChi() == null) {
//                nhanVien.setDiaChi(new DiaChi());
//            }
//
//            nhanVienServies.saveNhanVien(nhanVien);
//        return response;
//    }



    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<String> updateNhanVien(@RequestBody NhanVien nhanVien) {
        nhanVienService.updateNhanVien(nhanVien);
        return ResponseEntity.ok("Sửa nhân viên thành công");
    }

    //    @GetMapping("/detail/{id}")
//    public String detailNhanVienById(@PathVariable("id") Long id, Model model) {
//         model.addAttribute("nhanVien",nhanVienService.findNhanVienById(id));
//        return "/admin/nhan_vien/detail";
//    }
    @GetMapping("/detail")
    public String detail(@RequestParam Long id, Model model){
        NhanVien nhanVien=nhanVienService.findNhanVienById(id);
        model.addAttribute("nhanVien",nhanVien);

        return "admin/nhan_vien/detail";
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