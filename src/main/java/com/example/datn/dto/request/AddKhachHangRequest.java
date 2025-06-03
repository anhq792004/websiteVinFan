package com.example.datn.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddKhachHangRequest {
    private String ma;

    private String ten;

    private String email;

    private String matKhau;

    private String soDienThoai;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngaySinh;

    private String gioiTinh;

    private String tinhThanhPho;

    private String quanHuyen;

    private String xaPhuong;

    private String soNhaNgoDuong;
}
