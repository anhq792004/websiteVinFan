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
    public List<NutBam> findAll() {
        return nutBamRepo.findAll();
    }

    @Override
    public NutBam findById(Long id) {
        return nutBamRepo.findById(id).orElse(null);
    }

    @Override
    public void save(NutBam nutBam) {
        nutBamRepo.save(nutBam);
    }

    @Override
    public void delete(Long id) {
        nutBamRepo.deleteById(id);
    }

    @Override
    public void thayDoiTrangThai(Long id) {
        Optional<NutBam> nutBamOpt = nutBamRepo.findById(id);
        if (nutBamOpt.isPresent()) {
            NutBam nutBam = nutBamOpt.get();
            nutBam.setTrangThai(!nutBam.getTrangThai());
            nutBamRepo.save(nutBam);
        }
    }

    @Override
    public Page<NutBam> search(String query, Boolean trangThai, Pageable pageable) {
        if (query != null && !query.isEmpty()) {
            if (trangThai != null) {
                return nutBamRepo.findByTenContainingAndTrangThai(query, trangThai, pageable);
            }
            return nutBamRepo.findByTenContaining(query, pageable);
        } else {
            if (trangThai != null) {
                return nutBamRepo.findByTrangThai(trangThai, pageable);
            }
            return nutBamRepo.findAll(pageable);
        }
    }

    @Override
    public ResponseEntity<?> add(String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Tên không được để trống");
        }
        NutBam nutBam = new NutBam();
        nutBam.setTen(name.trim());
        nutBam.setTrangThai(true);
        nutBamRepo.save(nutBam);
        return ResponseEntity.ok("Thêm thành công");
    }

    @Override
    public ResponseEntity<?> update(Integer id, String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Tên không được để trống");
        }
        Optional<NutBam> nutBamOpt = nutBamRepo.findById(Long.valueOf(id));
        if (nutBamOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Không tìm thấy nút bấm");
        }
        NutBam nutBam = nutBamOpt.get();
        nutBam.setTen(name.trim());
        nutBamRepo.save(nutBam);
        return ResponseEntity.ok("Cập nhật thành công");
    }

    @Override
    public ResponseEntity<?> changeStatus(Integer id) {
        Optional<NutBam> nutBamOpt = nutBamRepo.findById(Long.valueOf(id));
        if (nutBamOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Không tìm thấy nút bấm");
        }
        NutBam nutBam = nutBamOpt.get();
        nutBam.setTrangThai(!nutBam.getTrangThai());
        nutBamRepo.save(nutBam);
        return ResponseEntity.ok("Thay đổi trạng thái thành công");
    }
}