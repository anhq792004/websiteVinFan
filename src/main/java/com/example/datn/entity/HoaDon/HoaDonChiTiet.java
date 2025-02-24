package com.example.datn.entity.HoaDon;

import com.example.datn.entity.NhanVien;
import com.example.datn.entity.SanPham.SanPhamChiTiet;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "hoa_don_chi_tiet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HoaDonChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hoa_don")
    HoaDon hoaDon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_san_pham_chi_tiet")
    SanPhamChiTiet sanPhamChiTiet;

    @Column(name = "so_luong")
    Integer soLuong;

    @Column(name = "gia_ban")
    BigDecimal giaBan;

    @Column(name = "gia_sau_giam")
    BigDecimal giaSauGiam;

    @Column(name = "thanh_tien")
    BigDecimal thanh_tien;

    @Column(name = "trang_thai")
    Integer trangThai;

}
