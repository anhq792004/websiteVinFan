package com.example.datn.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SanPhamRequest {
    private Long idSanPham;
    private Long idMauSac;
    private Long idCongSuat;
    private Long idNutBam;
    private Long idChatLieuCanh;
    private Long idDuongKinhCanh;
    private Long idChatLieuKhung;
    private Long idHang;
    private Long idCheDoGio;
    private Long idHinhAnh;
    private BigDecimal gia;
    private Integer soLuong;
    private Date ngayTao;
    private String nguoiTao;
    private Boolean trangThai;
}
