package com.example.datn.service.Implements.ThuocTinhImpl;

import com.example.datn.entity.ThuocTinh.KieuQuat;
import com.example.datn.repository.ThuocTinhRepo.KieuQuatRepo;
import com.example.datn.service.ThuocTinhService.KieuQuatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KieuQuatServiceImpl implements KieuQuatService {

    private final KieuQuatRepo kieuQuatRepo;

    @Override
    public List<KieuQuat> findAllKieuQuat() {
        return kieuQuatRepo.findAll();
    }

    @Override
    public KieuQuat findById(Long id) {
        return kieuQuatRepo.findById(id).orElse(null);
    }

    @Override
    public void save(KieuQuat kieuQuat) {
        kieuQuatRepo.save(kieuQuat);
    }

    @Override
    public Page<KieuQuat> search(String query, Boolean trangThai, Pageable pageable) {
        if (trangThai == null) {
            return kieuQuatRepo.searchOnlyTen(query, pageable);
        }
        return kieuQuatRepo.search(query, trangThai, pageable);
    }

    @Override
    public ResponseEntity<?> add(String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Tên kiểu quạt không được để trống.");
        }
        Optional<KieuQuat> checkTonTai = kieuQuatRepo.findByTen(name.trim());
        if (checkTonTai.isPresent()) {
            return ResponseEntity.badRequest().body("Đã tồn tại kiểu quạt.");
        }
        KieuQuat kieuQuat = new KieuQuat();
        kieuQuat.setTen(name.trim());
        kieuQuat.setTrangThai(true);
        kieuQuatRepo.save(kieuQuat);

        return ResponseEntity.ok("Kiểu quạt thêm mới thành công.");
    }

    @Override
    public ResponseEntity<?> changeStatus(Long id) {
        KieuQuat kieuQuat = kieuQuatRepo.findById(id).orElse(null);
        if (kieuQuat == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy kiểu quạt.");
        }
        kieuQuat.setTrangThai(!kieuQuat.getTrangThai());
        kieuQuatRepo.save(kieuQuat);
        return ResponseEntity.ok("Cập nhật trạng thái thành công.");
    }
}
