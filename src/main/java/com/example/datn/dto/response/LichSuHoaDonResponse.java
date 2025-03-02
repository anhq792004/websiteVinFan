package com.example.datn.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LichSuHoaDonResponse {
    Long idHoaDon;

    Long idNhanVien;

    Integer trangThai;

    String ngayTao;

    String moTa;
}
