package com.example.datn.repository.ThuocTinhRepo;


import com.example.datn.entity.ThuocTinh.Hang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HangRepo extends JpaRepository<Hang, Integer> {
    @Query("SELECT h FROM Hang h " +
            "WHERE (LOWER(h.ten) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "AND (:trang_thai IS NULL OR h.trangThai = :trangThai))")
    Page<Hang> search(String query, Boolean trangThai, Pageable pageable);

    Optional<Hang> findByTen(String ten);

    @Query("SELECT h FROM Hang h " +
            "WHERE LOWER(h.ten) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Hang> searchOnlyTen(String query, Pageable pageable);
}
