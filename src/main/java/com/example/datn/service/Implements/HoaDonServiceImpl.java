package com.example.datn.service.Implements;

import com.example.datn.entity.HoaDon.HoaDon;
import com.example.datn.repository.HoaDonRepo.HoaDonRepo;
import com.example.datn.service.HoaDonService.HoaDonSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HoaDonServiceImpl implements HoaDonSerivce {
    private final HoaDonRepo hoaDonRepo;

    @Override
    public Page<HoaDon> findAllHoaDonAndSortDay(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return hoaDonRepo.findHoaDonAndSortDay(pageable);
    }
}
