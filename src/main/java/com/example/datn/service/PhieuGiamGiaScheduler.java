package com.example.datn.service;

import com.example.datn.entity.PhieuGiamGia;
import com.example.datn.repository.PhieuGiamGiaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PhieuGiamGiaScheduler {
    @Autowired
    private PhieuGiamGiaRepo phieuGiamGiaRepo;

    @Scheduled(fixedRate = 60000) // Chạy mỗi 60 giây
    public void checkAndUpdateExpiredCoupons() {
        Date currentDate = new Date();
        // Lấy tất cả phiếu giảm giá để kiểm tra
        List<PhieuGiamGia> coupons = phieuGiamGiaRepo.findAll();

        for (PhieuGiamGia pgg : coupons) {
            // Nếu phiếu chưa bắt đầu hoặc đã hết hạn
            if ((pgg.getNgayBatDau() != null && pgg.getNgayBatDau().after(currentDate)) ||
                    (pgg.getNgayKetThuc() != null && pgg.getNgayKetThuc().before(currentDate))) {
                if (pgg.isTrangThai()) { // Chỉ cập nhật nếu đang active
                    pgg.setTrangThai(false);
                    phieuGiamGiaRepo.save(pgg);
                    System.out.println("Updated voucher " + pgg.getMa() + " to inactive (trangThai=false)");
                }
            }
        }
    }

}
