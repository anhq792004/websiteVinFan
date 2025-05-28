package com.example.datn.controller;


import com.example.datn.entity.DiaChi;
import com.example.datn.entity.KhachHang;
import com.example.datn.repository.DiaChiRepo;
import com.example.datn.service.KhachHangService.KhachHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/khach-hang")
public class KhachHangController {
    private final KhachHangService khachHangService;
    private final DiaChiRepo diaChiRepo;
    @ModelAttribute("listDiaChi")
    List<DiaChi> getListDiaChi(){ return  diaChiRepo.findAll();}
    @GetMapping("/index")
    public String getAllKhachHang(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(name = "trangThai", required = false) Boolean trangThai,
            Model model)
    {
        Page<KhachHang> khachHangPage=khachHangService.findAllKhachHang(page, size, search, trangThai);
        System.out.println("Số lượng khách hàng: " + khachHangPage.getTotalElements());
        System.out.println("Tổng số trang: " + khachHangPage.getTotalPages());
        model.addAttribute("khachHangPage", khachHangPage);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPage",khachHangPage.getTotalPages());
        model.addAttribute("search",search);
        model.addAttribute("trangThai",trangThai);
        if (!khachHangPage.getContent().isEmpty()){
            System.out.println("Khách hàng đầu tiên: "+khachHangPage.getContent().get(0));
        }
        return "admin/khach_hang/index";
    }
    @GetMapping("/view-them")
    public String showThemKhachHangForm(Model model){
        model.addAttribute("khachHang",new KhachHang());
        return "admin/khach_hang/view-them";
    }
    @PostMapping("/them")
    public String addKhachHang(@ModelAttribute("khachHang")KhachHang khachHang,Model model){
        khachHangService.saveKhachHang(khachHang);
        return "redirect:/admin/khach_hang/index";
    }
    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<String> updateKhachHang(@RequestBody KhachHang khachHang){
        khachHangService.saveKhachHang(khachHang);
        return ResponseEntity.ok("Sửa khách hàng");
    }
    @GetMapping("/detail/{id}")
    public String detailKhachHangByID(@PathVariable("id") Long id, Model model) {
        model.addAttribute("khachHang",khachHangService.findKhachHangById(id));
        return "/admin/khach_hang/detail";
    }
}
