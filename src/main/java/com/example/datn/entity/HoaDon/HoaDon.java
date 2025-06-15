package com.example.datn.entity.HoaDon;

import com.example.datn.entity.DiaChi;
import com.example.datn.entity.KhachHang;
import com.example.datn.entity.NhanVien.NhanVien;
import com.example.datn.entity.PhieuGiamGia;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "hoa_don")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_khach_hang")
    KhachHang khachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nhan_vien")
    NhanVien nhanVien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_phieu_giam_gia")
    PhieuGiamGia phieuGiamGia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dia_chi")
    DiaChi diaChi;

    @Column(name = "ma")
    String ma;

    @Column(name = "ten_nguoi_nhan")
    String tenNguoiNhan;

    @Column(name = "sdt_nguoi_nhan")
    String sdtNguoiNhan;

    @Column(name = "tong_tien")
    BigDecimal tongTien;

    @Column(name = "tong_tien_sau_giam_gia")
    BigDecimal tongTienSauGiamGia;

    @Column(name = "phi_van_chuyen")
    BigDecimal phiVanChuyen;

    @Column(name = "ghi_chu")
    String ghiChu;

    @Column(name = "loai_hoa_don")
    Boolean loaiHoaDon;

    @Column(name = "ngay_tao")
    LocalDateTime ngayTao;

    @Column(name = "ngay_sua")
    LocalDateTime ngaySua;

    @Column(name = "nguoi_tao")
    String nguoiTao;

    @Column(name = "trang_thai")
    Integer trangThai;

    @Column(name = "phuong_thuc_thanh_toan")
    String phuongThucThanhToan;
}
