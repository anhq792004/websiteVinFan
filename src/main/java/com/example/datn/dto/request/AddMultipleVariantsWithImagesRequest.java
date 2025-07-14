package com.example.datn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddMultipleVariantsWithImagesRequest {
    private Long sanPhamId;
    private List<VariantDetailWithImage> variants;
    private Map<Integer, MultipartFile> variantImages; 
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VariantDetailWithImage {
        private Long mauSacId;
        private Long congSuatId;
        private Long hangId;
        private Long nutBamId;
        private Integer soLuong;
        private Integer gia;
        private Double canNang;
        private String moTa;
        private Boolean trangThai;
        private MultipartFile hinhAnh; 
    }
} 