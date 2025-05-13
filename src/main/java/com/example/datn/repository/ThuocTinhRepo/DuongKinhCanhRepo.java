package com.example.datn.repository.ThuocTinhRepo;

import com.example.datn.entity.ThuocTinh.DuongKinhCanh;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository

public interface DuongKinhCanhRepo extends JpaRepository<DuongKinhCanh, Integer> {
    @Query("SELECT dkc FROM DuongKinhCanh dkc " +
            "WHERE (LOWER(dkc.ten) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "AND (:trangThai IS NULL OR dkc.trangThai = :trangThai))")
    Page<DuongKinhCanh> search(String query, Boolean trangThai, Pageable pageable);

    Optional<DuongKinhCanh> findByTen(String ten);

    @Query("SELECT dkc FROM DuongKinhCanh dkc " +
            "WHERE LOWER(dkc.ten) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<DuongKinhCanh> searchOnlyTen(String query, Pageable pageable);
}

