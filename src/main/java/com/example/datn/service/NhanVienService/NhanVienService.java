package com.example.datn.service.NhanVienService;

import com.example.datn.entity.NhanVien.NhanVien;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface NhanVienService {
    Page<NhanVien> findAllNhanVien(int page,int size,String search,Long chucVuId, Boolean trangThai);
    Optional<NhanVien> findNhanVienById(Long id);
    void saveNhanVien(NhanVien nhanVien);
    void deleteNhanVien(Long id);
    void updateNhanVien(NhanVien nhanVien);
    boolean thayDoiTrangThaiNhanVien(Long id);
}
