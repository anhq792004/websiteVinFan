package com.example.datn.repository.ThuocTinhRepo;

import com.example.datn.entity.ThuocTinh.KieuQuat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository

public interface KieuQuatRepo extends JpaRepository<KieuQuat, Integer> {
    @Query("SELECT kq FROM KieuQuat kq " +
            "WHERE (LOWER(kq.ten) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "AND (:trangThai IS NULL OR kq.trangThai = :trangThai))")
    Page<KieuQuat> search(String query, Boolean trangThai, Pageable pageable);

    @Query("SELECT kq FROM KieuQuat kq " +
            "WHERE LOWER(kq.ten) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<KieuQuat> searchOnlyTen(String query, Pageable pageable);
    Optional<KieuQuat> findByTen(String ten);
}

