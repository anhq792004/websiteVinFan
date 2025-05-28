package com.example.datn.service.HoaDonService;

import com.example.datn.dto.request.AddKHToHDCTRequest;
import com.example.datn.dto.request.AddSPToHDCTRequest;
import com.example.datn.dto.request.TrangThaiHoaDonRequest;
import com.example.datn.dto.request.UpdateSoLuongRequest;
import com.example.datn.dto.response.LichSuHoaDonResponse;
import com.example.datn.dto.response.LichSuThanhToanResponse;
import com.example.datn.entity.HoaDon.HoaDon;
import com.example.datn.entity.HoaDon.HoaDonChiTiet;
import com.example.datn.entity.HoaDon.LichSuHoaDon;
import com.example.datn.entity.SanPham.SanPhamChiTiet;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;

public interface HoaDonService {
    //hoaDon
    List<HoaDon> findAll();

    Page<HoaDon> findAllHoaDonAndSortDay(int page, int size);

//    Page<HoaDon> searchHoaDon(String query, Boolean loaiHoaDon, LocalDateTime tuNgay, LocalDateTime denNgay, Integer trangThai, Pageable pageable);


    Optional<HoaDon> findHoaDonById(Long id);

    String generateOrderCode();


    //hoaDonChiTiet

    List<HoaDonChiTiet> listHoaDonChiTiets(Long id);

    LichSuThanhToanResponse getLSTTByHoaDonId(Long id);

    List<SanPhamChiTiet> findSPCTByIdSanPham();

    void deleteSPInHD(Long idSPCT);

    void xacNhan(Long id);

    void giaoHang(Long id);

    void hoanThanh(Long id);

    void huy(Long id);

    void addSPToHDCT(AddSPToHDCTRequest addSPToHDCTRequest);

    void addKHToHDCT(AddKHToHDCTRequest addKHToHDCTRequest);

    void updateSoluong(UpdateSoLuongRequest updateSoLuongRequest);

    void tangSoLuong(Long idHD, Long idSPCT);

    void giamSoLuong(Long idHD, Long idSPCT);

    Integer tongSoLuong(Long idHD);


    //lichSuHoaDon
    List<LichSuHoaDon> lichSuHoaDonList(Long id);

    List<LichSuHoaDonResponse> lichSuHoaDonResponseList(Long id);

    //trang thai hoa don
    TrangThaiHoaDonRequest getTrangThaiHoaDon();
}
