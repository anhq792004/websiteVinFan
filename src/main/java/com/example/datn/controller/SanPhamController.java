//package com.example.datn.controller;
//
//import com.example.datn.entity.SanPham;
//import com.example.datn.entity.SanPhamChiTiet;
//import com.example.datn.entity.thuoc_tinh.*;
//import com.example.datn.repository.SPCTRepo;
//import com.example.datn.repository.SanPhamRepo;
//import com.example.datn.repository.ThuocTinhRepo.*;
//import com.example.datn.service.SanPhamService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.io.File;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Controller
//@RequestMapping("/admin")
//public class SanPhamController {
//    @Autowired
//    SanPhamService sanPhamService;
//
//    @Autowired
//    ChatLieuCanhRepo chatLieuCanhRepo;
//    @Autowired
//    ChatLieuKhungRepo chatLieuKhungRepo;
//    @Autowired
//    CheDoGioRepo cheDoGioRepo;
//    @Autowired
//    ChieuCaoRepo chieuCaoRepo;
//    @Autowired
//    CongSuatRepo congSuatRepo;
//    @Autowired
//    DeQuatRepo deQuatRepo;
//    @Autowired
//    DieuKhienTuXaRepo dieuKhienTuXaRepo;
//    @Autowired
//    DuongKinhCanhRepo duongKinhCanhRepo;
//    @Autowired
//    HangRepo hangRepo;
//    @Autowired
//    KieuQuatRepo kieuQuatRepo;
//    @Autowired
//    MauSacRepo mauSacRepo;
//    @Autowired
//    NutBamRepo nutBamRepo;
//    @Autowired
//    SPCTRepo spctRepo;
//    @Autowired
//    SanPhamRepo sanPhamRepo;
//
//    @ModelAttribute("listMau")
//    public List<MauSac> listMau() {
//        return mauSacRepo.findAll();
//    }
//
//    @ModelAttribute("listChatLieuCanh")
//    public List<ChatLieuCanh> listChatLieuCanh() {
//        return chatLieuCanhRepo.findAll();
//    }
//
//    @ModelAttribute("listKieuQuat")
//    public List<KieuQuat> listKieuQuat() {
//        return kieuQuatRepo.findAll();
//    }
//
//    @ModelAttribute("listCongSuat")
//    public List<CongSuat> listCongSuat() {
//        return congSuatRepo.findAll();
//    }
//
//    @ModelAttribute("listDeQuat")
//    public List<DeQuat> listDeQuat() {
//        return deQuatRepo.findAll();
//    }
//
//    @ModelAttribute("listHang")
//    public List<Hang> listHang() {
//        return hangRepo.findAll();
//    }
//
//    @ModelAttribute("listCheDoGio")
//    public List<CheDoGio> listCheDoGio() {
//        return cheDoGioRepo.findAll();
//    }
//
//    @ModelAttribute("listDieuKhienTuXa")
//    public List<DieuKhienTuXa> listDieuKhienTuXa() {
//        return dieuKhienTuXaRepo.findAll();
//    }
//
//    @ModelAttribute("listDuongKinhCanh")
//    public List<DuongKinhCanh> listDuongKinhCanh() {
//        return duongKinhCanhRepo.findAll();
//    }
//
//    @ModelAttribute("listNutBam")
//    public List<NutBam> listNutBam() {
//        return nutBamRepo.findAll();
//    }
//
//    @ModelAttribute("listChieuCao")
//    public List<ChieuCao> listChieuCao() {
//        return chieuCaoRepo.findAll();
//    }
//
//    @ModelAttribute("listChatLieuKhung")
//    public List<ChatLieuKhung> listChatLieuKhung() {
//        return chatLieuKhungRepo.findAll();
//    }
//
//    @GetMapping("/san-pham")
//    public String searchProducts(@RequestParam(value = "query", defaultValue = "") String query,
//                                 @RequestParam(value = "minPrice", defaultValue = "0") BigDecimal minPrice,
//                                 @RequestParam(value = "maxPrice", defaultValue = "0") BigDecimal maxPrice,
//                                 @RequestParam(defaultValue = "0") int page,
//                                 @RequestParam(defaultValue = "5") int size,
//                                 Model model) {
//        if (maxPrice.compareTo(BigDecimal.ZERO) == 0) {
//            maxPrice = new BigDecimal("9999999999");
//        }
//        Page<SanPhamChiTiet> searchPage = sanPhamService.searchProducts(query, minPrice, maxPrice, PageRequest.of(page, size));
//        model.addAttribute("listSP", searchPage);
//        model.addAttribute("query", query);
//        model.addAttribute("minPrice", minPrice);
//        model.addAttribute("maxPrice", maxPrice);
//        return "admin/san_pham/index";
//    }
//
//    @GetMapping("/san-pham/viewUpdate/{id}")
//    public String viewUpdateProduct(@PathVariable("id") Long id, Model model) {
//        SanPhamChiTiet sanPhamChiTiet = sanPhamService.findById(id);
//        if (sanPhamChiTiet == null) {
//            return "redirect:/admin/san-pham";
//        }
//        model.addAttribute("spUpdate", sanPhamChiTiet);
//        model.addAttribute("hinhAnhHienTai", sanPhamChiTiet.getHinh_anh());
//        return "admin/san_pham/san_pham_update";
//    }
//
//
//    @GetMapping("/san-pham/viewAdd")
//    public String viewAddProduct() {
//        return "admin/san_pham/san_pham_add";
//    }
//
//    @PostMapping("/san-pham/add-bien-the")
//    public String addProduct(
//            @RequestParam("sanPham.ten") String ten,
//            @RequestParam("sanPham.kieuQuat.id") Integer kieuQuatId,
//            @RequestParam("mauSac.id") List<Integer> mauSacIds,
//            @RequestParam("cheDoGio.id") List<Integer> cheDoGioIds,
//            @RequestParam("congSuat.id") List<Integer> congSuatIds,
//
//            @RequestParam("nutBam.id") Integer nutBamId,
//            @RequestParam("chatLieuCanh.id") Integer chatLieuCanhId,
//            @RequestParam("duongKinhCanh.id") Integer duongKinhCanhId,
//            @RequestParam("chatLieuKhung.id") Integer chatLieuKhungId,
//            @RequestParam("deQuat.id") Integer deQuatId,
//            @RequestParam("chieuCao.id") Integer chieuCaoId,
//            @RequestParam("hang.id") Integer hangId,
//            Model model) {
//
//        SanPham sanPham = new SanPham();
//        String ma = sanPhamService.taoMaTuDong();  // Tạo mã sản phẩm bằng tự động
//        sanPham.setMa(ma);
//        sanPham.setTen(ten);
//        sanPham.setKieuQuat(new KieuQuat(kieuQuatId));
//        sanPham.setTrang_thai(true);
//        sanPham.setNgay_tao(new Date());
//
//        List<SanPhamChiTiet> listSPCT = new ArrayList<>();
//
//        for (Integer mauSacId : mauSacIds) {
//            for (Integer congSuatId : congSuatIds) {
//                for (Integer cheDoGioId : cheDoGioIds) {
//                    SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
//                    sanPhamChiTiet.setSanPham(sanPham);
//                    sanPhamChiTiet.setMauSac(new MauSac(mauSacId));
//                    sanPhamChiTiet.setCongSuat(new CongSuat(congSuatId));
//                    sanPhamChiTiet.setCheDoGio(new CheDoGio(cheDoGioId));
//                    sanPhamChiTiet.setNutBam(new NutBam(nutBamId));
//                    sanPhamChiTiet.setChatLieuCanh(new ChatLieuCanh(chatLieuCanhId));
//                    sanPhamChiTiet.setDuongKinhCanh(new DuongKinhCanh(duongKinhCanhId));
//                    sanPhamChiTiet.setChatLieuKhung(new ChatLieuKhung(chatLieuKhungId));
//                    sanPhamChiTiet.setDeQuat(new DeQuat(deQuatId));
//                    sanPhamChiTiet.setChieuCao(new ChieuCao(chieuCaoId));
//                    sanPhamChiTiet.setHang(new Hang(hangId));
//
//                    sanPhamChiTiet.setGia(new BigDecimal(100000));
//                    sanPhamChiTiet.setSo_luong(1);
//                    sanPhamChiTiet.setTrang_thai(true);
//                    sanPhamChiTiet.setNgay_tao(new Date());
//                    sanPhamChiTiet.setNguoi_tao("admin");
//
//                    listSPCT.add(sanPhamChiTiet);
//                }
//            }
//        }
//
//        sanPhamService.create(sanPham, listSPCT);
//        model.addAttribute("listSPCT",listSPCT);
//        return "admin/san_pham/san_pham_add";
//    }
//    @PostMapping("/san-pham/updateBienThe")
//    public String updateBienThe(@RequestParam("id") Long sanPhamId,
//                              @RequestParam("newPrice") BigDecimal newPrice,
//                              RedirectAttributes redirectAttributes) {
//        try {
//            SanPhamChiTiet sanPhamChiTiet = spctRepo.findById(sanPhamId).orElseThrow();
//            sanPhamChiTiet.setGia(newPrice);
//            spctRepo.save(sanPhamChiTiet);
//            redirectAttributes.addFlashAttribute("message", "Cập nhật giá thành công!");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Cập nhật giá thất bại!");
//        }
//        return "redirect:/admin/san-pham";
//    }
//
//    @PostMapping("/san-pham/updatePrice")
//    public String updatePrice(@RequestParam("id") Long sanPhamId,
//                              @RequestParam("newPrice") BigDecimal newPrice,
//                              RedirectAttributes redirectAttributes) {
//        try {
//            SanPhamChiTiet sanPhamChiTiet = spctRepo.findById(sanPhamId).orElseThrow();
//            sanPhamChiTiet.setGia(newPrice);
//            spctRepo.save(sanPhamChiTiet);
//            redirectAttributes.addFlashAttribute("message", "Cập nhật giá thành công!");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Cập nhật giá thất bại!");
//        }
//        return "redirect:/admin/san-pham";
//    }
//
//    @PostMapping("/san-pham/update")
//    public String updateProduct(
//            @RequestParam("id") Long sanPhamId,
//            @RequestParam("sanPham.ten") String ten,
//            @RequestParam("sanPham.mo_ta") String moTa,
//            @RequestParam("gia") BigDecimal gia,
//            @RequestParam("gia_nhap") BigDecimal giaNhap,
//            @RequestParam("so_luong") Integer soLuong,
//            @RequestParam("trang_thai") Boolean trangThai,
//            @RequestParam("sanPham.kieuQuat.id") Integer kieuQuatId,
//            @RequestParam("mauSac.id") Integer mauSacId,
//            @RequestParam("nutBam.id") Integer nutBamId,
//            @RequestParam("congSuat.id") Integer congSuatId,
//            @RequestParam("chatLieuCanh.id") Integer chatLieuCanhId,
//            @RequestParam("duongKinhCanh.id") Integer duongKinhCanhId,
//            @RequestParam("chatLieuKhung.id") Integer chatLieuKhungId,
//            @RequestParam("deQuat.id") Integer deQuatId,
//            @RequestParam("chieuCao.id") Integer chieuCaoId,
//            @RequestParam("hang.id") Integer hangId,
//            @RequestParam("cheDoGio.id") Integer cheDoGioId,
//            @RequestParam("dieuKhienTuXa.id") Integer dieuKhienTuXaId,
//            @RequestParam("hinhAnhFile") MultipartFile hinhAnhFile,
//            RedirectAttributes redirectAttributes) {
//
//        try {
//            SanPhamChiTiet sanPhamChiTiet = sanPhamService.findById(sanPhamId);
//            if (sanPhamChiTiet == null) {
//                redirectAttributes.addFlashAttribute("errorMessage", "Sản phẩm không tồn tại!");
//                return "redirect:/admin/san-pham";
//            }
//
//            SanPham sanPham = sanPhamChiTiet.getSanPham();
//            sanPham.setTen(ten);
//            sanPham.setMo_ta(moTa);
//            sanPham.setTrang_thai(trangThai);
//            sanPham.setKieuQuat(new KieuQuat(kieuQuatId));
//
//            sanPhamChiTiet.setGia(gia);
//            sanPhamChiTiet.setGia_nhap(giaNhap);
//            sanPhamChiTiet.setSo_luong(soLuong);
//            sanPhamChiTiet.setMauSac(new MauSac(mauSacId));
//            sanPhamChiTiet.setNutBam(new NutBam(nutBamId));
//            sanPhamChiTiet.setCongSuat(new CongSuat(congSuatId));
//            sanPhamChiTiet.setChatLieuCanh(new ChatLieuCanh(chatLieuCanhId));
//            sanPhamChiTiet.setDuongKinhCanh(new DuongKinhCanh(duongKinhCanhId));
//            sanPhamChiTiet.setChatLieuKhung(new ChatLieuKhung(chatLieuKhungId));
//            sanPhamChiTiet.setDeQuat(new DeQuat(deQuatId));
//            sanPhamChiTiet.setChieuCao(new ChieuCao(chieuCaoId));
//            sanPhamChiTiet.setHang(new Hang(hangId));
//            sanPhamChiTiet.setCheDoGio(new CheDoGio(cheDoGioId));
//            sanPhamChiTiet.setDieuKhienTuXa(new DieuKhienTuXa(dieuKhienTuXaId));
//
//            if (!hinhAnhFile.isEmpty()) {
//                String fileName = hinhAnhFile.getOriginalFilename();
//                sanPhamChiTiet.setHinh_anh(fileName);
//
//                File file = new File("/src/main/resources/static/admin/images/" + fileName); // Thay đổi đường dẫn đến thư mục hình ảnh
//                hinhAnhFile.transferTo(file);
//            }
//
//            sanPham.setNgay_sua(new Date());
//            sanPhamChiTiet.setNgay_sua(new Date());
//            sanPhamChiTiet.setNguoi_sua("admin");
//
//            sanPhamService.update(sanPhamChiTiet);
//            redirectAttributes.addFlashAttribute("message", "Cập nhật sản phẩm thành công!");
//        } catch (IOException e) {
//            e.printStackTrace();
//            redirectAttributes.addFlashAttribute("errorMessage", "Cập nhật sản phẩm thất bại!");
//        }
//
//        return "redirect:/admin/san-pham";
//    }
//
//    @GetMapping("/demo")
//    public String ht(Model model) {
//        return "admin/san_pham/san_pham_add1";
//    }
//
//}
