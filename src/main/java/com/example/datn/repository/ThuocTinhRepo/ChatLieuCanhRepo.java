package com.example.datn.repository.ThuocTinhRepo;

import com.example.datn.entity.ThuocTinh.ChatLieuCanh;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChatLieuCanhRepo extends JpaRepository<ChatLieuCanh, Long> {
    @Query("SELECT cls FROM ChatLieuCanh cls " +
            "WHERE (LOWER(cls.ten) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "AND (:trangThai IS NULL OR cls.trangThai = :trangThai))")
    Page<ChatLieuCanh> search(String query, Boolean trangThai, Pageable pageable);

    Optional<ChatLieuCanh> findByTen(String ten);

    @Query("SELECT cls FROM ChatLieuCanh cls " +
            "WHERE LOWER(cls.ten) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<ChatLieuCanh> searchOnlyTen(String query, Pageable pageable);
}
