package com.example.datn.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DangKyDto {

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 1, message = "Mật khẩu phải có ít nhất 6 ký tự")
    String matKhau;

    @NotBlank(message = "Xác nhận mật khẩu không được để trống")
    String xacNhanMatKhau;

    // Thông tin khách hàng
    @NotBlank(message = "Tên không được để trống")
    String ten;

    @NotBlank(message = "Giới tính không được để trống")
    String gioiTinh;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Size(min = 10, max = 11, message = "Số điện thoại phải có 10-11 số")
    String soDienThoai;

    @NotNull(message = "Ngày sinh không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date ngaySinh;
}
