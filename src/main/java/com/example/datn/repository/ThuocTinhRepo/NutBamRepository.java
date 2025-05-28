package com.example.datn.repository.ThuocTinhRepo;

import com.example.datn.entity.ThuocTinh.NutBam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NutBamRepository extends JpaRepository<NutBam, Long> {
} 