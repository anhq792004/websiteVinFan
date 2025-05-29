package com.example.datn.entity.SanPham;

import com.example.datn.entity.HinhAnh;
import com.example.datn.entity.ThuocTinh.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "san_pham_chi_tiet")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SanPhamChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "id_san_pham")
    @JsonBackReference
    SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "id_mau_sac")
    @JsonManagedReference
    MauSac mauSac;

    @ManyToOne
    @JoinColumn(name = "id_cong_suat")
    @JsonManagedReference
    CongSuat congSuat;

    @ManyToOne
    @JoinColumn(name = "id_hang")
    @JsonManagedReference
    Hang hang;

    @ManyToOne
    @JoinColumn(name = "id_nut_bam")
    @JsonManagedReference
    NutBam nutBam;

    @OneToOne
    @JoinColumn(name = "id_hinh_anh")
    @JsonManagedReference
    HinhAnh hinhAnh;

    @Column(name = "gia")
    BigDecimal gia;

    @Column(name = "so_luong")
    Integer soLuong;

    @Column(name = "can_nang")
    Float canNang;

    @Column(name = "mo_ta", columnDefinition = "TEXT")
    String moTa;

    @Column(name = "trang_thai")
    Boolean trangThai;
}
