package com.example.datn.service.Implements.ChucVuServicempl;

import com.example.datn.entity.ChucVu;
import com.example.datn.repository.ChucVuRepo;
import com.example.datn.service.ChucVuService.ChucVuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChucVuservicelmpl implements ChucVuService {
    @Autowired
    private final ChucVuRepo chucVuRepo;
    @Override
    public List<ChucVu> findAllChucVu(){return chucVuRepo.findAll();}

    @Override
    public ChucVu findChucVuByViTri(String viTri){
    return chucVuRepo.findByViTri(viTri).orElse(null);
    }
    @Override
    public ChucVu findById(Integer id) {
        return null;
    }

    @Override
    public void save(ChucVu chucVu) {

    }

    @Override
    public Page<ChucVu> search(String query, Pageable pageable) {
        return null;
    }

    @Override
    public ResponseEntity<?> add(String viTri) {
        return null;
    }

    @Override
    public ResponseEntity<?> changeStatus(Integer id) {
        return null;
    }
}
