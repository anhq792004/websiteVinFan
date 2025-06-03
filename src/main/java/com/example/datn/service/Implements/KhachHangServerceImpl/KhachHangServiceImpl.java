package com.example.datn.service.Implements.KhachHangServerceImpl;

import com.example.datn.dto.request.AddKhachHangRequest;
import com.example.datn.entity.DiaChi;
import com.example.datn.entity.KhachHang;
import com.example.datn.repository.DiaChiRepo;
import com.example.datn.repository.KhachHangRepo.KhachHangRepo;
import com.example.datn.service.KhachHangService.KhachHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KhachHangServiceImpl implements KhachHangService {

    private final KhachHangRepo khachHangRepo;
    private final DiaChiRepo diaChiRepo;

    @Override
    public Page<KhachHang> findAllKhachHang(int page, int size, String search, Boolean trangThai) {
        Pageable pageable = PageRequest.of(page, size);
        // Logic tìm kiếm, ví dụ:
        if (search != null && !search.isEmpty()) {
            return khachHangRepo.findByTenContainingIgnoreCaseAndTrangThai(search, trangThai, pageable);
        }
        return khachHangRepo.findByTrangThai(trangThai, pageable);
    }

    @Override
    public Optional<KhachHang> findKhachHangById(Long id) {
        return khachHangRepo.findById(id);
    }

    @Override
    public void saveKhachHang(KhachHang khachHang) {
        khachHangRepo.save(khachHang);
    }

    @Override
    public KhachHang addKH(AddKhachHangRequest request) {
        KhachHang khachHang = new KhachHang();
        khachHang.setTen(request.getTen());
        khachHang.setGioiTinh(request.getGioiTinh());
        khachHang.setNgayTao(new Date());
        khachHang.setSoDienThoai(request.getSoDienThoai());
        khachHang.setNgaySinh(request.getNgaySinh());
        khachHang.setTrangThai(true);
        khachHangRepo.save(khachHang);

        DiaChi diaChi = new DiaChi();
        diaChi.setKhachHang(khachHang);
        diaChi.setHuyen(request.getQuanHuyen());
        diaChi.setTinh(request.getTinhThanhPho());
        diaChi.setXa(request.getXaPhuong());
        diaChi.setSoNhaNgoDuong(request.getSoNhaNgoDuong());
        diaChiRepo.save(diaChi);
        return null;
    }

}
