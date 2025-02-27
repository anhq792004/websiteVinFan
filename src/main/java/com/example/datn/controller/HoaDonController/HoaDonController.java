package com.example.datn.controller.HoaDonController;

import com.example.datn.entity.HoaDon.HoaDon;
import com.example.datn.entity.SanPham.SanPham;
import com.example.datn.service.HoaDonService.HoaDonSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
    ){
        Page<HoaDon> listHoaDon = hoaDonSerivce.findAllHoaDonAndSortDay(page, size);
        model.addAttribute("list",listHoaDon);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",listHoaDon.getTotalPages());
        return "admin/hoa_don/index";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam Long id, Model model){
        return"admin/hoa_don/detail";
    }
}
