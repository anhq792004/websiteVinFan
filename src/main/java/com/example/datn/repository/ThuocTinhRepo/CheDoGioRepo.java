package com.example.datn.repository.ThuocTinhRepo;


import com.example.datn.entity.ThuocTinh.CheDoGio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheDoGioRepo extends JpaRepository<CheDoGio, Integer> {
    @Query("SELECT cdg FROM CheDoGio cdg " +
            "WHERE (LOWER(cdg.ten) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "AND (:trangThai IS NULL OR cdg.trangThai = :trangThai))")
    Page<CheDoGio> search(String query, Boolean trangThai, Pageable pageable);

    Optional<CheDoGio> findByTen(String ten);

    @Query("SELECT cdg FROM CheDoGio cdg " +
            "WHERE LOWER(cdg.ten) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<CheDoGio> searchOnlyTen(String query, Pageable pageable);
}

