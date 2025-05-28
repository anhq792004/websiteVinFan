package com.example.datn.controller.ChucVuController;

import com.example.datn.entity.ChucVu;
import com.example.datn.repository.ChucVuRepo;
import com.example.datn.service.ChucVuService.ChucVuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/chuc-vu")
@RequiredArgsConstructor
public class ChucVuController {
    private final ChucVuRepo chucVuRepo;
    private final ChucVuService chucVuService;
    @ModelAttribute("listChucVu")
    public List<ChucVu> listChuVu(){
        return chucVuRepo.findAll();
    }

}
