//package com.example.datn.service.Implements.ThuocTinhImpl;
//
//import com.example.datn.entity.ThuocTinh.DuongKinhCanh;
//import com.example.datn.repository.ThuocTinhRepo.DuongKinhCanhRepo;
//import com.example.datn.service.ThuocTinhService.DuongKinhCanhService;
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
//
//public class DuongKinhCanhServiceImpl implements DuongKinhCanhService {
//
//    private final DuongKinhCanhRepo duongKinhCanhRepo;
//
//    @Override
//    public List<DuongKinhCanh> findAllDuongKinhCanh() {
//        return duongKinhCanhRepo.findAll();
//    }
//
//    @Override
//    public DuongKinhCanh findById(Integer id) {
//        return duongKinhCanhRepo.findById(id).orElse(null);
//    }
//
//    @Override
//    public void save(DuongKinhCanh duongKinhCanh) {
//        duongKinhCanhRepo.save(duongKinhCanh);
//    }
//
//    @Override
//    public Page<DuongKinhCanh> search(String query, Boolean trangThai, Pageable pageable) {
//        if (trangThai == null) {
//            return duongKinhCanhRepo.searchOnlyTen(query, pageable);
//        }
//        return duongKinhCanhRepo.search(query, trangThai, pageable);
//    }
//
//    @Override
//    public ResponseEntity<?> add(String name) {
//        if (name == null || name.trim().isEmpty()) {
//            return ResponseEntity.badRequest().body("Tên đường kính cánh không được để trống.");
//        }
//        Optional<DuongKinhCanh> checkTonTai = duongKinhCanhRepo.findByTen(name.trim());
//        if (checkTonTai.isPresent()) {
//            return ResponseEntity.badRequest().body("Đã tồn tại đường kính cánh.");
//        }
//        DuongKinhCanh duongKinhCanh = new DuongKinhCanh();
//        duongKinhCanh.setTen(name.trim());
//        duongKinhCanh.setTrangThai(true);
//        duongKinhCanhRepo.save(duongKinhCanh);
//
//        return ResponseEntity.ok("Đường kính cánh thêm mới thành công.");
//    }
//
//    @Override
//    public ResponseEntity<?> update(Integer id, String name) {
//        if (name == null || name.trim().isEmpty()) {
//            return ResponseEntity.badRequest().body("Tên đường kính cánh không được để trống.");
//        }
//        Optional<DuongKinhCanh> checkTonTai = duongKinhCanhRepo.findByTen(name.trim());
//        if (checkTonTai.isPresent() && !checkTonTai.get().getId().equals(id)) {
//            return ResponseEntity.badRequest().body("Đã tồn tại đường kính cánh.");
//        }
//        DuongKinhCanh duongKinhCanh = duongKinhCanhRepo.findById(id).orElse(null);
//        if (duongKinhCanh == null) {
//            return ResponseEntity.badRequest().body("Không tìm thấy đường kính cánh.");
//        }
//        duongKinhCanh.setTen(name.trim());
//        duongKinhCanhRepo.save(duongKinhCanh);
//        return ResponseEntity.ok("Cập nhật đường kính cánh thành công.");
//    }
//
//    @Override
//    public ResponseEntity<?> changeStatus(Integer id) {
//        DuongKinhCanh duongKinhCanh = duongKinhCanhRepo.findById(id).orElse(null);
//        if (duongKinhCanh == null) {
//            return ResponseEntity.badRequest().body("Không tìm thấy đường kính cánh.");
//        }
//        duongKinhCanh.setTrangThai(!duongKinhCanh.getTrangThai());
//        duongKinhCanhRepo.save(duongKinhCanh);
//        return ResponseEntity.ok("Cập nhật trạng thái thành công.");
//    }
//}
