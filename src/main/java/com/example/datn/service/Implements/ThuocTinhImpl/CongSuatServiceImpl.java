package com.example.datn.service.Implements.ThuocTinhImpl;

import com.example.datn.entity.ThuocTinh.CongSuat;
import com.example.datn.repository.ThuocTinhRepo.CongSuatRepo;
import com.example.datn.service.ThuocTinhService.CongSuatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CongSuatServiceImpl implements CongSuatService {

    private final CongSuatRepo congSuatRepo;

    @Override
    public List<CongSuat> findAllCongSuat() {
        return congSuatRepo.findAll();
    }

    @Override
    public CongSuat findById(Integer id) {
        return congSuatRepo.findById(id).orElse(null);
    }

    @Override
    public void save(CongSuat congSuat) {
        congSuatRepo.save(congSuat);
    }

    @Override
    public Page<CongSuat> search(String query, Boolean trangThai, Pageable pageable) {
        if (trangThai == null) {
            return congSuatRepo.searchOnlyTen(query, pageable);
        }
        return congSuatRepo.search(query, trangThai, pageable);
    }

    @Override
    public ResponseEntity<?> add(String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Tên công suất không được để trống.");
        }
        Optional<CongSuat> checkTonTai = congSuatRepo.findByTen(name.trim());
        if (checkTonTai.isPresent()) {
            return ResponseEntity.badRequest().body("Đã tồn tại công suất.");
        }
        CongSuat congSuat = new CongSuat();
        congSuat.setTen(name.trim());
        congSuat.setTrangThai(true);
        congSuatRepo.save(congSuat);

        return ResponseEntity.ok("Công suất thêm mới thành công.");
    }

    @Override
    public ResponseEntity<?> update(Integer id, String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Tên công suất không được để trống.");
        }
        Optional<CongSuat> checkTonTai = congSuatRepo.findByTen(name.trim());
        if (checkTonTai.isPresent() && !checkTonTai.get().getId().equals(id)) {
            return ResponseEntity.badRequest().body("Đã tồn tại công suất.");
        }
        CongSuat congSuat = congSuatRepo.findById(id).orElse(null);
        if (congSuat == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy công suất.");
        }
        congSuat.setTen(name.trim());
        congSuatRepo.save(congSuat);
        return ResponseEntity.ok("Cập nhật công suất thành công.");
    }

    @Override
    public ResponseEntity<?> changeStatus(Integer id) {
        CongSuat congSuat = congSuatRepo.findById(id).orElse(null);
        if (congSuat == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy công suất.");
        }
        congSuat.setTrangThai(!congSuat.getTrangThai());
        congSuatRepo.save(congSuat);
        return ResponseEntity.ok("Cập nhật trạng thái thành công.");
    }
}