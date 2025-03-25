package com.example.datn.repository.ThuocTinhRepo;

import com.example.datn.entity.ThuocTinh.NutBam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NutBamRepo extends JpaRepository<NutBam, Integer> {
    @Query("SELECT n FROM NutBam n " +
            "WHERE (LOWER(n.ten) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "AND (:trangThai IS NULL OR n.trangThai = :trangThai))")
    Page<NutBam> search(String query, Boolean trangThai, Pageable pageable);

    Optional<NutBam> findByTen(String ten);

    @Query("SELECT n FROM NutBam n " +
            "WHERE LOWER(n.ten) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<NutBam> searchOnlyTen(String query, Pageable pageable);
}
