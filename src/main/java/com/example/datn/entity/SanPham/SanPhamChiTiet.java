package com.example.datn.entity.SanPham;

import com.example.datn.entity.HinhAnh;
import com.example.datn.entity.ThuocTinh.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    @JoinColumn(name = "id_mau_sac")
    MauSac mauSac;

    @ManyToOne
    @JoinColumn(name = "id_san_pham")
    SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "id_chat_lieu_canh")
    ChatLieuCanh chatLieuCanh;

    @ManyToOne
    @JoinColumn(name = "id_nut_bam")
    NutBam nutBam;

    @ManyToOne
    @JoinColumn(name = "id_duong_kinh_canh")
    DuongKinhCanh duongKinhCanh;

    @ManyToOne
    @JoinColumn(name = "id_chat_lieu_khung")
    ChatLieuKhung chatLieuKhung;

    @ManyToOne
    @JoinColumn(name = "id_cong_suat")
    CongSuat congSuat;

    @ManyToOne
    @JoinColumn(name = "id_hang")
    Hang hang;

    @ManyToOne
    @JoinColumn(name = "id_che_do_gio")
    CheDoGio cheDoGio;

    @ManyToOne
    @JoinColumn(name = "id_hinh_anh")
    HinhAnh hinhAnh;

    @Column(name = "gia")
    BigDecimal gia;

    @Column(name = "so_luong")
    Integer soLuong;

    @Column(name = "mo_ta")
    String moTa;

    @Column(name = "ngay_tao")
    LocalDateTime ngayTao;

    @Column(name = "ngay_sua")
    LocalDateTime ngaySua;

    @Column(name = "nguoi_tao")
    String nguoiTao;

    @Column(name = "trang_thai")
    Boolean trangThai;

    @Column(name = "gia_sau_giam")
    BigDecimal giaSauGiam;

}
