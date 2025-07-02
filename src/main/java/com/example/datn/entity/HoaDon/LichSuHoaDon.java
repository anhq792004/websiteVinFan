package com.example.datn.entity.HoaDon;

import com.example.datn.entity.NhanVien.NhanVien;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "lich_su_hoa_don")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LichSuHoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hoa_don")
    HoaDon hoaDon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nhan_vien")
    NhanVien nhanVien;

    @Column(name = "trang_thai")
    Integer trangThai;

    @Column(name = "ngay_tao")
    LocalDateTime ngayTao;

    @Column(name = "mo_ta")
    String moTa;

    @Column(name = "nguoi_tao")
    String nguoiTao;

}
