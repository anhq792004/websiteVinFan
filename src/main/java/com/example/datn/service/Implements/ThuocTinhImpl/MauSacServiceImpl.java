package com.example.datn.service.Implements.ThuocTinhImpl;

import com.example.datn.entity.ThuocTinh.MauSac;
import com.example.datn.repository.ThuocTinhRepo.MauSacRepo;
import com.example.datn.service.ThuocTinhService.MauSacService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MauSacServiceImpl implements MauSacService {

    private final MauSacRepo mauSacRepo;

    @Override
    public List<MauSac> findAllMauSac() {
        return mauSacRepo.findAll();
    }

    @Override
    public MauSac findById(Integer id) {
        return mauSacRepo.findById(id).orElse(null);
    }

    @Override
    public void save(MauSac mauSac) {
        mauSacRepo.save(mauSac);
    }

    @Override
    public Page<MauSac> search(String query, Boolean trangThai, Pageable pageable) {
        if (trangThai == null) {
            return mauSacRepo.searchOnlyTen(query, pageable);
        }
        return mauSacRepo.search(query, trangThai, pageable);
    }

    @Override
    public ResponseEntity<?> add(String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Tên màu sắc không được để trống.");
        }
        Optional<MauSac> checkTonTai = mauSacRepo.findByTen(name.trim());
        if (checkTonTai.isPresent()) {
            return ResponseEntity.badRequest().body("Đã tồn tại màu sắc.");
        }
        MauSac mauSac = new MauSac();
        mauSac.setTen(name.trim());
        mauSac.setTrangThai(true);
        mauSacRepo.save(mauSac);

        return ResponseEntity.ok("Màu sắc thêm mới thành công.");
    }

    @Override
    public ResponseEntity<?> update(Integer id, String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Tên màu sắc không được để trống.");
        }
        Optional<MauSac> checkTonTai = mauSacRepo.findByTen(name.trim());
        if (checkTonTai.isPresent() && !checkTonTai.get().getId().equals(id)) {
            return ResponseEntity.badRequest().body("Đã tồn tại màu sắc.");
        }
        MauSac mauSac = mauSacRepo.findById(id).orElse(null);
        if (mauSac == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy màu sắc.");
        }
        mauSac.setTen(name.trim());
        mauSacRepo.save(mauSac);
        return ResponseEntity.ok("Cập nhật màu sắc thành công.");
    }

    @Override
    public ResponseEntity<?> changeStatus(Integer id) {
        MauSac mauSac = mauSacRepo.findById(id).orElse(null);
        if (mauSac == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy màu sắc.");
        }
        mauSac.setTrangThai(!mauSac.getTrangThai());
        mauSacRepo.save(mauSac);
        return ResponseEntity.ok("Cập nhật trạng thái thành công.");
    }
}