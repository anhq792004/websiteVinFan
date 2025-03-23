package com.example.datn.entity.SanPham;

import com.example.datn.entity.ThuocTinh.KieuQuat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "san_pham")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "ma")
    String ma;

    @Column(name = "ten")
    String ten;

    @Column(name = "mo_ta")
    String moTa;

    @Column(name = "ngay_tao")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDateTime ngayTao;


    @Column(name = "trang_thai")
    Boolean trangThai;

    @ManyToOne
    @JoinColumn(name = "id_kieu_quat")
    KieuQuat kieuQuat;

    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<SanPhamChiTiet> sanPhamChiTiet;

}
