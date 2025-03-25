package com.example.datn.service.Implements.ThuocTinhImpl;

import com.example.datn.entity.ThuocTinh.NutBam;
import com.example.datn.repository.ThuocTinhRepo.NutBamRepo;
import com.example.datn.service.ThuocTinhService.NutBamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NutBamServiceImpl implements NutBamService {

    private final NutBamRepo nutBamRepo;

    @Override
    public List<NutBam> findAllNutBam() {
        return nutBamRepo.findAll();
    }

    @Override
    public NutBam findById(Integer id) {
        return nutBamRepo.findById(id).orElse(null);
    }

    @Override
    public void save(NutBam nutBam) {
        nutBamRepo.save(nutBam);
    }

    @Override
    public Page<NutBam> search(String query, Boolean trangThai, Pageable pageable) {
        if (trangThai == null) {
            return nutBamRepo.searchOnlyTen(query, pageable);
        }
        return nutBamRepo.search(query, trangThai, pageable);
    }

    @Override
    public ResponseEntity<?> add(String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Tên nút bấm không được để trống.");
        }
        Optional<NutBam> checkTonTai = nutBamRepo.findByTen(name.trim());
        if (checkTonTai.isPresent()) {
            return ResponseEntity.badRequest().body("Đã tồn tại nút bấm.");
        }
        NutBam nutBam = new NutBam();
        nutBam.setTen(name.trim());
        nutBam.setTrangThai(true);
        nutBamRepo.save(nutBam);

        return ResponseEntity.ok("Nút bấm thêm mới thành công.");
    }

    @Override
    public ResponseEntity<?> update(Integer id, String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Tên nút bấm không được để trống.");
        }
        Optional<NutBam> checkTonTai = nutBamRepo.findByTen(name.trim());
        if (checkTonTai.isPresent() && !checkTonTai.get().getId().equals(id)) {
            return ResponseEntity.badRequest().body("Đã tồn tại nút bấm.");
        }
        NutBam nutBam = nutBamRepo.findById(id).orElse(null);
        if (nutBam == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy nút bấm.");
        }
        nutBam.setTen(name.trim());
        nutBamRepo.save(nutBam);
        return ResponseEntity.ok("Cập nhật nút bấm thành công.");
    }

    @Override
    public ResponseEntity<?> changeStatus(Integer id) {
        NutBam nutBam = nutBamRepo.findById(id).orElse(null);
        if (nutBam == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy nút bấm.");
        }
        nutBam.setTrangThai(!nutBam.getTrangThai());
        nutBamRepo.save(nutBam);
        return ResponseEntity.ok("Cập nhật trạng thái thành công.");
    }
}