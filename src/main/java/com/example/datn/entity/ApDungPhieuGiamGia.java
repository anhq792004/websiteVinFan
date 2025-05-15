package com.example.datn.entity;

import com.example.datn.entity.SanPham.SanPhamChiTiet;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Table(name = "ap_dung_phieu_giam_gia")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApDungPhieuGiamGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "id_phieu_giam_gia")
    PhieuGiamGia phieuGiamGia;

    @ManyToOne
    @JoinColumn(name = "id_san_pham_chi_tiet")
    SanPhamChiTiet sanPhamChiTiet;

    @Column(name = "gia_tri_giam")
    BigDecimal giaTriGiam;

    @Column(name = "gia_truoc_giam")
    BigDecimal giaTruocGiam;

    @Column(name = "gia_sau_giam")
    BigDecimal giaSauGiam;

    @Column(name = "ngay_ap_dung")
    LocalDateTime ngayApDung;

    @Column(name = "trang_thai")
    Boolean trangThai;
}
