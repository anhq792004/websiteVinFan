package com.example.datn.service.Implements;

import com.example.datn.entity.HoaDon.HoaDon;
import com.example.datn.entity.HoaDon.LichSuHoaDon;
import com.example.datn.entity.KhachHang;
import com.example.datn.repository.HoaDonRepo.HoaDonRepo;
import com.example.datn.repository.HoaDonRepo.LichSuHoaDonRepo;
import com.example.datn.service.BanHang.BanHangService;
import com.example.datn.service.HoaDonService.HoaDonSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BanHangServiceImpl implements BanHangService {

    private final HoaDonSerivce hoaDonSerivce;
    private final HoaDonRepo hoaDonRepo;
    private final LichSuHoaDonRepo lichSuHoaDonRepo;
    @Transactional
    @Override
    public void taoHoaDonCho(HoaDon hoaDon) {
        List<HoaDon> listHoaDon = hoaDonSerivce.findAll();
        int count = (int) listHoaDon.stream()
                .filter(sl -> sl.getTrangThai() == hoaDonSerivce.getTrangThaiHoaDon().getHoaDonCho())
                .count();
        if (count >= 10) {
            // Thông báo khi số lượng hóa đơn chờ vượt quá 10
            throw new IllegalStateException("Số lượng hóa đơn chờ tối qua là 10");
        }
        hoaDon.setMa(hoaDonSerivce.generateOrderCode());
        hoaDon.setTrangThai(hoaDonSerivce.getTrangThaiHoaDon().getChoXacNhan());
        hoaDon.setNgayTao(LocalDateTime.now());
        hoaDon.setLoaiHoaDon(true);

        hoaDonRepo.saveAndFlush(hoaDon);

        //tao lich su hoa don
        LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
        lichSuHoaDon.setHoaDon(hoaDon);
        lichSuHoaDon.setNgayTao(hoaDon.getNgayTao());
        lichSuHoaDon.setTrangThai(hoaDon.getTrangThai());

        lichSuHoaDonRepo.save(lichSuHoaDon);
    }

    @Override
    public List<HoaDon> findHoaDon() {
        return hoaDonRepo.findAll().stream().
                filter(loc -> loc.getTrangThai() == hoaDonSerivce.getTrangThaiHoaDon()
                        .getHoaDonCho()).toList();
    }

    @Override
    public KhachHang getKhachHangLe(Long id) {
        return null;
    }
}
