package com.example.datn.service.Implements;

import com.example.datn.entity.HinhAnh;
import com.example.datn.entity.SanPham.SanPham;
import com.example.datn.entity.SanPham.SanPhamChiTiet;
import com.example.datn.repository.HinhAnhRepo;
import com.example.datn.repository.SanPhamRepo.SanPhamChiTietRepo;
import com.example.datn.repository.SanPhamRepo.SanPhamRepo;
import com.example.datn.service.FileUploadService;
import com.example.datn.service.SanPhamSerivce.SanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SanPhamServiceImpl implements SanPhamService {
    private final SanPhamRepo sanPhamRepo;
    private final SanPhamChiTietRepo sanPhamChiTietRepo;
    private final HinhAnhRepo hinhAnhRepo;
    private final FileUploadService fileUploadService;

    @Override
    public Page<SanPham> findAllSanPham(int page, int size, String search, Long kieuQuatId, Boolean trangThai) {
        Pageable pageable = PageRequest.of(page, size);

        if (search != null && !search.isEmpty()) {
            if (kieuQuatId != null && trangThai != null) {
                return sanPhamRepo.findByTenContainingAndKieuQuatIdAndTrangThai(search, kieuQuatId, trangThai, pageable);
            } else if (kieuQuatId != null) {
                return sanPhamRepo.findByTenContainingAndKieuQuatId(search, kieuQuatId, pageable);
            } else if (trangThai != null) {
                return sanPhamRepo.findByTenContainingAndTrangThai(search, trangThai, pageable);
            } else {
                return sanPhamRepo.findByTenContaining(search, pageable);
            }
        } else {
            if (kieuQuatId != null && trangThai != null) {
                return sanPhamRepo.findByKieuQuatIdAndTrangThai(kieuQuatId, trangThai, pageable);
            } else if (kieuQuatId != null) {
                return sanPhamRepo.findByKieuQuatId(kieuQuatId, pageable);
            } else if (trangThai != null) {
                return sanPhamRepo.findByTrangThai(trangThai, pageable);
            } else {
                return sanPhamRepo.findAll(pageable);
            }
        }
    }

    @Override
    public List<SanPham> findAllActiveProducts() {
        return sanPhamRepo.findByTrangThaiTrue();
    }

    @Override
    public void saveSanPham(SanPham sanPham) {
        // Đảm bảo ngày tạo được thiết lập
        if (sanPham.getNgayTao() == null) {
            sanPham.setNgayTao(LocalDateTime.now());
        }
        sanPhamRepo.save(sanPham);
    }

    @Override
    public void saveSanPhamWithImage(SanPham sanPham, MultipartFile imageFile) {
        // Đảm bảo ngày tạo được thiết lập
        if (sanPham.getNgayTao() == null) {
            sanPham.setNgayTao(LocalDateTime.now());
        }

        // Lưu sản phẩm trước để có ID
        SanPham savedSanPham = sanPhamRepo.save(sanPham);

        try {
            // Sử dụng FileUploadService để lưu file
            String imagePath = fileUploadService.saveFile(imageFile);
            // Tạo đối tượng HinhAnh
            HinhAnh hinhAnh = new HinhAnh();
            hinhAnh.setHinhAnh(imagePath);
            HinhAnh savedHinhAnh = hinhAnhRepo.save(hinhAnh);

            // Tạo SanPhamChiTiet mới với ảnh
            SanPhamChiTiet spct = new SanPhamChiTiet();
            spct.setSanPham(savedSanPham);
            spct.setHinhAnh(savedHinhAnh);
            spct.setTrangThai(true);

            // Khởi tạo danh sách nếu chưa có
            if (savedSanPham.getSanPhamChiTiet() == null) {
                savedSanPham.setSanPhamChiTiet(new ArrayList<>());
            }

            sanPhamChiTietRepo.save(spct);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi lưu hình ảnh: " + e.getMessage());
        }
    }

    @Override
    public void updateSanPham(SanPham sanPham) {
        // Lấy sản phẩm hiện tại để giữ ngày tạo
        Optional<SanPham> existingSanPham = sanPhamRepo.findById(sanPham.getId());
        if (existingSanPham.isPresent()) {
            // Giữ ngày tạo ban đầu
            sanPham.setNgayTao(existingSanPham.get().getNgayTao());
            // Giữ danh sách chi tiết
            sanPham.setSanPhamChiTiet(existingSanPham.get().getSanPhamChiTiet());
        }
        sanPhamRepo.save(sanPham);
    }

    @Override
    public void updateSanPhamWithImage(SanPham sanPham, MultipartFile imageFile) {
        // Lấy sản phẩm hiện tại
        Optional<SanPham> existingSanPhamOpt = sanPhamRepo.findById(sanPham.getId());
        if (existingSanPhamOpt.isPresent()) {
            SanPham existingSanPham = existingSanPhamOpt.get();
            // Giữ ngày tạo ban đầu
            sanPham.setNgayTao(existingSanPham.getNgayTao());
            // Giữ danh sách chi tiết
            sanPham.setSanPhamChiTiet(existingSanPham.getSanPhamChiTiet());

            // Lưu sản phẩm
            sanPhamRepo.save(sanPham);

            try {
                // Sử dụng FileUploadService để lưu file
                String imagePath = fileUploadService.saveFile(imageFile);
                // Kiểm tra xem sản phẩm đã có hình ảnh chưa
                boolean hasImage = false;
                HinhAnh existingImage = null;

                if (existingSanPham.getSanPhamChiTiet() != null && !existingSanPham.getSanPhamChiTiet().isEmpty()) {
                    for (SanPhamChiTiet spct : existingSanPham.getSanPhamChiTiet()) {
                        if (spct.getHinhAnh() != null) {
                            hasImage = true;
                            existingImage = spct.getHinhAnh();
                            
                            // Xóa hình ảnh cũ và cập nhật hình ảnh mới
                            String oldImagePath = existingImage.getHinhAnh();
                            if (oldImagePath != null && !oldImagePath.isEmpty()) {
                                fileUploadService.deleteFile(oldImagePath);
                            }
                            
                            existingImage.setHinhAnh(imagePath);
                            hinhAnhRepo.save(existingImage);
                            break;
                        }
                    }
                }

                // Nếu không có hình ảnh, tạo mới
                if (!hasImage) {
                    // Tạo đối tượng HinhAnh mới
                    HinhAnh hinhAnh = new HinhAnh();
                    hinhAnh.setHinhAnh(imagePath);
                    HinhAnh savedHinhAnh = hinhAnhRepo.save(hinhAnh);

                    // Tạo SanPhamChiTiet mới với ảnh
                    SanPhamChiTiet spct = new SanPhamChiTiet();
                    spct.setSanPham(sanPham);
                    spct.setHinhAnh(savedHinhAnh);
                    spct.setTrangThai(true);

                    // Khởi tạo danh sách nếu chưa có
                    if (sanPham.getSanPhamChiTiet() == null) {
                        sanPham.setSanPhamChiTiet(new ArrayList<>());
                    }

                    sanPhamChiTietRepo.save(spct);
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Lỗi khi lưu hình ảnh: " + e.getMessage());
            }
        }
    }

    @Override
    public Optional<SanPham> findSanPhamById(Long id) {
        return sanPhamRepo.findById(id);
    }

    @Override
    public boolean thayDoiTrangThaiSanPham(Long id) {
        Optional<SanPham> sanPhamOptional = sanPhamRepo.findById(id);
        if (sanPhamOptional.isPresent()) {
            SanPham sanPham = sanPhamOptional.get();
            sanPham.setTrangThai(!sanPham.getTrangThai());
            sanPhamRepo.save(sanPham);
            return true;
        }
        return false;
    }
}
