package com.example.datn.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
}
