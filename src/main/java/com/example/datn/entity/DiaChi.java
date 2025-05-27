package com.example.datn.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "dia_chi")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiaChi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "phuong")
    String phuong;

    @Column(name = "xa")
    String xa;

    @Column(name = "tinh")
    String tinh;

    @Column(name = "so_nha_ngo_duong")
    String soNhaNgoDuong;

    @Column(name = "thanhPho")
    String thanh_pho;

    @Column(name = "ngay_tao")
    String ngayTao;

    @Column(name = "trang_thai")
    Boolean trangThai;
}
