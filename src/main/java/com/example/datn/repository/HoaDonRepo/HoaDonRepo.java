package com.example.datn.repository.HoaDonRepo;

import com.example.datn.entity.HoaDon.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HoaDonRepo extends JpaRepository<HoaDon,Long> {
    @Query("SELECT hd from HoaDon hd order by hd.ngayTao desc ")
    Page<HoaDon> findHoaDonAndSortDay(Pageable pageable);
}
