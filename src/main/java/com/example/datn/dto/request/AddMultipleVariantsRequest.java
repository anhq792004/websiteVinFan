package com.example.datn.dto.request;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class AddMultipleVariantsRequest {
    private Long sanPhamId;
    private List<Long> mauSacIds;
    private List<Long> congSuatIds;
    private Long hangId;
    private Long nutBamId;
    private Integer soLuong;
    private BigDecimal gia;
    private Float canNang;
    private String moTa;
    private Boolean trangThai;
} 