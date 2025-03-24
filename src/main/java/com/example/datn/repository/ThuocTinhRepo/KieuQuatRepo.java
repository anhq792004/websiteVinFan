package com.example.datn.repository.ThuocTinhRepo;

import com.example.datn.entity.ThuocTinh.KieuQuat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KieuQuatRepo extends JpaRepository<KieuQuat, Long> {
}
