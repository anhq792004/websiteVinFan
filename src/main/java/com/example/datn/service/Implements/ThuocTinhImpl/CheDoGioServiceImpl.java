//package com.example.datn.service.Implements.ThuocTinhImpl;
//
//import com.example.datn.entity.ThuocTinh.CheDoGio;
//import com.example.datn.repository.ThuocTinhRepo.CheDoGioRepo;
//import com.example.datn.service.ThuocTinhService.CheDoGioService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class CheDoGioServiceImpl implements CheDoGioService {
//
//    private final CheDoGioRepo cheDoGioRepo;
//
//    @Override
//    public List<CheDoGio> findAllCheDoGio() {
//        return cheDoGioRepo.findAll();
//    }
//
//    @Override
//    public CheDoGio findById(Integer id) {
//        return cheDoGioRepo.findById(id).orElse(null);
//    }
//
//    @Override
//    public void save(CheDoGio cheDoGio) {
//        cheDoGioRepo.save(cheDoGio);
//    }
//
//    @Override
//    public Page<CheDoGio> search(String query, Boolean trangThai, Pageable pageable) {
//        if (trangThai == null) {
//            return cheDoGioRepo.searchOnlyTen(query, pageable);
//        }
//        return cheDoGioRepo.search(query, trangThai, pageable);
//    }
//
//    @Override
//    public ResponseEntity<?> add(String name) {
//        if (name == null || name.trim().isEmpty()) {
//            return ResponseEntity.badRequest().body("Tên chế độ gió không được để trống.");
//        }
//        Optional<CheDoGio> checkTonTai = cheDoGioRepo.findByTen(name.trim());
//        if (checkTonTai.isPresent()) {
//            return ResponseEntity.badRequest().body("Đã tồn tại chế độ gió.");
//        }
//        CheDoGio cheDoGio = new CheDoGio();
//        cheDoGio.setTen(name.trim());
//        cheDoGio.setTrangThai(true);
//        cheDoGioRepo.save(cheDoGio);
//
//        return ResponseEntity.ok("Chế độ gió thêm mới thành công.");
//    }
//
//    @Override
//    public ResponseEntity<?> update(Integer id, String name) {
//        if (name == null || name.trim().isEmpty()) {
//            return ResponseEntity.badRequest().body("Tên chế độ gió không được để trống.");
//        }
//        Optional<CheDoGio> checkTonTai = cheDoGioRepo.findByTen(name.trim());
//        if (checkTonTai.isPresent() && !checkTonTai.get().getId().equals(id)) {
//            return ResponseEntity.badRequest().body("Đã tồn tại chế độ gió.");
//        }
//        CheDoGio cheDoGio = cheDoGioRepo.findById(id).orElse(null);
//        if (cheDoGio == null) {
//            return ResponseEntity.badRequest().body("Không tìm thấy chế độ gió.");
//        }
//        cheDoGio.setTen(name.trim());
//        cheDoGioRepo.save(cheDoGio);
//        return ResponseEntity.ok("Cập nhật chế độ gió thành công.");
//    }
//
//    @Override
//    public ResponseEntity<?> changeStatus(Integer id) {
//        CheDoGio cheDoGio = cheDoGioRepo.findById(id).orElse(null);
//        if (cheDoGio == null) {
//            return ResponseEntity.badRequest().body("Không tìm thấy chế độ gió.");
//        }
//        cheDoGio.setTrangThai(!cheDoGio.getTrangThai());
//        cheDoGioRepo.save(cheDoGio);
//        return ResponseEntity.ok("Cập nhật trạng thái thành công.");
//    }
//}