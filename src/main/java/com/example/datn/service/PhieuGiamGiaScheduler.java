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
        Date currentDate = new Date(); // 10:17 AM +07, 08/07/2025
        List<PhieuGiamGia> expiredCoupons = phieuGiamGiaRepo.findExpiredCoupons(currentDate);

        for (PhieuGiamGia pgg : expiredCoupons) {
            pgg.setTrangThai(false);
            phieuGiamGiaRepo.save(pgg);
        }
    }

}
