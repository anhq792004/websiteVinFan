package com.example.datn.repository.ThuocTinhRepo;

import com.example.datn.entity.ThuocTinh.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, Long> {
} 