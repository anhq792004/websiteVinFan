package com.example.datn.controller.webController;
import com.example.datn.entity.SanPham.SanPham;
import com.example.datn.entity.SanPham.SanPhamChiTiet;
import com.example.datn.repository.SanPhamRepo.SanPhamRepo;
import com.example.datn.service.SanPhamSerivce.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/fanTech")
public class giaoDienController {
    @Autowired
    private SanPhamService sanPhamService;
    @Autowired
    private SanPhamRepo sanPhamRepo;

    @GetMapping("/index")
    public String index(Model model) {
        // Lấy danh sách sản phẩm có trạng thái true (đang hoạt động)
        List<SanPham> sanPhamList = sanPhamService.findAllActiveProducts();

        // Thêm danh sách sản phẩm vào model để sử dụng trong template
        model.addAttribute("sanPhamList", sanPhamList);

        return "user/index";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam("id") Long id, Model model) {
        try {
            // Tìm sản phẩm theo ID
            Optional<SanPham> sanPhamOpt = sanPhamRepo.findById(id);

            if (sanPhamOpt.isPresent()) {
                SanPham sanPham = sanPhamOpt.get();
                model.addAttribute("sanPham", sanPham);

                // Lấy chi tiết sản phẩm đầu tiên (nếu có)
                if (sanPham.getSanPhamChiTiet() != null && !sanPham.getSanPhamChiTiet().isEmpty()) {
                    SanPhamChiTiet chiTietDauTien = sanPham.getSanPhamChiTiet().get(0);
                    model.addAttribute("chiTietDauTien", chiTietDauTien);
                }

                return "user/detail";
            } else {
                // Nếu không tìm thấy sản phẩm, redirect về trang chủ
                return "redirect:/fanTech/index";
            }
        } catch (Exception e) {
            // Xử lý lỗi, redirect về trang chủ
            return "redirect:/fanTech/index";
        }
    }

    @GetMapping("/cart")
    public String cart() {
        return "user/cart";
    }

}
