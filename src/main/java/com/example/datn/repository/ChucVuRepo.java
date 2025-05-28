package com.example.datn.repository;

import com.example.datn.entity.ChucVu;
import com.example.datn.entity.ChucVu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChucVuRepo extends JpaRepository<ChucVu,Long> {
    Optional<ChucVu> findByViTri(String viTri);
}
