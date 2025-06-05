package com.example.datn.service.Implements.KhachHangServerceImpl;

import com.example.datn.dto.request.AddKhachHangRequest;
import com.example.datn.entity.DiaChi;
import com.example.datn.entity.KhachHang;
import com.example.datn.repository.DiaChiRepo;
import com.example.datn.repository.KhachHangRepo.KhachHangRepo;
import com.example.datn.service.KhachHangService.KhachHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class KhachHangServiceImpl implements KhachHangService {

    private final KhachHangRepo khachHangRepo;

    private final DiaChiRepo diaChiRepo;


    @Override
    public Page<KhachHang> findAll(String keyword, Boolean trang_thai, Pageable pageable) {
        if (trang_thai == null) {
            return khachHangRepo.searchKhachHangKhongCoTrangThai(keyword, pageable);
        }
        return khachHangRepo.searchKhachHang(keyword, trang_thai, pageable);
    }

    @Override
    public KhachHang addKH(AddKhachHangRequest request) {
        KhachHang khachHang = new KhachHang();
        khachHang.setMa(generateCode());
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

    @Override
    public String generateCode() {
        Long count = khachHangRepo.count(); // Số lượng hóa đơn trong DB
        // Tạo mã hóa đơn với tiền tố "HD" và số thứ tự
        return String.format("KH%03d", count + 1); // VD: HD001, HD002
    }

}
