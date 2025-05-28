package com.example.datn.repository.ThuocTinhRepo;

import com.example.datn.entity.ThuocTinh.Hang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HangRepository extends JpaRepository<Hang, Long> {
} 