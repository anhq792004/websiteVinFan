package com.example.datn.entity;

import com.example.datn.entity.SanPham.SanPham;
import com.example.datn.entity.SanPham.SanPhamChiTiet;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "hinh_anh")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HinhAnh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "hinh_anh")
    String hinhAnh;

    @Column(name = "ngay_tao")
    LocalDateTime ngayTao;

    public String getDuongDan() {
        return hinhAnh;
    }
    
    public void setDuongDan(String duongDan) {
        this.hinhAnh = duongDan;
    }
}
