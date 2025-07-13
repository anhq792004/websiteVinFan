package com.example.datn.service;

import com.example.datn.entity.KhachHang;
import com.example.datn.entity.PhieuGiamGia;
import com.example.datn.entity.PhieuGiamGiaKhachHang;
import com.example.datn.repository.PhieuGiamGiaKhachHangRepo;
import com.example.datn.repository.PhieuGiamGiaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PhieuGiamGiaSercvice {
    @Autowired
    private PhieuGiamGiaRepo phieuGiamGiaRepo;

    @Autowired
    private PhieuGiamGiaKhachHangRepo phieuGiamGiaKhachHangRepo;

    public List<Map<String, Object>> getAvailableVouchers(KhachHang khachHang, BigDecimal orderAmount) {
        List<Map<String, Object>> availableVouchers = new ArrayList<>();
        Date now = new Date();

        try {
            // 1. Lấy phiếu giảm giá CÔNG KHAI (loaiPhieu = true)
            List<PhieuGiamGia> publicVouchers = phieuGiamGiaRepo.findAll();

            for (PhieuGiamGia voucher : publicVouchers) {
                System.out.println("Checking public voucher: " + voucher.getMa() +
                        ", Start Date: " + voucher.getNgayBatDau() +
                        ", End Date: " + voucher.getNgayKetThuc() +
                        ", Status: " + voucher.isTrangThai() +
                        ", Current Time: " + now);

                if (voucher.getLoaiPhieu() == true && // công khai
                        voucher.isTrangThai() && // active
                        voucher.getNgayBatDau() != null && voucher.getNgayBatDau().before(now) && // đã bắt đầu
                        voucher.getNgayKetThuc() != null && voucher.getNgayKetThuc().after(now) && // chưa kết thúc
                        isVoucherValid(voucher, orderAmount)) {
                    availableVouchers.add(createVoucherMap(voucher, "public"));
                    System.out.println("Added public voucher: " + voucher.getMa());
                }
            }

            // 2. Lấy phiếu giảm giá CÁ NHÂN (loaiPhieu = false)
            List<PhieuGiamGiaKhachHang> privateVoucherRelations = phieuGiamGiaKhachHangRepo
                    .findByKhachHangAndTrangThaiAndDaSuDung(khachHang, true, false);

            System.out.println("DEBUG: Found " + privateVoucherRelations.size() + " private voucher relations");

            for (PhieuGiamGiaKhachHang relation : privateVoucherRelations) {
                PhieuGiamGia voucher = relation.getPhieuGiamGia();

                System.out.println("Checking private voucher: " + voucher.getMa() +
                        ", Start Date: " + voucher.getNgayBatDau() +
                        ", End Date: " + voucher.getNgayKetThuc() +
                        ", Status: " + voucher.isTrangThai() +
                        ", Current Time: " + now);

                if (voucher.isTrangThai() && // voucher active
                        voucher.getNgayBatDau() != null && voucher.getNgayBatDau().before(now) && // đã bắt đầu
                        voucher.getNgayKetThuc() != null && voucher.getNgayKetThuc().after(now) && // chưa kết thúc
                        isVoucherValid(voucher, orderAmount)) { // đủ điều kiện đơn hàng
                    availableVouchers.add(createVoucherMap(voucher, "private"));
                    System.out.println("Added private voucher: " + voucher.getMa());
                } else {
                    System.out.println("Private voucher " + voucher.getMa() + " not valid");
                }
            }

            // Sắp xếp theo giá trị giảm giá
            availableVouchers.sort((v1, v2) -> {
                BigDecimal value1 = (BigDecimal) v1.get("giaTriGiam");
                BigDecimal value2 = (BigDecimal) v2.get("giaTriGiam");
                return value2.compareTo(value1);
            });

            System.out.println("FINAL RESULT: Total available vouchers: " + availableVouchers.size());

        } catch (Exception e) {
            System.err.println("ERROR in getAvailableVouchers: " + e.getMessage());
            e.printStackTrace();
        }

        return availableVouchers;
    }

    private boolean isVoucherValid(PhieuGiamGia voucher, BigDecimal orderAmount) {
        // Kiểm tra số lượng còn lại
        if (voucher.getSoLuongDaSuDung() >= voucher.getSoLuong()) {
            System.out.println(" Voucher " + voucher.getMa() + " hết số lượng");
            return false;
        }

        // Kiểm tra đơn hàng tối thiểu
        if (voucher.getGiaTriDonHangToiThieu() != null &&
                orderAmount.compareTo(voucher.getGiaTriDonHangToiThieu()) < 0) {
            System.out.println(" Voucher " + voucher.getMa() + " không đủ điều kiện đơn hàng tối thiểu");
            return false;
        }

        System.out.println(" Voucher " + voucher.getMa() + " valid");
        return true;
    }

    private Map<String, Object> createVoucherMap(PhieuGiamGia voucher, String type) {
        Map<String, Object> voucherMap = new HashMap<>();
        voucherMap.put("id", voucher.getId());
        voucherMap.put("ma", voucher.getMa());
        voucherMap.put("ten", voucher.getTen());
        voucherMap.put("giaTriGiam", voucher.getGiaTriGiam());

        // ✅ Sửa: Gửi rõ ràng loại giảm: "PERCENT" hoặc "AMOUNT"
        String loaiGiam = (voucher.getLoaiGiamGia() != null && voucher.getLoaiGiamGia()) ? "PERCENT" : "AMOUNT";
        voucherMap.put("loaiGiam", loaiGiam);

        voucherMap.put("giaTriDonHangToiThieu", voucher.getGiaTriDonHangToiThieu());
        voucherMap.put("giaTriGiamToiDa", voucher.getGiaTriGiamToiDa());
        voucherMap.put("soLuong", voucher.getSoLuong());
        voucherMap.put("soLuongDaSuDung", voucher.getSoLuongDaSuDung());
        voucherMap.put("soLuongConLai", voucher.getSoLuong() - voucher.getSoLuongDaSuDung());
        voucherMap.put("type", type);
        voucherMap.put("loaiPhieu", voucher.getLoaiPhieu());
        return voucherMap;
    }


    public BigDecimal calculateDiscountAmount(PhieuGiamGia voucher, BigDecimal orderAmount) {
        BigDecimal discountAmount = BigDecimal.ZERO;

        if (voucher != null && voucher.getLoaiGiamGia()) { // Giảm theo phần trăm
            BigDecimal percent = voucher.getGiaTriGiam(); // Giả sử getGiaTriGiam() trả về BigDecimal
            if (percent == null || percent.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("ERROR: giaTriGiam is invalid: " + percent);
                return BigDecimal.ZERO;
            }
            discountAmount = orderAmount.multiply(percent.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
            System.out.println("PERCENT: orderAmount=" + orderAmount + ", giaTriGiam=" + percent + ", discountAmount=" + discountAmount);
        } else if (voucher != null) { // Giảm tiền mặt
            discountAmount = voucher.getGiaTriGiam() != null ? voucher.getGiaTriGiam() : BigDecimal.ZERO;
            System.out.println("FIXED: orderAmount=" + orderAmount + ", giaTriGiam=" + voucher.getGiaTriGiam() + ", discountAmount=" + discountAmount);
        }

        if (voucher != null && voucher.getGiaTriGiamToiDa() != null && discountAmount.compareTo(voucher.getGiaTriGiamToiDa()) > 0) {
            discountAmount = voucher.getGiaTriGiamToiDa();
            System.out.println("Applied max discount: " + discountAmount);
        }

        return discountAmount;
    }
}
