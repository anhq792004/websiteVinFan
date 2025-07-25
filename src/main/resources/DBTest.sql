CREATE DATABASE datn
USE datn

CREATE TABLE chuc_vu (
    id BIGINT PRIMARY KEY IDENTITY(1, 1),
    vi_tri NVARCHAR(255),
    mo_ta NVARCHAR(255)
);

CREATE TABLE tai_khoan (
    id INT PRIMARY KEY IDENTITY(1, 1),
	id_chuc_vu BIGINT FOREIGN KEY REFERENCES chuc_vu(id),
    ma NVARCHAR(255),
    email NVARCHAR(255),
    mat_khau NVARCHAR(255),
    reset_token NVARCHAR(255),
    ngay_tao DATETIME,
    trang_thai BIT,
); 

CREATE TABLE kieu_quat (
    id BIGINT PRIMARY KEY IDENTITY(1, 1),
    ten NVARCHAR(255),
    trang_thai BIT
);

CREATE TABLE san_pham (
    id BIGINT PRIMARY KEY IDENTITY(1, 1),
    id_kieu_quat BIGINT FOREIGN KEY REFERENCES kieu_quat(id),
    ma NVARCHAR(255),
    ten NVARCHAR(255),
	mo_ta NVARCHAR(MAX),
    ngay_tao DATETIME,
    trang_thai BIT
);

CREATE TABLE nut_bam (
    id BIGINT PRIMARY KEY IDENTITY(1, 1),
    ten NVARCHAR(255),
    trang_thai BIT
);

CREATE TABLE cong_suat (
    id BIGINT PRIMARY KEY IDENTITY(1, 1),
    ten NVARCHAR(255),
    trang_thai BIT
);

CREATE TABLE mau_sac (
    id BIGINT PRIMARY KEY IDENTITY(1, 1),
    ten NVARCHAR(255),
    trang_thai BIT
);

CREATE TABLE hang (
    id BIGINT PRIMARY KEY IDENTITY(1, 1),
    ten NVARCHAR(255),
    trang_thai BIT
);


CREATE TABLE hinh_anh(
	id BIGINT PRIMARY KEY IDENTITY(1, 1),
	hinh_anh NVARCHAR(255) null,
)
CREATE TABLE san_pham_chi_tiet (
    id BIGINT PRIMARY KEY IDENTITY(1, 1),
    id_mau_sac BIGINT FOREIGN KEY REFERENCES mau_sac(id),
    id_san_pham BIGINT FOREIGN KEY REFERENCES san_pham(id),
    id_nut_bam BIGINT FOREIGN KEY REFERENCES nut_bam(id),
    id_cong_suat BIGINT FOREIGN KEY REFERENCES cong_suat(id),
    id_hang BIGINT FOREIGN KEY REFERENCES hang(id),
	id_hinh_anh BIGINT FOREIGN KEY REFERENCES hinh_anh(id),
	gia DECIMAL(18, 0),
	so_luong INT,
	can_nang FLOAT,
	mo_ta NVARCHAR(MAX),
	ngay_tao DATETIME,
	ngay_sua DATETIME,
	nguoi_tao NVARCHAR(255),
    trang_thai BIT,
);


CREATE TABLE khach_hang (
    id BIGINT PRIMARY KEY IDENTITY(1, 1),
	id_tai_khoan INT NULL FOREIGN KEY REFERENCES tai_khoan(id),
    ma NVARCHAR(255),
    ten NVARCHAR(255),
    gioi_tinh NVARCHAR(50),
    so_dien_thoai NVARCHAR(50),
	ngay_sinh DATETIME,
	ngay_tao DATETIME,
    trang_thai BIT,
	hinh_anh NVARCHAR(255),
);

CREATE TABLE dia_chi (
    id BIGINT PRIMARY KEY IDENTITY(1, 1),
    id_khach_hang BIGINT FOREIGN KEY REFERENCES khach_hang(id),
    xa  NVARCHAR(255) ,
	huyen  NVARCHAR(255),
	tinh  NVARCHAR(255),
	so_nha_ngo_duong NVARCHAR(255)
);


CREATE TABLE nhan_vien (
    id BIGINT PRIMARY KEY IDENTITY(1, 1) not null,
    id_chuc_vu BIGINT FOREIGN KEY REFERENCES chuc_vu(id) not null,
	id_tai_khoan INT NULL FOREIGN KEY REFERENCES tai_khoan(id),
	id_dia_chi BIGINT FOREIGN KEY REFERENCES dia_chi(id),
    ma NVARCHAR(255) ,
    ten NVARCHAR(255) ,
    gioi_tinh BIT ,
	ngay_sinh DATETIME ,
    so_dien_thoai NVARCHAR(50) ,
	can_cuoc_cong_dan NVARCHAR(255) ,
	ngay_tao DATETIME ,
    ngay_sua DATETIME ,
    trang_thai BIT ,
	hinh_anh NVARCHAR(255) ,
);

CREATE TABLE phieu_giam_gia (
    id BIGINT PRIMARY KEY IDENTITY(1, 1),
    ma NVARCHAR(255),
    ten NVARCHAR(255),
    ngay_bat_dau DATETIME,
    ngay_ket_thuc DATETIME,
    so_luong INT,
    so_luong_da_su_dung INT DEFAULT 0, 
    loai_giam_gia BIT, 
    gia_tri_giam DECIMAL(18, 0),
    gia_tri_don_hang_toi_thieu DECIMAL(18, 0), 
	gia_tri_giam_toi_da DECIMAL(18, 2) NULL,
    loai_phieu BIT, 
    ngay_tao DATETIME,
    nguoi_tao NVARCHAR(255),
    trang_thai BIT
);

CREATE TABLE hoa_don (
    id BIGINT PRIMARY KEY IDENTITY(1, 1),
    id_khach_hang BIGINT FOREIGN KEY REFERENCES khach_hang(id),
    id_nhan_vien BIGINT FOREIGN KEY REFERENCES nhan_vien(id),
    id_phieu_giam_gia BIGINT FOREIGN KEY REFERENCES phieu_giam_gia(id),
	ma NVARCHAR(255),
	ten_nguoi_nhan NVARCHAR(255),
	sdt_nguoi_nhan NVARCHAR(50),
    tong_tien  DECIMAL(18, 0),
	tong_tien_sau_giam_gia DECIMAL(18, 0),
	phi_van_chuyen DECIMAL(18, 0) ,
	ghi_chu NVARCHAR(255),
	loai_hoa_don BIT,
	[phuong_thuc_thanh_toan] [nvarchar](50) NULL,
	xa  NVARCHAR(255) ,
	huyen  NVARCHAR(255),
	tinh  NVARCHAR(255),
	so_nha_ngo_duong NVARCHAR(255),
    ngay_tao DATETIME,
    ngay_sua DATETIME,
	nguoi_tao NVARCHAR(255),
    trang_thai INT
);


CREATE TABLE [dbo].[Momo_transaction](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	id_hoa_don BIGINT FOREIGN KEY REFERENCES hoa_don(id),
	[partner_code] [nvarchar](50) NULL,
	[order_id] [nvarchar](50) NULL,
	[amount] [decimal](18, 0) NULL,
	[order_info] [nvarchar](255) NULL,
	[order_type] [nvarchar](50) NULL,
	[trans_id] [bigint] NULL,
	[response_time] [bigint] NULL,
	[message] [nvarchar](255) NULL,
	[pay_type] [nvarchar](50) NULL,
	[signature] [nvarchar](max) NULL,
	[ngay_tao] [datetime] NULL,
	[trang_thai] [int] NULL,
	[extra_data] [nvarchar](255) NULL,
	[request_id] [nvarchar](50) NULL,
	[result_code] [int] NULL,
	[redirect_url] [nvarchar](255) NULL,
	[ipn_url] [nvarchar](255) NULL,
	[request_type] [nvarchar](50) NULL,
	[pay_url] [nvarchar](255) NULL,
);

CREATE TABLE hoa_don_chi_tiet (
    id BIGINT PRIMARY KEY IDENTITY(1, 1),
    id_hoa_don BIGINT FOREIGN KEY REFERENCES hoa_don(id),
    id_san_pham_chi_tiet BIGINT FOREIGN KEY REFERENCES san_pham_chi_tiet(id),
    so_luong INT,
    gia_ban DECIMAL(18, 0),
	gia_sau_giam DECIMAL(18, 0),
	thanh_tien DECIMAL(18, 0),
    trang_thai INT
);
CREATE TABLE lich_su_hoa_don(
	id BIGINT PRIMARY KEY IDENTITY(1, 1),
	id_hoa_don BIGINT FOREIGN KEY REFERENCES hoa_don(id),
	id_nhan_vien BIGINT FOREIGN KEY REFERENCES nhan_vien(id),
    trang_thai INT,
	ngay_tao DATETIME,
	ngay_sua DATETIME,
	nguoi_tao NVARCHAR(255),
	mo_ta NVARCHAR(255),
);

INSERT INTO chuc_vu (vi_tri, mo_ta) VALUES 
(N'Admin', N'Quản trị viên hệ thống'),
(N'Employe', N'Nhân viên thực hiện công việc'),
(N'User', N'Người dùng thông thường');

INSERT INTO tai_khoan (id_chuc_vu, ma, email, mat_khau, reset_token, ngay_tao, trang_thai) VALUES
(1, N'TK001', N'admin2@gmail.com', N'{noop}123456', NULL, GETDATE(), 1),
(3, N'TK002', N'khachhang2@gmail.com', N'{noop}123456', NULL, GETDATE(), 1),
(3, N'TK003', N'user@example.com', N'{noop}1', NULL, GETDATE(), 1)
             
-- Dữ liệu cho bảng kieu_quat
INSERT INTO kieu_quat (ten, trang_thai) VALUES 
(N'Quạt trần', 1),
(N'Quạt đứng', 1),
(N'Quạt để bàn', 1),
(N'Quạt treo tường', 1),
(N'Quạt hút gió', 1);

-- Dữ liệu cho bảng san_pham
INSERT INTO san_pham (id_kieu_quat, ma, ten, mo_ta, ngay_tao, trang_thai) VALUES 
(1, N'SP001', N'Quạt trần Panasonic F-60TAN', N'Quạt trần cao cấp, tiết kiệm điện, vận hành êm ái', GETDATE(), 1),
(2, N'SP002', N'Quạt đứng Mitsubishi LV16-RU4', N'Quạt đứng 3 cánh, điều khiển từ xa, timer 7.5 giờ', GETDATE(), 1),
(3, N'SP003', N'Quạt để bàn Toshiba F-LSA10', N'Quạt để bàn mini, phù hợp văn phòng, gia đình', GETDATE(), 1),
(4, N'SP004', N'Quạt treo tường Asia A16005-CV', N'Quạt treo tường 3 tốc độ, điều khiển cơ', GETDATE(), 1),
(5, N'SP005', N'Quạt hút gió Daikio DK-20A', N'Quạt hút gió công nghiệp, lưu lượng gió lớn', GETDATE(), 1);

-- Dữ liệu cho bảng nut_bam
INSERT INTO nut_bam (ten, trang_thai) VALUES 
(N'Nút cơ', 1),
(N'Nút điện tử', 1),
(N'Điều khiển từ xa', 1),
(N'Cảm ứng', 1),
(N'Không có nút bấm', 1);

-- Dữ liệu cho bảng cong_suat
INSERT INTO cong_suat (ten, trang_thai) VALUES 
(N'40W', 1),
(N'60W', 1),
(N'75W', 1),
(N'100W', 1),
(N'150W', 1);

-- Dữ liệu cho bảng mau_sac
INSERT INTO mau_sac (ten, trang_thai) VALUES 
(N'Trắng', 1),
(N'Đen', 1),
(N'Xanh', 1),
(N'Nâu gỗ', 1),
(N'Xám bạc', 1);

-- Dữ liệu cho bảng hang
INSERT INTO hang (ten, trang_thai) VALUES 
(N'Panasonic', 1),
(N'Mitsubishi', 1),
(N'Toshiba', 1),
(N'Asia', 1),
(N'Daikio', 1);

-- Dữ liệu cho bảng hinh_anh
INSERT INTO hinh_anh (hinh_anh) VALUES 
(N'/uploads/quat-tran-panasonic.jpg'),
(N'/uploads/quat-dung-mitsubishi.jpg'),
(N'/uploads/quat-ban-toshiba.jpg'),
(N'/uploads/quat-treo-asia.jpg'),
(N'/uploads/quat-hut-daikio.jpg');

-- Dữ liệu cho bảng san_pham_chi_tiet
INSERT INTO san_pham_chi_tiet (id_mau_sac, id_san_pham, id_nut_bam, id_cong_suat, id_hang, id_hinh_anh, gia, so_luong, can_nang, mo_ta, ngay_tao, ngay_sua, nguoi_tao, trang_thai) VALUES 
(1, 1, 3, 2, 1, 1, 1500000, 50, 3.5, N'Quạt trần Panasonic màu trắng 60W', GETDATE(), GETDATE(), N'Admin', 1),
(2, 2, 3, 2, 2, 2, 1200000, 30, 4.2, N'Quạt đứng Mitsubishi màu đen 60W', GETDATE(), GETDATE(), N'Admin', 1),
(1, 3, 2, 1, 3, 3, 800000, 40, 2.1, N'Quạt để bàn Toshiba màu trắng 40W', GETDATE(), GETDATE(), N'Admin', 1),
(4, 4, 1, 3, 4, 4, 600000, 25, 2.8, N'Quạt treo tường Asia màu nâu gỗ 75W', GETDATE(), GETDATE(), N'Admin', 1),
(5, 5, 5, 5, 5, 5, 2000000, 15, 8.5, N'Quạt hút gió Daikio màu xám bạc 150W', GETDATE(), GETDATE(), N'Admin', 1);

-- Dữ liệu cho bảng khach_hang
INSERT INTO khach_hang (id_tai_khoan, ma, ten, gioi_tinh, so_dien_thoai, ngay_sinh, ngay_tao, trang_thai, hinh_anh) VALUES 
(3, N'KH001', N'Nguyễn Văn An', N'Nam', N'0987654321', '1990-05-15', GETDATE(), 1, N'/uploads/avatar1.jpg'),
(NULL, N'KH002', N'Trần Thị Bình', N'Nữ', N'0976543210', '1985-08-20', GETDATE(), 1, N'/uploads/avatar2.jpg'),
(NULL, N'KH003', N'Lê Hoàng Cường', N'Nam', N'0965432109', '1995-12-10', GETDATE(), 1, N'/uploads/avatar3.jpg'),
(NULL, N'KH004', N'Phạm Thị Dung', N'Nữ', N'0954321098', '1988-03-25', GETDATE(), 1, N'/uploads/avatar4.jpg'),
(NULL, N'KH005', N'Hoàng Văn Em', N'Nam', N'0943210987', '1992-07-08', GETDATE(), 1, N'/uploads/avatar5.jpg');

-- Dữ liệu cho bảng dia_chi
INSERT INTO dia_chi (id_khach_hang, xa, huyen, tinh, so_nha_ngo_duong) VALUES 
(1, N'Phường Láng Thượng', N'Quận Đống Đa', N'Hà Nội', N'123 Ngõ 456 Đường Láng'),
(2, N'Phường Bến Nghé', N'Quận 1', N'TP. Hồ Chí Minh', N'789 Đường Nguyễn Huệ'),
(3, N'Phường Hải Châu 1', N'Quận Hải Châu', N'Đà Nẵng', N'456 Đường Trần Phú'),
(4, N'Phường Trường Thi', N'TP. Huế', N'Thừa Thiên Huế', N'321 Đường Lê Lợi'),
(5, N'Phường Tân An', N'TP. Cần Thơ', N'Cần Thơ', N'654 Đường 30 Tháng 4');

-- Dữ liệu cho bảng nhan_vien
INSERT INTO nhan_vien (id_chuc_vu, id_tai_khoan, id_dia_chi, ma, ten, gioi_tinh, ngay_sinh, so_dien_thoai, can_cuoc_cong_dan, ngay_tao, ngay_sua, trang_thai, hinh_anh) VALUES 
(1, 1, 1, N'NV001', N'Nguyễn Quản Trị', 1, '1985-01-15', N'0912345678', N'123456789012', GETDATE(), GETDATE(), 1, N'/uploads/nv1.jpg'),
(2, 2, 2, N'NV002', N'Trần Nhân Viên', 0, '1990-06-20', N'0923456789', N'234567890123', GETDATE(), GETDATE(), 1, N'/uploads/nv2.jpg'),
(2, NULL, 3, N'NV003', N'Lê Bán Hàng', 1, '1995-03-10', N'0934567890', N'345678901234', GETDATE(), GETDATE(), 1, N'/uploads/nv3.jpg'),
(2, NULL, 4, N'NV004', N'Phạm Kho Hàng', 0, '1988-09-25', N'0945678901', N'456789012345', GETDATE(), GETDATE(), 1, N'/uploads/nv4.jpg'),
(2, NULL, 5, N'NV005', N'Hoàng Kế Toán', 1, '1992-12-05', N'0956789012', N'567890123456', GETDATE(), GETDATE(), 1, N'/uploads/nv5.jpg');

-- Dữ liệu cho bảng phieu_giam_gia
INSERT INTO phieu_giam_gia (ma, ten, ngay_bat_dau, ngay_ket_thuc, so_luong, so_luong_da_su_dung, loai_giam_gia, gia_tri_giam, gia_tri_don_hang_toi_thieu, gia_tri_giam_toi_da, loai_phieu, ngay_tao, nguoi_tao, trang_thai) VALUES 
(N'PGG001', N'Giảm giá mùa hè', '2024-06-01', '2024-08-31', 100, 25, 1, 10, 500000, 100000, 1, GETDATE(), N'Admin', 1),
(N'PGG002', N'Ưu đãi khách hàng VIP', '2024-01-01', '2024-12-31', 50, 10, 0, 200000, 1000000, NULL, 0, GETDATE(), N'Admin', 1),
(N'PGG003', N'Khuyến mãi cuối năm', '2024-12-01', '2024-12-31', 200, 45, 1, 15, 300000, 150000, 1, GETDATE(), N'Admin', 1),
(N'PGG004', N'Giảm giá sinh nhật', '2024-03-01', '2024-03-31', 30, 8, 0, 100000, 800000, NULL, 0, GETDATE(), N'Admin', 1),
(N'PGG005', N'Flash Sale 24h', '2024-07-15', '2024-07-16', 500, 350, 1, 20, 200000, 200000, 1, GETDATE(), N'Admin', 0);

-- Dữ liệu cho bảng hoa_don
INSERT INTO hoa_don (id_khach_hang, id_nhan_vien, id_phieu_giam_gia, ma, ten_nguoi_nhan, sdt_nguoi_nhan, tong_tien, tong_tien_sau_giam_gia, phi_van_chuyen, ghi_chu, loai_hoa_don, phuong_thuc_thanh_toan, xa, huyen, tinh, so_nha_ngo_duong, ngay_tao, ngay_sua, nguoi_tao, trang_thai) VALUES 
(1, 2, 1, N'HD001', N'Nguyễn Văn An', N'0987654321', 1500000, 1350000, 50000, N'Giao hàng giờ hành chính', 0, N'MOMO', N'Phường Láng Thượng', N'Quận Đống Đa', N'Hà Nội', N'123 Ngõ 456 Đường Láng', GETDATE(), GETDATE(), N'NV002', 3),
(2, 3, NULL, N'HD002', N'Trần Thị Bình', N'0976543210', 1200000, 1200000, 30000, N'Giao hàng nhanh', 0, N'COD', N'Phường Bến Nghé', N'Quận 1', N'TP. Hồ Chí Minh', N'789 Đường Nguyễn Huệ', GETDATE(), GETDATE(), N'NV003', 2),
(3, 2, 2, N'HD003', N'Lê Hoàng Cường', N'0965432109', 800000, 600000, 25000, N'Gọi trước khi giao', 0, N'BANK_TRANSFER', N'Phường Hải Châu 1', N'Quận Hải Châu', N'Đà Nẵng', N'456 Đường Trần Phú', GETDATE(), GETDATE(), N'NV002', 1),
(NULL, 4, NULL, N'HD004', N'Khách lẻ', N'', 600000, 600000, 0, N'Mua tại cửa hàng', 1, N'CASH', N'', N'', N'', N'', GETDATE(), GETDATE(), N'NV004', 3),
(4, 3, 3, N'HD005', N'Phạm Thị Dung', N'0954321098', 2000000, 1700000, 40000, N'Hàng dễ vỡ', 0, N'MOMO', N'Phường Trường Thi', N'TP. Huế', N'Thừa Thiên Huế', N'321 Đường Lê Lợi', GETDATE(), GETDATE(), N'NV003', 0);

-- Dữ liệu cho bảng Momo_transaction
INSERT INTO Momo_transaction (id_hoa_don, partner_code, order_id, amount, order_info, order_type, trans_id, response_time, message, pay_type, signature, ngay_tao, trang_thai, extra_data, request_id, result_code, redirect_url, ipn_url, request_type, pay_url) VALUES 
(1, N'MOMO123', N'HD001_240101', 1400000, N'Thanh toán hóa đơn HD001', N'momo_wallet', 2024010112345678, 1640995200, N'Thành công', N'qr', N'abcd1234efgh5678', GETDATE(), 0, N'', N'REQ_240101_001', 0, N'https://payment.momo.vn/result', N'https://shop.com/ipn', N'captureWallet', N'https://payment.momo.vn/pay'),
(5, N'MOMO123', N'HD005_240102', 1740000, N'Thanh toán hóa đơn HD005', N'momo_wallet', 2024010212345679, 1640995300, N'Thành công', N'qr', N'efgh5678ijkl9012', GETDATE(), 0, N'', N'REQ_240102_001', 0, N'https://payment.momo.vn/result', N'https://shop.com/ipn', N'captureWallet', N'https://payment.momo.vn/pay'),
(NULL, N'MOMO123', N'HD006_240103', 500000, N'Giao dịch thất bại', N'momo_wallet', 0, 1640995400, N'Thất bại', N'qr', N'ijkl9012mnop3456', GETDATE(), 1, N'', N'REQ_240103_001', 1, N'https://payment.momo.vn/result', N'https://shop.com/ipn', N'captureWallet', N''),
(NULL, N'MOMO123', N'HD007_240104', 750000, N'Giao dịch đang xử lý', N'momo_wallet', 2024010412345680, 1640995500, N'Đang xử lý', N'qr', N'mnop3456qrst7890', GETDATE(), 2, N'', N'REQ_240104_001', 2, N'https://payment.momo.vn/result', N'https://shop.com/ipn', N'captureWallet', N'https://payment.momo.vn/pay'),
(NULL, N'MOMO123', N'HD008_240105', 300000, N'Giao dịch hủy', N'momo_wallet', 0, 1640995600, N'Đã hủy', N'qr', N'qrst7890uvwx1234', GETDATE(), 3, N'', N'REQ_240105_001', 99, N'https://payment.momo.vn/result', N'https://shop.com/ipn', N'captureWallet', N'');

-- Dữ liệu cho bảng hoa_don_chi_tiet
INSERT INTO hoa_don_chi_tiet (id_hoa_don, id_san_pham_chi_tiet, so_luong, gia_ban, gia_sau_giam, thanh_tien, trang_thai) VALUES 
(1, 1, 1, 1500000, 1350000, 1350000, 1),
(2, 2, 1, 1200000, 1200000, 1200000, 1),
(3, 3, 1, 800000, 600000, 600000, 1),
(4, 4, 1, 600000, 600000, 600000, 1),
(5, 5, 1, 2000000, 1700000, 1700000, 1);

-- Dữ liệu cho bảng lich_su_hoa_don
INSERT INTO lich_su_hoa_don (id_hoa_don, id_nhan_vien, trang_thai, ngay_tao, ngay_sua, nguoi_tao, mo_ta) VALUES 
(1, 2, 0, GETDATE()-3, GETDATE()-3, N'NV002', N'Tạo đơn hàng mới'),
(1, 2, 1, GETDATE()-2, GETDATE()-2, N'NV002', N'Xác nhận đơn hàng'),
(1, 2, 2, GETDATE()-1, GETDATE()-1, N'NV002', N'Đang giao hàng'),
(1, 2, 3, GETDATE(), GETDATE(), N'NV002', N'Giao hàng thành công'),
(2, 3, 0, GETDATE()-2, GETDATE()-2, N'NV003', N'Tạo đơn hàng mới'),
(2, 3, 1, GETDATE()-1, GETDATE()-1, N'NV003', N'Xác nhận đơn hàng'),
(2, 3, 2, GETDATE(), GETDATE(), N'NV003', N'Đang giao hàng'),
(3, 2, 0, GETDATE()-1, GETDATE()-1, N'NV002', N'Tạo đơn hàng mới'),
(3, 2, 1, GETDATE(), GETDATE(), N'NV002', N'Xác nhận đơn hàng'),
(4, 4, 0, GETDATE(), GETDATE(), N'NV004', N'Tạo đơn hàng tại quầy'),
(4, 4, 3, GETDATE(), GETDATE(), N'NV004', N'Hoàn thành bán hàng'),
(5, 3, 0, GETDATE(), GETDATE(), N'NV003', N'Tạo đơn hàng mới');

-- Kiểm tra dữ liệu đã insert
SELECT 'chuc_vu' as bang, COUNT(*) as so_luong FROM chuc_vu
UNION ALL
SELECT 'tai_khoan', COUNT(*) FROM tai_khoan
UNION ALL
SELECT 'kieu_quat', COUNT(*) FROM kieu_quat
UNION ALL
SELECT 'san_pham', COUNT(*) FROM san_pham
UNION ALL
SELECT 'nut_bam', COUNT(*) FROM nut_bam
UNION ALL
SELECT 'cong_suat', COUNT(*) FROM cong_suat
UNION ALL
SELECT 'mau_sac', COUNT(*) FROM mau_sac
UNION ALL
SELECT 'hang', COUNT(*) FROM hang
UNION ALL
SELECT 'hinh_anh', COUNT(*) FROM hinh_anh
UNION ALL
SELECT 'san_pham_chi_tiet', COUNT(*) FROM san_pham_chi_tiet
UNION ALL
SELECT 'khach_hang', COUNT(*) FROM khach_hang
UNION ALL
SELECT 'dia_chi', COUNT(*) FROM dia_chi
UNION ALL
SELECT 'nhan_vien', COUNT(*) FROM nhan_vien
UNION ALL
SELECT 'phieu_giam_gia', COUNT(*) FROM phieu_giam_gia
UNION ALL
SELECT 'hoa_don', COUNT(*) FROM hoa_don
UNION ALL
SELECT 'Momo_transaction', COUNT(*) FROM Momo_transaction
UNION ALL
SELECT 'hoa_don_chi_tiet', COUNT(*) FROM hoa_don_chi_tiet
UNION ALL
SELECT 'lich_su_hoa_don', COUNT(*) FROM lich_su_hoa_don;


select * from khach_hang
select * from dia_chi
select * from tai_khoan
select * from chuc_vu
select * from hoa_don
select * from lich_su_hoa_don
select * from san_pham
select * from san_pham_chi_tiet
select * from hinh_anh