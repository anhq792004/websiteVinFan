package com.example.datn.service.NhanVienService;

import com.example.datn.dto.request.AddNhanVienRequest;
import com.example.datn.entity.NhanVien.NhanVien;
import com.example.datn.repository.NhanVienRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
public interface NhanVienService {

    //    Page<NhanVien> findAllNhanVien(int page,int size,String search,Long chucVuId, Boolean trangThai);
    Page<NhanVien> findAllNhanVien(String keyword, Boolean trang_thai, Pageable pageable);
    NhanVien findNhanVienById(Long id);

    void deleteNhanVien(Long id);
    void updateNhanVien(NhanVien nhanVien);
    boolean thayDoiTrangThaiNhanVien(Long id);

    void addNhanVien(AddNhanVienRequest nhanVien);

    String generateCode();
}
