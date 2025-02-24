package com.example.datn.repository.SanPhamRepo;

import com.example.datn.entity.SanPham.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SanPhamRepo extends JpaRepository<SanPham,Long> {
}
