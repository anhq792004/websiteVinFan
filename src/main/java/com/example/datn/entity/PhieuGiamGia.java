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

    @OneToOne(optional = true)
    @JoinColumn(name = "id_san_pham_chi_tiet")
    SanPhamChiTiet sanPhamChiTiet;

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

    @Column(name = "loai_giam_gia")
    boolean loaiGiamGia;

    @Column(name = "gia_tri_giam")
    BigDecimal giaTriGiam;

    @Column(name = "ngay_tao")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date ngayTao;

    @Column(name = "trang_thai")
    boolean trangThai;

    public boolean isLoaiGiamGia() {
        return loaiGiamGia;
    }
}
