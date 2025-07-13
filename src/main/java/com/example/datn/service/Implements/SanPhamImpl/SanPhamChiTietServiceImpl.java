package com.example.datn.service.Implements.SanPhamImpl;

import com.example.datn.entity.HinhAnh;
import com.example.datn.entity.SanPham.SanPhamChiTiet;
import com.example.datn.entity.ThuocTinh.CongSuat;
import com.example.datn.entity.ThuocTinh.Hang;
import com.example.datn.entity.ThuocTinh.MauSac;
import com.example.datn.entity.ThuocTinh.NutBam;
import com.example.datn.repository.HinhAnhRepo;
import com.example.datn.repository.SanPhamRepo.SanPhamChiTietRepository;
import com.example.datn.repository.ThuocTinhRepo.CongSuatRepository;
import com.example.datn.repository.ThuocTinhRepo.HangRepository;
import com.example.datn.repository.ThuocTinhRepo.MauSacRepository;
import com.example.datn.repository.ThuocTinhRepo.NutBamRepository;
import com.example.datn.service.SanPhamSerivce.SanPhamChiTietService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Service
@RequiredArgsConstructor
public class SanPhamChiTietServiceImpl implements SanPhamChiTietService {

    private final SanPhamChiTietRepository sanPhamChiTietRepository;
    private final MauSacRepository mauSacRepository;
    private final CongSuatRepository congSuatRepository;
    private final HangRepository hangRepository;
    private final NutBamRepository nutBamRepository;
    private final HinhAnhRepo hinhAnhRepo;

    @Override
    public SanPhamChiTiet findById(Long id) {
        return sanPhamChiTietRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void update(Long id, Long mauSacId, Long congSuatId, Long hangId, Long nutBamId,
                      Integer soLuong, Double gia, Double canNang, Boolean trangThai,
                      String moTa, MultipartFile hinhAnh) {
        log.info("=== BẮT ĐẦU CẬP NHẬT SPCT ===");
        log.info("ID: {}", id);
        log.info("MauSacId: {}", mauSacId);
        log.info("CongSuatId: {}", congSuatId);
        log.info("HangId: {}", hangId);
        log.info("NutBamId: {}", nutBamId);
        log.info("SoLuong: {}", soLuong);
        log.info("Gia: {}", gia);
        log.info("TrangThai: {}", trangThai);
        log.info("HinhAnh file: {}", hinhAnh != null ? hinhAnh.getOriginalFilename() : "null");
        
        SanPhamChiTiet spct = sanPhamChiTietRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm chi tiết"));
        log.info("Tìm thấy SPCT: {}", spct.getId());

        if (mauSacId != null) {
            MauSac mauSac = mauSacRepository.findById(mauSacId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy màu sắc"));
            spct.setMauSac(mauSac);
            log.info("Set màu sắc: {}", mauSac.getTen());
        }

        if (congSuatId != null) {
            CongSuat congSuat = congSuatRepository.findById(congSuatId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy công suất"));
            spct.setCongSuat(congSuat);
            log.info("Set công suất: {}", congSuat.getTen());
        }

        if (hangId != null) {
            Hang hang = hangRepository.findById(hangId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy hãng"));
            spct.setHang(hang);
            log.info("Set hãng: {}", hang.getTen());
        }

        if (nutBamId != null) {
            NutBam nutBam = nutBamRepository.findById(nutBamId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy nút bấm"));
            spct.setNutBam(nutBam);
            log.info("Set nút bấm: {}", nutBam.getTen());
        }

        if (soLuong != null) {
            spct.setSoLuong(soLuong);
            log.info("Set số lượng: {}", soLuong);
        }
        if (gia != null) {
            spct.setGia(BigDecimal.valueOf(gia));
            log.info("Set giá: {}", gia);
        }
        if (canNang != null) {
            spct.setCanNang(canNang.floatValue());
            log.info("Set cân nặng: {}", canNang);
        }
        if (trangThai != null) {
            spct.setTrangThai(trangThai);
            log.info("Set trạng thái: {}", trangThai);
        }
        if (moTa != null) {
            spct.setMoTa(moTa);
            log.info("Set mô tả: {}", moTa.length() > 50 ? moTa.substring(0, 50) + "..." : moTa);
        }

        // Xử lý hình ảnh
        if (hinhAnh != null && !hinhAnh.isEmpty()) {
            log.info("=== XỬ LÝ HÌNH ẢNH ===");
            String fileName = StringUtils.cleanPath(hinhAnh.getOriginalFilename());
            String uploadDir = "src/main/resources/static/admin/assets/images/products";
            String filePath = uploadDir + "/" + fileName;
            log.info("File name: {}", fileName);
            log.info("Upload dir: {}", uploadDir);

            try {
                // Tạo thư mục nếu chưa tồn tại
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                    log.info("Đã tạo thư mục: {}", uploadPath);
                }

                // Lưu file
                try (InputStream inputStream = hinhAnh.getInputStream()) {
                    Path path = Paths.get(filePath);
                    Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
                    log.info("Đã lưu file: {}", filePath);
                }

                // Cập nhật hoặc tạo mới HinhAnh
                HinhAnh hinhAnhEntity = spct.getHinhAnh();
                if (hinhAnhEntity == null) {
                    hinhAnhEntity = new HinhAnh();
                    log.info("Tạo mới HinhAnh entity");
                } else {
                    log.info("Cập nhật HinhAnh entity có ID: {}", hinhAnhEntity.getId());
                }
                hinhAnhEntity.setHinhAnh("/admin/assets/images/products/" + fileName);
                // Lưu HinhAnh trước
                hinhAnhEntity = hinhAnhRepo.save(hinhAnhEntity);
                log.info("Đã lưu HinhAnh với ID: {}", hinhAnhEntity.getId());
                spct.setHinhAnh(hinhAnhEntity);
            } catch (IOException e) {
                log.error("Lỗi khi lưu file: {}", fileName, e);
                throw new RuntimeException("Không thể lưu file: " + fileName, e);
            }
        } else {
            log.info("Không có hình ảnh mới để cập nhật");
        }

        try {
            SanPhamChiTiet saved = sanPhamChiTietRepository.save(spct);
            log.info("=== CẬP NHẬT THÀNH CÔNG ===");
            log.info("SPCT đã lưu với ID: {}", saved.getId());
        } catch (Exception e) {
            log.error("=== LỖI KHI LƯU SPCT ===", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public SanPhamChiTiet add(Long sanPhamId, Long mauSacId, Long congSuatId, Long hangId, Long nutBamId,
                             Integer soLuong, Double gia, Double canNang, Boolean trangThai,
                             String moTa, MultipartFile hinhAnh) {
        log.info("Bắt đầu thêm sản phẩm chi tiết mới cho sản phẩm ID: {}", sanPhamId);
        
        SanPhamChiTiet spct = new SanPhamChiTiet();
        
        // Set sản phẩm
        com.example.datn.entity.SanPham.SanPham sanPham = new com.example.datn.entity.SanPham.SanPham();
        sanPham.setId(sanPhamId);
        spct.setSanPham(sanPham);

        // Set thuộc tính
        MauSac mauSac = mauSacRepository.findById(mauSacId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy màu sắc"));
        spct.setMauSac(mauSac);

        CongSuat congSuat = congSuatRepository.findById(congSuatId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy công suất"));
        spct.setCongSuat(congSuat);

        Hang hang = hangRepository.findById(hangId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hãng"));
        spct.setHang(hang);

        NutBam nutBam = nutBamRepository.findById(nutBamId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nút bấm"));
        spct.setNutBam(nutBam);

        // Set thông tin cơ bản
        spct.setSoLuong(soLuong);
        spct.setGia(BigDecimal.valueOf(gia));
        spct.setCanNang(canNang != null ? canNang.floatValue() : null);
        spct.setTrangThai(trangThai);
        spct.setMoTa(moTa);

        // Xử lý hình ảnh
        if (hinhAnh != null && !hinhAnh.isEmpty()) {
            String fileName = StringUtils.cleanPath(hinhAnh.getOriginalFilename());
            String uploadDir = "src/main/resources/static/admin/assets/images/products";
            String filePath = uploadDir + "/" + fileName;

            try {
                // Tạo thư mục nếu chưa tồn tại
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Lưu file
                try (InputStream inputStream = hinhAnh.getInputStream()) {
                    Path path = Paths.get(filePath);
                    Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
                }

                // Tạo HinhAnh entity
                HinhAnh hinhAnhEntity = new HinhAnh();
                hinhAnhEntity.setHinhAnh("/admin/assets/images/products/" + fileName);
                // Lưu HinhAnh trước
                hinhAnhEntity = hinhAnhRepo.save(hinhAnhEntity);
                spct.setHinhAnh(hinhAnhEntity);
            } catch (IOException e) {
                throw new RuntimeException("Không thể lưu file: " + fileName, e);
            }
        }

        SanPhamChiTiet result = sanPhamChiTietRepository.save(spct);
        log.info("Thêm sản phẩm chi tiết thành công với ID: {}", result.getId());
        return result;
    }

    @Override
    @Transactional
    public void updateInline(Long id, Integer soLuong, Double gia) {
        log.info("=== BẮT ĐẦU INLINE UPDATE ===");
        log.info("ID: {}, SoLuong: {}, Gia: {}", id, soLuong, gia);
        
        SanPhamChiTiet spct = sanPhamChiTietRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm chi tiết"));
        log.info("Tìm thấy SPCT: {}", spct.getId());

        // Chỉ cập nhật field được gửi
        if (soLuong != null) {
            spct.setSoLuong(soLuong);
            log.info("Cập nhật số lượng: {}", soLuong);
        }
        
        if (gia != null) {
            spct.setGia(BigDecimal.valueOf(gia));
            log.info("Cập nhật giá: {}", gia);
        }

        try {
            SanPhamChiTiet saved = sanPhamChiTietRepository.save(spct);
            log.info("=== INLINE UPDATE THÀNH CÔNG ===");
            log.info("SPCT đã lưu với ID: {}", saved.getId());
        } catch (Exception e) {
            log.error("=== LỖI KHI LƯU INLINE UPDATE ===", e);
            throw e;
        }
    }

    @Override
    public void delete(Long id) {
        sanPhamChiTietRepository.deleteById(id);
    }
} 