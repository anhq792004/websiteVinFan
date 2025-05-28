package com.example.datn.service.Implements.NhanVienServiceImpl;

import com.example.datn.entity.NhanVien.NhanVien;
import com.example.datn.repository.NhanVienRepo;
import com.example.datn.service.NhanVienService.NhanVienService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
    @RequiredArgsConstructor
    public class NhanVienServicelmpl implements NhanVienService {
    private final NhanVienRepo nhanVienRepo;
    @Override
        public Page<NhanVien> findAllNhanVien(int page,int size,String search,Long chucVuId,Boolean trangThai){
        Pageable pageable= PageRequest.of(page,size);
        if (search!=null && !search.isEmpty()){
            if (chucVuId != null && trangThai !=null){
                return nhanVienRepo.findByTenContainingAndChucVuIdAndTrangThai(search,chucVuId,trangThai,pageable);
            } else if (chucVuId!=null) {
                return nhanVienRepo.findByTenContainingAndChucVuId(search,chucVuId,pageable);
            } else if (trangThai!=null) {
                return nhanVienRepo.findByTenContainingAndTrangThai(search,trangThai,pageable);
            }
            else {
                return nhanVienRepo.findByTenContaining(search,pageable);
            }
        }
        else {
            if (chucVuId!=null && trangThai !=null){
                return nhanVienRepo.findByChucVuIdAndTrangThai(chucVuId, trangThai,pageable);
            } else if (chucVuId!=null) {
                return nhanVienRepo.findByChucVuId(chucVuId,pageable);
            } else if (trangThai!=null) {
                return nhanVienRepo.findByTrangThai(trangThai,pageable);
            }else {
                return nhanVienRepo.findAll(pageable);
            }

        }
    }

    @Override
    public Optional<NhanVien> findNhanVienById(Long id) {
            return nhanVienRepo.findById(id);    }

    @Override
    public void saveNhanVien(NhanVien nhanVien) {
        nhanVienRepo.save(nhanVien);
    }

    @Override
    public void deleteNhanVien(Long id) {
    nhanVienRepo.deleteById(id);
    }

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
}
