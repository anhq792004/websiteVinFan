package com.example.datn.entity.HoaDon;

public enum PhuongThucThanhToan {
    TIEN_MAT("Tiền mặt"),
    MOMO("Momo");
    
    private String displayName;
    
    PhuongThucThanhToan(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
} 