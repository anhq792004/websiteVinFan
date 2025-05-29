package com.example.datn.repository.ThuocTinhRepo;

import com.example.datn.entity.ThuocTinh.CongSuat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CongSuatRepository extends JpaRepository<CongSuat, Long> {
} 