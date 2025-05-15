package com.example.datn.service.Implements;

import com.example.datn.dto.request.AddSPToHDCTRequest;
import com.example.datn.dto.request.TrangThaiHoaDonRequest;
import com.example.datn.dto.request.UpdateSoLuongRequest;
import com.example.datn.dto.response.LichSuHoaDonResponse;
import com.example.datn.dto.response.LichSuThanhToanResponse;
import com.example.datn.entity.HoaDon.HoaDon;
import com.example.datn.entity.HoaDon.HoaDonChiTiet;
import com.example.datn.entity.HoaDon.LichSuHoaDon;
import com.example.datn.entity.SanPham.SanPhamChiTiet;
import com.example.datn.repository.HoaDonRepo.HoaDonChiTietRepo;
import com.example.datn.repository.HoaDonRepo.HoaDonRepo;
import com.example.datn.repository.HoaDonRepo.LichSuHoaDonRepo;
import com.example.datn.repository.SanPhamRepo.SanPhamChiTietRepo;
import com.example.datn.service.HoaDonService.HoaDonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HoaDonServiceImpl implements HoaDonService {
    private final HoaDonRepo hoaDonRepo;
    private final SanPhamChiTietRepo sanPhamChiTietRepo;
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
    public List<SanPhamChiTiet> findSPCTByIdSanPham() {
        return hoaDonChiTietRepo.findSanPhamChiTietByIdSanPham();
    }

    @Override
    public void deleteSPInHD(Long idSPCT) {
        hoaDonChiTietRepo.deleteSanPhamChiTiet_Id(idSPCT);
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
            lichSuHoaDon.setMoTa("Đơn hàng đã được giao thành công lúc " + LocalDate.now());
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
            lichSuHoaDon.setMoTa("Đơn hàng đã được hủy lúc " + LocalDateTime.now());
            lichSuHoaDonRepo.save(lichSuHoaDon);
        }
    }

    @Override
    public void addSPToHDCT(AddSPToHDCTRequest addSPToHDCTRequest) {
        Optional<SanPhamChiTiet> sanPhamChiTietOptional = sanPhamChiTietRepo.findById(addSPToHDCTRequest.getIdSP());
        Optional<HoaDon> hoaDonOptional = hoaDonRepo.findById(addSPToHDCTRequest.getIdHD());

        if (sanPhamChiTietOptional.isEmpty()) {
            throw new RuntimeException("Không tìm thấy sản phẩm với ID: " + addSPToHDCTRequest.getIdSP());
        }

        if (hoaDonOptional.isEmpty()) {
            throw new RuntimeException("Không tìm thấy hóa đơn với ID: " + addSPToHDCTRequest.getIdHD());
        }
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietOptional.get();
        HoaDon hoaDon = hoaDonOptional.get();

        //check sp tồn tại trong hóa đơn
        Optional<HoaDonChiTiet> existingHDCT = hoaDonChiTietRepo.
                findByHoaDonIdAndSanPhamChiTietId(hoaDon.getId(), sanPhamChiTiet.getId());

        if (existingHDCT.isPresent()) {
            // Nếu đã có thì tăng số lượng lên 1 và cập nhật thành tiền
            HoaDonChiTiet hoaDonChiTiet = existingHDCT.get();
            int soLuongMoi = hoaDonChiTiet.getSoLuong() + 1;
            hoaDonChiTiet.setSoLuong(soLuongMoi);
            BigDecimal thanhTienMoi = hoaDonChiTiet.getGia().multiply(BigDecimal.valueOf(soLuongMoi));
            hoaDonChiTiet.setThanhTien(thanhTienMoi);

            hoaDonChiTietRepo.save(hoaDonChiTiet);
        } else {
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setHoaDon(hoaDonOptional.get());
            hoaDonChiTiet.setSanPhamChiTiet(sanPhamChiTietOptional.get());
            hoaDonChiTiet.setGia(addSPToHDCTRequest.getGia());
            hoaDonChiTiet.setSoLuong(addSPToHDCTRequest.getSoLuong());
            BigDecimal thanhTien = addSPToHDCTRequest.getGia().multiply(BigDecimal.valueOf(addSPToHDCTRequest.getSoLuong()));
            hoaDonChiTiet.setThanhTien(thanhTien);

            hoaDonChiTietRepo.save(hoaDonChiTiet);
        }
    }

    @Override
    public void updateSoluong(UpdateSoLuongRequest request) {
        Optional<SanPhamChiTiet> spOpt = sanPhamChiTietRepo.findById(request.getIdSP());
        Optional<HoaDon> hdOpt = hoaDonRepo.findById(request.getIdHD());
        System.out.println("id san pham là"+ request.getIdSP());
        System.out.println("id hd là"+ request.getIdHD());

        if (spOpt.isEmpty()) {
            throw new RuntimeException("Không tìm thấy sản phẩm với ID: " + request.getIdSP());
        }

        if (hdOpt.isEmpty()) {
            throw new RuntimeException("Không tìm thấy hóa đơn với ID: " + request.getIdHD());
        }

        HoaDon hoaDon = hdOpt.get();
        SanPhamChiTiet sanPhamChiTiet = spOpt.get();

        // 🔍 Tìm xem sản phẩm này đã có trong hóa đơn chưa
        Optional<HoaDonChiTiet> hdctOpt = hoaDonChiTietRepo.findByHoaDonAndSanPhamChiTiet(hoaDon, sanPhamChiTiet);

        HoaDonChiTiet hoaDonChiTiet;
        if (hdctOpt.isPresent()) {
            hoaDonChiTiet = hdctOpt.get();
            hoaDonChiTiet.setSoLuong(request.getSoLuong()); //  Cập nhật số lượng
        } else {
            hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setHoaDon(hoaDon);
            hoaDonChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
            hoaDonChiTiet.setSoLuong(request.getSoLuong());
            hoaDonChiTiet.setGia(request.getGia());
        }

        BigDecimal thanhTien = request.getGia().multiply(BigDecimal.valueOf(request.getSoLuong()));
        hoaDonChiTiet.setThanhTien(thanhTien);

        hoaDonChiTietRepo.save(hoaDonChiTiet);
    }


    @Override
    public void tangSoLuong(Long idHD, Long idSPCT) {
        HoaDonChiTiet hdct = hoaDonChiTietRepo.findByHoaDon_IdAndSanPhamChiTiet_Id(idHD, idSPCT);

        if (hdct != null) {
            // Lấy thông tin sản phẩm chi tiết
            SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepo.findById(idSPCT)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm chi tiết với ID đã cho."));

            // Kiểm tra số lượng sản phẩm trong kho
            int soLuongTrongKho = sanPhamChiTiet.getSoLuong();
            int soLuongTrongHoaDon = hdct.getSoLuong();

            if (soLuongTrongHoaDon < soLuongTrongKho) {
                // Tăng số lượng lên 1
                hdct.setSoLuong(soLuongTrongHoaDon + 1);

                // Cập nhật lại thành tiền
                BigDecimal gia = hdct.getGia();
                hdct.setThanhTien(gia.multiply(BigDecimal.valueOf(hdct.getSoLuong())));

                // Lưu lại bản ghi HoaDonChiTiet đã cập nhật
                hoaDonChiTietRepo.save(hdct);
            } else {
                throw new RuntimeException("Số lượng sản phẩm trong kho không đủ để thêm.");
            }
        } else {
            throw new RuntimeException("Không tìm thấy hóa đơn chi tiết với ID sản phẩm chi tiết.");
        }
    }

    @Override
    public void giamSoLuong(Long idHD, Long idSPCT) {
        // Lấy HoaDonChiTiet theo idHoaDon và idSanPhamChiTiet
        HoaDonChiTiet hdct = hoaDonChiTietRepo.findByHoaDon_IdAndSanPhamChiTiet_Id(idHD, idSPCT);

        // Kiểm tra nếu không có bản ghi nào tìm thấy
        if (hdct != null) {
            // Kiểm tra nếu số lượng hiện tại bằng 0
            if (hdct.getSoLuong() <= 1) {
                throw new RuntimeException("Số lượng không thể nhỏ hơn 1");
            }

            // Giảm số lượng đi 1
            hdct.setSoLuong(hdct.getSoLuong() - 1);

            // Cập nhật lại thành tiền
            BigDecimal gia = hdct.getGia();
            hdct.setThanhTien(gia.multiply(BigDecimal.valueOf(hdct.getSoLuong())));

            // Lưu lại bản ghi HoaDonChiTiet đã cập nhật
            hoaDonChiTietRepo.save(hdct);
        } else {
            throw new RuntimeException("Không tìm thấy hóa đơn chi tiết với ID sản phẩm chi tiết.");
        }
    }

    @Override
    public Integer tongSoLuong(Long idHD) {
        Integer tongSoLuong = hoaDonChiTietRepo.sumSoLuong(idHD);
        return tongSoLuong != null ? tongSoLuong : 0;
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
