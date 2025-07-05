package com.example.datn.service.Implements.NhanVienServiceImpl;

import com.example.datn.dto.request.AddNhanVienRequest;
import com.example.datn.entity.ChucVu;
import com.example.datn.entity.DiaChi;
import com.example.datn.entity.KhachHang;
import com.example.datn.entity.NhanVien.NhanVien;
import com.example.datn.entity.TaiKhoan;
import com.example.datn.repository.ChucVuRepo;
import com.example.datn.repository.DiaChiRepo;
import com.example.datn.repository.NhanVienRepo;
import com.example.datn.repository.TaiKhoanRepo;
import com.example.datn.service.NhanVienService.NhanVienService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NhanVienServicelmpl implements NhanVienService {
    private final NhanVienRepo nhanVienRepo;
    private final ChucVuRepo chucVuRepo;
    private final TaiKhoanRepo taiKhoanRepo;
    private final DiaChiRepo diaChiRepo;

    @Override
    public Page<NhanVien> findAll(String keyword, Boolean trangThai, Pageable pageable) {
        if (trangThai == null) {
            return nhanVienRepo.searchNhanVienKhongCoTrangThai(keyword, pageable);
        }
        return nhanVienRepo.searchNhanVien(keyword, trangThai, pageable);
    }

    @Override
    public NhanVien findNhanVienById(Long id) {
        return nhanVienRepo.findById(id).orElse(null);    }


    @Override
    public void updateNhanVien(NhanVien nhanVien) {
        nhanVienRepo.save(nhanVien);
    }

    @Override
    public boolean thayDoiTrangThaiNhanVien(Long id) {
        Optional<NhanVien> nhanVienOptional=nhanVienRepo.findById(id);
        if (nhanVienOptional.isPresent()){
            NhanVien nhanVien= nhanVienOptional.get();
            nhanVien.setTrangThai(nhanVien.getTrangThai()==Boolean.FALSE?Boolean.TRUE:Boolean.FALSE);
            nhanVienRepo.save(nhanVien);
            return true;
        }
        return false;
    }

    @Override
    public void addNhanVien(AddNhanVienRequest nhanVien) {
        TaiKhoan taiKhoan= new TaiKhoan();
        ChucVu chucVu= chucVuRepo.findByViTri("Employe").orElseGet(() -> {
            ChucVu newChucVu= new ChucVu();
            newChucVu.setViTri("Employe");
            return chucVuRepo.save(newChucVu);
        });
        taiKhoan.setChucVu(chucVu);
        taiKhoan.setEmail(nhanVien.getEmail());
        taiKhoan.setNgayTao(new Date());
        taiKhoanRepo.save(taiKhoan);

        DiaChi diaChi= new DiaChi();
        diaChi.setHuyen(nhanVien.getQuanHuyen());
        diaChi.setTinh(nhanVien.getTinhThanhPho());
        diaChi.setXa(nhanVien.getXaPhuong());
        diaChi.setSoNhaNgoDuong(nhanVien.getSoNhaNgoDuong());
        diaChiRepo.save(diaChi);

        NhanVien newNhanVien = new NhanVien();
        newNhanVien.setMa(generateCode());
        newNhanVien.setTaiKhoan(taiKhoan);
        newNhanVien.setChucVu(chucVu);
        newNhanVien.setCanCuocCongDan(nhanVien.getCanCuocCongDan());
        newNhanVien.setTen(nhanVien.getTen());
        newNhanVien.setGioiTinh(nhanVien.getGioiTinh());
        newNhanVien.setSoDienThoai(nhanVien.getSoDienThoai());
        newNhanVien.setNgaySinh(nhanVien.getNgaySinh());
        newNhanVien.setTrangThai(true);
        newNhanVien.setDiaChi(diaChi);
        nhanVienRepo.save(newNhanVien);

    }
    @Override
    public String generateCode() {
        Long count = nhanVienRepo.count(); // Số lượng hóa đơn trong DB
        // Tạo mã hóa đơn với tiền tố "HD" và số thứ tự
        return String.format("NV%03d", count + 1); // VD: HD001, HD002
    }

    @Override
    public ResponseEntity<?> changeStatus(Long id) {
        NhanVien nhanVien = nhanVienRepo.findById(id).orElse(null);
        if (nhanVien == null) {
            return ResponseEntity.badRequest().body("Không tìm nhân viên.");
        }
        nhanVien.setTrangThai(!nhanVien.getTrangThai());
        nhanVienRepo.save(nhanVien);
        return ResponseEntity.ok("Cập nhật trạng thái thành công.");
    }
}
