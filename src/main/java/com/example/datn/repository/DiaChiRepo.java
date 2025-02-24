package com.example.datn.repository;

import com.example.datn.entity.ChucVu;
import com.example.datn.entity.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaChiRepo extends JpaRepository<DiaChi,Long> {
}
