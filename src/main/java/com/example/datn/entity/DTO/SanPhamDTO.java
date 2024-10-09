package com.example.datn.entity.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SanPhamDTO {
//    id_san_pham BIGINT FOREIGN KEY REFERENCES san_pham(id),
//    id_dieu_khien_tu_xa INT FOREIGN KEY REFERENCES dieu_khien_tu_xa(id),
    private String ten;
    private Integer kieuQuatId;
    private List<Integer> mauSacIds;
    private List<Integer> cheDoGioIds;
    private List<Integer> congSuatIds;
    private Integer nutBamId;
    private Integer chatLieuCanhId;
    private Integer duongKinhCanhId;
    private Integer chatLieuKhungId;
    private Integer deQuatId;
    private Integer chieuCaoId;
    private Integer hangId;
}
