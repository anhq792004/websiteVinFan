package com.example.datn.entity;

import com.example.datn.entity.SanPham.SanPhamChiTiet;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "phieu_giam_gia")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhieuGiamGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "ma")
    String ma;

    @Column(name = "ten")
    String ten;

    @Column(name = "ngay_bat_dau")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date ngayBatDau;

    @Column(name = "ngay_ket_thuc")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date ngayKetThuc;

    @Column(name = "so_luong")
    Integer soLuong;

    @Column(name = "so_luong_da_su_dung")
    Integer soLuongDaSuDung = 0;

    @Column(name = "loai_giam_gia")
    Boolean loaiGiamGia; // true: phần trăm, false: tiền mặt

    @Column(name = "gia_tri_giam")
    BigDecimal giaTriGiam;

    @Column(name = "gia_tri_giam_toi_da")
    BigDecimal giaTriGiamToiDa;

    @Column(name = "gia_tri_don_hang_toi_thieu")
    BigDecimal giaTriDonHangToiThieu;

    @Column(name = "loai_phieu")
    Boolean loaiPhieu; // true: công khai, false: riêng tư

    @Column(name = "ngay_tao")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date ngayTao;

    @Column(name = "nguoi_tao")
    String nguoiTao;

    @Column(name = "trang_thai")
    boolean trangThai;

}
