package com.example.datn.service.Implements.ThuocTinhImpl;

import com.example.datn.entity.ThuocTinh.Hang;
import com.example.datn.repository.ThuocTinhRepo.HangRepo;
import com.example.datn.service.ThuocTinhService.HangService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HangServiceImpl implements HangService {

    private final HangRepo hangRepo;

    @Override
    public List<Hang> findAllHang() {
        return hangRepo.findAll();
    }

    @Override
    public Hang findById(Integer id) {
        return hangRepo.findById(id).orElse(null);
    }

    @Override
    public void save(Hang hang) {
        hangRepo.save(hang);
    }

    @Override
    public Page<Hang> search(String query, Boolean trangThai, Pageable pageable) {
        if (trangThai == null) {
            return hangRepo.searchOnlyTen(query, pageable);
        }
        return hangRepo.search(query, trangThai, pageable);
    }

    @Override
    public ResponseEntity<?> add(String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Tên hãng không được để trống.");
        }
        Optional<Hang> checkTonTai = hangRepo.findByTen(name.trim());
        if (checkTonTai.isPresent()) {
            return ResponseEntity.badRequest().body("Đã tồn tại hãng.");
        }
        Hang hang = new Hang();
        hang.setTen(name.trim());
        hang.setTrangThai(true);
        hangRepo.save(hang);

        return ResponseEntity.ok("Hãng thêm mới thành công.");
    }

    @Override
    public ResponseEntity<?> update(Integer id, String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Tên hãng không được để trống.");
        }
        Optional<Hang> checkTonTai = hangRepo.findByTen(name.trim());
        if (checkTonTai.isPresent() && !checkTonTai.get().getId().equals(id)) {
            return ResponseEntity.badRequest().body("Đã tồn tại hãng.");
        }
        Hang hang = hangRepo.findById(id).orElse(null);
        if (hang == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy hãng.");
        }
        hang.setTen(name.trim());
        hangRepo.save(hang);
        return ResponseEntity.ok("Cập nhật hãng thành công.");
    }

    @Override
    public ResponseEntity<?> changeStatus(Integer id) {
        Hang hang = hangRepo.findById(id).orElse(null);
        if (hang == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy hãng.");
        }
        hang.setTrangThai(!hang.getTrangThai());
        hangRepo.save(hang);
        return ResponseEntity.ok("Cập nhật trạng thái thành công.");
    }
}