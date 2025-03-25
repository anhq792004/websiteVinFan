package com.example.datn.repository.ThuocTinhRepo;

import com.example.datn.entity.ThuocTinh.KieuQuat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface KieuQuatRepo extends JpaRepository<KieuQuat, Long> {
    @Query("SELECT kq FROM KieuQuat kq " +
            "WHERE (LOWER(kq.ten) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "AND (:trang_thai IS NULL OR kq.trangThai = :trangThai))")
    Page<KieuQuat> search(String query, Boolean trangThai, Pageable pageable);

    Optional<KieuQuat> findByTen(String ten);

    @Query("SELECT kq FROM KieuQuat kq " +
            "WHERE LOWER(kq.ten) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<KieuQuat> searchOnlyTen(String query, Pageable pageable);
}
