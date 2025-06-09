package com.example.datn.service.Implements;

import com.example.datn.entity.DiaChi;
import com.example.datn.repository.DiaChiRepo;
import com.example.datn.service.DiaChiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaChiServiceImpl implements DiaChiService {

    private final DiaChiRepo diaChiRepo;

    @Override
    public List<DiaChi> getDiaChiByIdKhachHang(Long idKH) {
        return diaChiRepo.findByKhachHang_Id(idKH);
    }
}
