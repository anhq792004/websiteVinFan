package com.example.datn.service.Implements.SanPhamImpl;

import com.example.datn.entity.HinhAnh;
import com.example.datn.entity.SanPham.SanPhamChiTiet;
import com.example.datn.entity.ThuocTinh.CongSuat;
import com.example.datn.entity.ThuocTinh.Hang;
import com.example.datn.entity.ThuocTinh.MauSac;
import com.example.datn.entity.ThuocTinh.NutBam;
import com.example.datn.repository.SanPhamRepo.SanPhamChiTietRepository;
import com.example.datn.repository.ThuocTinhRepo.CongSuatRepository;
import com.example.datn.repository.ThuocTinhRepo.HangRepository;
import com.example.datn.repository.ThuocTinhRepo.MauSacRepository;
import com.example.datn.repository.ThuocTinhRepo.NutBamRepository;
import com.example.datn.service.SanPhamSerivce.SanPhamChiTietService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class SanPhamChiTietServiceImpl implements SanPhamChiTietService {

    private final SanPhamChiTietRepository sanPhamChiTietRepository;
    private final MauSacRepository mauSacRepository;
    private final CongSuatRepository congSuatRepository;
    private final HangRepository hangRepository;
    private final NutBamRepository nutBamRepository;

    @Override
    public SanPhamChiTiet findById(Long id) {
        return sanPhamChiTietRepository.findById(id).orElse(null);
    }

    @Override
    public void update(Long id, Long mauSacId, Long congSuatId, Long hangId, Long nutBamId,
                      Integer soLuong, Double gia, Double canNang, Boolean trangThai,
                      String moTa, MultipartFile hinhAnh) {
        SanPhamChiTiet spct = sanPhamChiTietRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm chi tiết"));

        if (mauSacId != null) {
            MauSac mauSac = mauSacRepository.findById(mauSacId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy màu sắc"));
            spct.setMauSac(mauSac);
        }

        if (congSuatId != null) {
            CongSuat congSuat = congSuatRepository.findById(congSuatId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy công suất"));
            spct.setCongSuat(congSuat);
        }

        if (hangId != null) {
            Hang hang = hangRepository.findById(hangId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy hãng"));
            spct.setHang(hang);
        }

        if (nutBamId != null) {
            NutBam nutBam = nutBamRepository.findById(nutBamId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy nút bấm"));
            spct.setNutBam(nutBam);
        }

        if (soLuong != null) spct.setSoLuong(soLuong);
        if (gia != null) spct.setGia(BigDecimal.valueOf(gia));
        if (canNang != null) spct.setCanNang(canNang.floatValue());
        if (trangThai != null) spct.setTrangThai(trangThai);
        if (moTa != null) spct.setMoTa(moTa);

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

                // Cập nhật hoặc tạo mới HinhAnh
                HinhAnh hinhAnhEntity = spct.getHinhAnh();
                if (hinhAnhEntity == null) {
                    hinhAnhEntity = new HinhAnh();
                    hinhAnhEntity.setSanPhamChiTiet(spct);
                }
                hinhAnhEntity.setHinhAnh("/admin/assets/images/products/" + fileName);
                spct.setHinhAnh(hinhAnhEntity);
            } catch (IOException e) {
                throw new RuntimeException("Không thể lưu file: " + fileName, e);
            }
        }

        sanPhamChiTietRepository.save(spct);
    }

    @Override
    public void delete(Long id) {
        sanPhamChiTietRepository.deleteById(id);
    }
} 