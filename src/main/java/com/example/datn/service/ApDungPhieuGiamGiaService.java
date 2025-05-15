package com.example.datn.service;

import com.example.datn.entity.ApDungPhieuGiamGia;
import com.example.datn.entity.PhieuGiamGia;
import com.example.datn.entity.SanPham.SanPhamChiTiet;
import com.example.datn.repository.ApDungPhieuGiamGiaRepo;
import com.example.datn.repository.PhieuGiamGiaRepo;
import com.example.datn.repository.SanPhamRepo.SanPhamChiTietRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApDungPhieuGiamGiaService {
    @Autowired
    private ApDungPhieuGiamGiaRepo apDungPhieuGiamGiaRepo;

    @Autowired
    private PhieuGiamGiaRepo phieuGiamGiaRepo;

    @Autowired
    private SanPhamChiTietRepo sanPhamChiTietRepo;

    /**
     * Áp dụng phiếu giảm giá cho nhiều sản phẩm chi tiết
     * @param phieuGiamGiaId ID của phiếu giảm giá
     * @param sanPhamChiTietIds Danh sách ID của sản phẩm chi tiết được chọn
     * @param nguoiTao Người tạo
     * @return Danh sách các áp dụng phiếu giảm giá đã được lưu
     */
    @Transactional
    public List<ApDungPhieuGiamGia> apDungPhieuGiamGiaChoNhieuSanPham(
            Long phieuGiamGiaId,
            List<Long> sanPhamChiTietIds) {

        // Lấy phiếu giảm giá
        PhieuGiamGia phieuGiamGia = phieuGiamGiaRepo.findById(phieuGiamGiaId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu giảm giá với ID: " + phieuGiamGiaId));

        // Thời gian hiện tại
        Date now = new Date();

        // Kiểm tra phiếu giảm giá còn hiệu lực không
        if (phieuGiamGia.getNgayBatDau().after(now) || phieuGiamGia.getNgayKetThuc().before(now)) {
            throw new RuntimeException("Phiếu giảm giá không còn hiệu lực");
        }

        // Chuyển đổi danh sách ID thành danh sách sản phẩm chi tiết
        List<SanPhamChiTiet> sanPhamChiTiets = sanPhamChiTietRepo.findAllById(sanPhamChiTietIds);

        // Tạo danh sách áp dụng phiếu giảm giá
        List<ApDungPhieuGiamGia> apDungPhieuGiamGias = sanPhamChiTiets.stream().map(spct -> {
            // Kiểm tra xem sản phẩm này đã áp dụng phiếu giảm giá khác chưa
            ApDungPhieuGiamGia apDungHienTai = apDungPhieuGiamGiaRepo.findBySanPhamChiTietAndTrangThaiTrue(spct);
            if (apDungHienTai != null) {
                // Nếu đã áp dụng phiếu giảm giá khác, hủy áp dụng cũ
                apDungHienTai.setTrangThai(false);
                apDungPhieuGiamGiaRepo.save(apDungHienTai);
            }

            // Tạo áp dụng mới
            ApDungPhieuGiamGia apDungPhieuGiamGia = new ApDungPhieuGiamGia();
            apDungPhieuGiamGia.setPhieuGiamGia(phieuGiamGia);
            apDungPhieuGiamGia.setSanPhamChiTiet(spct);
            apDungPhieuGiamGia.setNgayApDung(LocalDateTime.now());
            apDungPhieuGiamGia.setTrangThai(true);

            // Lưu giá trước khi giảm
            BigDecimal giaTruocGiam = spct.getGia();
            apDungPhieuGiamGia.setGiaTruocGiam(giaTruocGiam);

            // Tính toán giá sau khi giảm
            BigDecimal giaSauGiam;
            BigDecimal giaTriGiam;

            if (phieuGiamGia.isLoaiGiamGia()) {
                // Giảm theo phần trăm
                BigDecimal phanTramGiam = phieuGiamGia.getGiaTriGiam().divide(new BigDecimal(100));
                giaTriGiam = giaTruocGiam.multiply(phanTramGiam);
                giaSauGiam = giaTruocGiam.subtract(giaTriGiam);
            } else {
                // Giảm theo số tiền cố định
                giaTriGiam = phieuGiamGia.getGiaTriGiam();
                giaSauGiam = giaTruocGiam.subtract(giaTriGiam);
                if (giaSauGiam.compareTo(BigDecimal.ZERO) < 0) {
                    giaSauGiam = BigDecimal.ZERO;
                }
            }

            apDungPhieuGiamGia.setGiaTriGiam(giaTriGiam);
            apDungPhieuGiamGia.setGiaSauGiam(giaSauGiam);

            // Cập nhật giá sau giảm vào sản phẩm chi tiết
            spct.setGiaSauGiam(giaSauGiam);
            sanPhamChiTietRepo.save(spct);

            return apDungPhieuGiamGia;
        }).collect(Collectors.toList());

        // Lưu tất cả áp dụng phiếu giảm giá
        return apDungPhieuGiamGiaRepo.saveAll(apDungPhieuGiamGias);
    }

    /**
     * Hủy áp dụng phiếu giảm giá cho một sản phẩm chi tiết
     * @param apDungPhieuGiamGiaId ID của áp dụng phiếu giảm giá
     */
    @Transactional
    public void huyApDungPhieuGiamGia(Long apDungPhieuGiamGiaId) {
        ApDungPhieuGiamGia apDungPhieuGiamGia = apDungPhieuGiamGiaRepo.findById(apDungPhieuGiamGiaId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy áp dụng phiếu giảm giá với ID: " + apDungPhieuGiamGiaId));

        // Hủy áp dụng
        apDungPhieuGiamGia.setTrangThai(false);
        apDungPhieuGiamGiaRepo.save(apDungPhieuGiamGia);

        // Cập nhật lại giá sau giảm của sản phẩm chi tiết
        SanPhamChiTiet spct = apDungPhieuGiamGia.getSanPhamChiTiet();
        spct.setGiaSauGiam(null); // hoặc có thể đặt lại bằng giá gốc
        sanPhamChiTietRepo.save(spct);
    }

    /**
     * Hủy tất cả áp dụng phiếu giảm giá của một phiếu giảm giá
     * @param phieuGiamGiaId ID của phiếu giảm giá
     */
    @Transactional
    public void huyTatCaApDungTheoPhieuGiamGia(Long phieuGiamGiaId) {
        List<ApDungPhieuGiamGia> danhSachApDung = apDungPhieuGiamGiaRepo.findByPhieuGiamGiaId(phieuGiamGiaId);

        for (ApDungPhieuGiamGia apDung : danhSachApDung) {
            apDung.setTrangThai(false);

            // Cập nhật lại giá sau giảm của sản phẩm chi tiết
            SanPhamChiTiet spct = apDung.getSanPhamChiTiet();
            spct.setGiaSauGiam(null); // hoặc có thể đặt lại bằng giá gốc
            sanPhamChiTietRepo.save(spct);
        }

        apDungPhieuGiamGiaRepo.saveAll(danhSachApDung);
    }

    /**
     * Lấy danh sách sản phẩm chi tiết đã được áp dụng phiếu giảm giá
     * @param phieuGiamGiaId ID của phiếu giảm giá
     * @return Danh sách ID của sản phẩm chi tiết
     */
    public List<Long> getSanPhamChiTietIdsApDungPhieuGiamGia(Long phieuGiamGiaId) {
        return apDungPhieuGiamGiaRepo.findSanPhamChiTietIdsByPhieuGiamGiaId(phieuGiamGiaId);
    }

    /**
     * Kiểm tra sản phẩm chi tiết đã áp dụng phiếu giảm giá chưa
     * @param sanPhamChiTietId ID của sản phẩm chi tiết
     * @return true nếu đã áp dụng, false nếu chưa
     */
    public boolean kiemTraSanPhamDaApDungPhieuGiamGia(Long sanPhamChiTietId) {
        SanPhamChiTiet spct = sanPhamChiTietRepo.findById(sanPhamChiTietId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm chi tiết với ID: " + sanPhamChiTietId));
        return apDungPhieuGiamGiaRepo.existsBySanPhamChiTietAndTrangThaiTrue(spct);
    }
}
