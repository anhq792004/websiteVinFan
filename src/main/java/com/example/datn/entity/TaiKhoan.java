package com.example.datn.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "tai_khoan")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaiKhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "ma")
    String ma;

    @Column(name = "email")
    String email;

    @Column(name = "mat_khau")
    String matKhau;

    @Column(name = "reset_token")
    String resetToken;

    @Column(name = "ngay_tao")
    LocalDateTime ngayTao;

    @Column(name = "trang_thai")
    Boolean trangThai;
}
