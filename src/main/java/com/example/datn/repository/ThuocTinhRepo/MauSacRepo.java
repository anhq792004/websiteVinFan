package com.example.datn.repository.ThuocTinhRepo;

import com.example.datn.entity.ThuocTinh.MauSac;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MauSacRepo extends JpaRepository<MauSac, Integer> {
    @Query("SELECT m FROM MauSac m " +
            "WHERE (LOWER(m.ten) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "AND (:trangThai IS NULL OR m.trangThai = :trangThai))")
    Page<MauSac> search(String query, Boolean trangThai, Pageable pageable);

    Optional<MauSac> findByTen(String ten);

    @Query("SELECT m FROM MauSac m " +
            "WHERE LOWER(m.ten) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<MauSac> searchOnlyTen(String query, Pageable pageable);
}
