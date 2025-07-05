package com.example.datn.repository;

import com.example.datn.entity.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DiaChiRepo extends JpaRepository<DiaChi,Long> {
    List<DiaChi> findByKhachHang_Id(Long id);
}
