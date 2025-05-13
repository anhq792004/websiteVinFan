package com.example.datn.service.Implements;

import com.example.datn.dto.request.TrangThaiHoaDonRequest;
import com.example.datn.dto.response.LichSuHoaDonResponse;
import com.example.datn.dto.response.LichSuThanhToanResponse;
import com.example.datn.entity.HoaDon.HoaDon;
import com.example.datn.entity.HoaDon.HoaDonChiTiet;
import com.example.datn.entity.HoaDon.LichSuHoaDon;
import com.example.datn.entity.NhanVien;
import com.example.datn.entity.SanPham.SanPhamChiTiet;
import com.example.datn.repository.HoaDonRepo.HoaDonChiTietRepo;
import com.example.datn.repository.HoaDonRepo.HoaDonRepo;
import com.example.datn.repository.HoaDonRepo.LichSuHoaDonRepo;
import com.example.datn.service.HoaDonService.HoaDonSerivce;
import com.example.datn.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HoaDonServiceImpl implements HoaDonSerivce {
    private final HoaDonRepo hoaDonRepo;
    private final HoaDonChiTietRepo hoaDonChiTietRepo;
    private final LichSuHoaDonRepo lichSuHoaDonRepo;

    @Override
    public List<HoaDon> findAll() {
        return hoaDonRepo.findAll();
    }

    //hoaDon
    @Override
    public Page<HoaDon> findAllHoaDonAndSortDay(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return hoaDonRepo.findHoaDonAndSortDay(pageable);
    }

//    @Override
//    public Page<HoaDon> searchHoaDon(String query, Boolean loaiHoaDon, LocalDateTime tuNgay, LocalDateTime denNgay, Integer trangThai, Pageable pageable) {
//        if (trangThai == null) {
//            return hoaDonRepo.searchHoaDonKhongtrangThai(query, loaiHoaDon, tuNgay, denNgay, pageable);
//        }
//        return hoaDonRepo.searchHoaDon(query, loaiHoaDon, tuNgay, denNgay, trangThai, pageable);
//    }

    @Override
    public Optional<HoaDon> findHoaDonById(Long id) {
        return hoaDonRepo.findById(id);
    }

    @Override
    public String generateOrderCode() {
        // Lấy số lượng hóa đơn hiện tại
        Long count = hoaDonRepo.count(); // Số lượng hóa đơn trong DB
        // Tạo mã hóa đơn với tiền tố "HD" và số thứ tự
        return String.format("HD%03d", count + 1); // VD: HD001, HD002
    }

    @Override
    public List<HoaDonChiTiet> listHoaDonChiTiets(Long id) {
        return hoaDonChiTietRepo.findByHoaDon_Id(id);
    }

    @Override
    public LichSuThanhToanResponse getLSTTByHoaDonId(Long idHoaDon) {
        return hoaDonRepo.findThanhToanHoaDonId(idHoaDon);
    }

    @Override
    public void xacNhan(Long id) {
        // Tìm kiếm HoaDon dựa trên ID
        Optional<HoaDon> hoaDonOptional = hoaDonRepo.findById(id);
        List<HoaDonChiTiet> listHDCT = hoaDonChiTietRepo.findByHoaDon_Id(id);


        if (hoaDonOptional.isPresent()) {
//            for (HoaDonChiTiet hdct : listHDCT) {
//                SanPhamChiTiet spct = hdct.getSanPhamChiTiet();
//                int soLuongTon = spct.getSoLuong(); // Số lượng hiện tại trong kho
//                int soLuongBan = hdct.getSoLuong();    // Số lượng trong hóa đơn chi tiết
//
//                if (soLuongTon < soLuongBan) {
//                    throw new RuntimeException("Số lượng tồn kho không đủ cho sản phẩm: " + spct.getSanPham().getTen());
//                }
//            }
            HoaDon hoaDon = hoaDonOptional.get();

            // Cập nhật trạng thái của HoaDon
            hoaDon.setTrangThai(getTrangThaiHoaDon().getDaXacNhan());
            hoaDonRepo.save(hoaDon);

            // Tạo lịch sử cập nhật
            LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
            lichSuHoaDon.setHoaDon(hoaDon);
            lichSuHoaDon.setTrangThai(getTrangThaiHoaDon().getDaXacNhan());
            lichSuHoaDon.setNgayTao(LocalDateTime.now());
            lichSuHoaDon.setMoTa("Admin đã xác nhận đơn hàng");

            lichSuHoaDonRepo.save(lichSuHoaDon);
        }
    }

    @Override
    public void giaoHang(Long id) {
        Optional<HoaDon> hoaDonOptional = hoaDonRepo.findById(id);
        if (hoaDonOptional.isPresent()) {
            HoaDon hoaDon = hoaDonOptional.get();
            // Cập nhật trạng thái của HoaDon giao hàng
            hoaDon.setTrangThai(getTrangThaiHoaDon().getDangGiaoHang());
            hoaDonRepo.save(hoaDon);
            // Tạo một bản ghi lịch sử cho HoaDon đã được giao hàng
            LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
            lichSuHoaDon.setHoaDon(hoaDon);
            lichSuHoaDon.setTrangThai(getTrangThaiHoaDon().getDangGiaoHang());
            lichSuHoaDon.setNgayTao(LocalDateTime.now());
            lichSuHoaDon.setMoTa("Đơn hàng đã được gửi lúc " + LocalDate.now());
            lichSuHoaDonRepo.save(lichSuHoaDon);
        }
    }

    @Override
    public void hoanThanh(Long id) {
        Optional<HoaDon> hoaDonOptional = hoaDonRepo.findById(id);
        if (hoaDonOptional.isPresent()) {
            HoaDon hoaDon = hoaDonOptional.get();
            // Cập nhật trạng thái của HoaDon hoàn thành
            hoaDon.setTrangThai(getTrangThaiHoaDon().getHoanThanh());
            hoaDon.setNgaySua(LocalDateTime.now());
            hoaDonRepo.save(hoaDon);
            // Tạo một bản ghi lịch sử cho HoaDon hoàn thành
            LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
            lichSuHoaDon.setHoaDon(hoaDon);
            lichSuHoaDon.setTrangThai(getTrangThaiHoaDon().getHoanThanh());
            lichSuHoaDon.setNgayTao(LocalDateTime.now());
            lichSuHoaDon.setMoTa("Đơn hàng đã được giao thành công lúc "+ LocalDate.now());
            lichSuHoaDonRepo.save(lichSuHoaDon);
        }
    }

    @Override
    public void huy(Long id) {
        Optional<HoaDon> hoaDonOptional = hoaDonRepo.findById(id);
        if (hoaDonOptional.isPresent()) {
            HoaDon hoaDon = hoaDonOptional.get();
            hoaDon.setTrangThai(getTrangThaiHoaDon().getHuy());
            hoaDonRepo.save(hoaDon);
            // Tạo một bản ghi lịch sử cho HoaDon hủy
            LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
            lichSuHoaDon.setHoaDon(hoaDon);
            lichSuHoaDon.setTrangThai(getTrangThaiHoaDon().getHuy());
            lichSuHoaDon.setNgayTao(LocalDateTime.now());
            lichSuHoaDon.setMoTa("Đơn hàng đã được hủy lúc "+ LocalDateTime.now());
            lichSuHoaDonRepo.save(lichSuHoaDon);
        }
    }


    // lichSuHoaDon
    @Override
    public List<LichSuHoaDon> lichSuHoaDonList(Long id) {
        return lichSuHoaDonRepo.findLichSuHoaDonByIdHoaDon(id);
    }

    @Override
    public List<LichSuHoaDonResponse> lichSuHoaDonResponseList(Long id) {
        return null;
    }

    @Override
    public TrangThaiHoaDonRequest getTrangThaiHoaDon() {
        TrangThaiHoaDonRequest request = new TrangThaiHoaDonRequest(0, 1, 2,
                3, 4, 5);
        return request;
    }
}
