package com.example.datn.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LichSuThanhToanResponse {
    BigDecimal tongTienSauGiamGia;
    LocalDateTime ngayTao;
    boolean loaiHoaDon;
    String hinhThucThanhToan;
    Integer trangThai;
}
