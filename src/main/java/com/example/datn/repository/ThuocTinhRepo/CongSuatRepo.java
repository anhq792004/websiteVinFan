package com.example.datn.repository.ThuocTinhRepo;

import com.example.datn.entity.ThuocTinh.CongSuat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CongSuatRepo extends JpaRepository<CongSuat, Long> {
    @Query("SELECT cs FROM CongSuat cs " +
            "WHERE (LOWER(cs.ten) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "AND (:trangThai IS NULL OR cs.trangThai = :trangThai))")
    Page<CongSuat> search(String query, Boolean trangThai, Pageable pageable);

    Optional<CongSuat> findByTen(String ten);

    @Query("SELECT cs FROM CongSuat cs " +
            "WHERE LOWER(cs.ten) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<CongSuat> searchOnlyTen(String query, Pageable pageable);
}

