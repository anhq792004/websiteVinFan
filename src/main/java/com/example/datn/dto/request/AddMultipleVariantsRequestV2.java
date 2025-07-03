package com.example.datn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddMultipleVariantsRequestV2 {
    private Long sanPhamId;
    private List<VariantDetail> variants;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VariantDetail {
        private Long mauSacId;
        private Long congSuatId;
        private Long hangId;
        private Long nutBamId;
        private Integer soLuong;
        private Integer gia;
        private Double canNang;
        private String moTa;
        private Boolean trangThai;
    }
} 