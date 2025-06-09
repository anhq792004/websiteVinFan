package com.example.datn.service.taiKhoanService;

import com.example.datn.dto.DangKyDto;
import com.example.datn.entity.ChucVu;
import com.example.datn.entity.KhachHang;
import com.example.datn.entity.TaiKhoan;
import com.example.datn.repository.ChucVuRepo;
import com.example.datn.repository.KhachHangRepo.KhachHangRepo;
import com.example.datn.repository.TaiKhoanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Service
public class TaiKhoanService {
    @Autowired
    private TaiKhoanRepo taiKhoanRepository;

    @Autowired
    private ChucVuRepo chucVuRepository;

    @Autowired
    private KhachHangRepo khachHangRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public boolean emailDaTonTai(String email) {
        return taiKhoanRepository.findByEmail(email) != null;
    }

    public void dangKyTaiKhoan(DangKyDto dangKyDto) {
        // Tạo tài khoản
        TaiKhoan taiKhoan = new TaiKhoan();

        // Tạo mã ngẫu nhiên cho tài khoản
        String maTaiKhoan = "TK" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        taiKhoan.setMa(maTaiKhoan);
        taiKhoan.setEmail(dangKyDto.getEmail());

        // Mã hóa mật khẩu
        taiKhoan.setMatKhau(passwordEncoder.encode(dangKyDto.getMatKhau()));

        // Thời gian tạo tài khoản
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        taiKhoan.setNgayTao(new Date());

        // Tìm chức vụ theo tên vị trí "User"
        ChucVu chucVuUser = chucVuRepository.findByViTri("User")
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chức vụ mặc định 'User'"));

        taiKhoan.setChucVu(chucVuUser);
        taiKhoan.setTrangThai(true);

        // Lưu tài khoản trước
        TaiKhoan savedTaiKhoan = taiKhoanRepository.save(taiKhoan);

        // Tạo khách hàng
        KhachHang khachHang = new KhachHang();
        String maKhachHang = "KH" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        khachHang.setMa(maKhachHang);
        khachHang.setTen(dangKyDto.getTen());
        khachHang.setGioiTinh(dangKyDto.getGioiTinh());
        khachHang.setSoDienThoai(dangKyDto.getSoDienThoai());
        khachHang.setNgaySinh(dangKyDto.getNgaySinh());
        khachHang.setNgayTao(new Date());
        khachHang.setTrangThai(true);
        khachHang.setTaiKhoan(savedTaiKhoan);

        // Lưu khách hàng
        khachHangRepository.save(khachHang);
    }


    /**
     * Kiểm tra mật khẩu cũ và đổi mật khẩu
     * @param email Email của tài khoản
     * @param matKhauCu Mật khẩu cũ
     * @param matKhauMoi Mật khẩu mới
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean doiMatKhauVaThongBao(String email, String matKhauCu, String matKhauMoi) {
        TaiKhoan taiKhoan = taiKhoanRepository.findByEmail(email);
        if (taiKhoan == null) {
            return false;
        }

        // Kiểm tra mật khẩu cũ
        if (!passwordEncoder.matches(matKhauCu, taiKhoan.getMatKhau())) {
            return false;
        }

        // Cập nhật mật khẩu mới
        taiKhoan.setMatKhau(passwordEncoder.encode(matKhauMoi));
        taiKhoanRepository.save(taiKhoan);

        // Gửi email thông báo
        String noidungEmail =
                "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #e0e0e0; border-radius: 5px;'>" +
                        "<h2 style='color: #333; text-align: center;'>Thông báo đổi mật khẩu</h2>" +
                        "<p>Kính gửi Quý khách,</p>" +
                        "<p>Mật khẩu tài khoản VinFan của bạn đã được thay đổi thành công.</p>" +
                        "<p>Nếu bạn không thực hiện thay đổi này, vui lòng liên hệ với chúng tôi ngay lập tức.</p>" +
                        "<p>Trân trọng,<br/>Đội ngũ VinFan</p>" +
                        "</div>";

        try {
            emailService.sendEmail(email, "Thông báo đổi mật khẩu VinFan", noidungEmail);
            return true;
        } catch (Exception e) {
            // Vẫn trả về true vì mật khẩu đã được đổi, chỉ có email thông báo là không gửi được
            e.printStackTrace();
            return true;
        }
    }

}
