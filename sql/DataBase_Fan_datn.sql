
-- Kiểm tra xem database đã tồn tại chưa
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'datn')
BEGIN
    CREATE DATABASE datn;
END
GO

USE datn;
GO

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
    trang_thai BIT
);

CREATE TABLE dia_chi (
    id BIGINT PRIMARY KEY IDENTITY(1, 1) not null,
    xa  NVARCHAR(255) ,
	huyen  NVARCHAR(255),
	tinh  NVARCHAR(255),
	so_nha_ngo_duong NVARCHAR(255)
);

CREATE TABLE khach_hang (
    id BIGINT PRIMARY KEY IDENTITY(1, 1),
	id_tai_khoan INT NULL FOREIGN KEY REFERENCES tai_khoan(id),
	id_dia_chi BIGINT FOREIGN KEY REFERENCES dia_chi(id),
    ma NVARCHAR(255),
    ten NVARCHAR(255),
    gioi_tinh NVARCHAR(50),
    so_dien_thoai NVARCHAR(50),
	ngay_sinh DATETIME,
	ngay_tao DATETIME,
    trang_thai BIT,
	hinh_anh NVARCHAR(255),
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
	loai_giam_gia BIT,
	gia_tri_giam DECIMAL(18, 0),
	ngay_tao DATETIME,
	nguoi_tao NVARCHAR(255),
    trang_thai BIT,
);

CREATE TABLE hoa_don (
    id BIGINT PRIMARY KEY IDENTITY(1, 1),
    id_khach_hang BIGINT FOREIGN KEY REFERENCES khach_hang(id),
    id_nhan_vien BIGINT FOREIGN KEY REFERENCES nhan_vien(id),
    id_phieu_giam_gia BIGINT FOREIGN KEY REFERENCES phieu_giam_gia(id),
	id_dia_chi BIGINT FOREIGN KEY REFERENCES dia_chi(id),
	ma NVARCHAR(255),
	ten_nguoi_nhan NVARCHAR(255),
	sdt_nguoi_nhan NVARCHAR(50),
    tong_tien  DECIMAL(18, 0),
	tong_tien_sau_giam_gia DECIMAL(18, 0),
	phi_van_chuyen DECIMAL(18, 0) ,
	ghi_chu NVARCHAR(255),
	loai_hoa_don BIT,
	hinh_thuc_thanh_toan NVARCHAR(50),
    ngay_tao DATETIME,
    ngay_sua DATETIME,
	nguoi_tao NVARCHAR(255),
    trang_thai INT
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
	nguoi_tao NVARCHAR(255),
	mo_ta NVARCHAR(255),
);

CREATE TABLE Momo_transaction (
    id BIGINT PRIMARY KEY IDENTITY(1, 1),
    id_hoa_don BIGINT FOREIGN KEY REFERENCES hoa_don(id),
    partner_code NVARCHAR(50),
    order_id NVARCHAR(50),
    amount DECIMAL(18, 0),
    order_info NVARCHAR(255),
    order_type NVARCHAR(50),
    trans_id BIGINT,
    response_time BIGINT,
    message NVARCHAR(255),
    pay_type NVARCHAR(50),
    signature NVARCHAR(MAX),
    ngay_tao DATETIME,
    trang_thai INT
);

-- Thêm dữ liệu mẫu cho các bảng
-- 1. Chức vụ
INSERT INTO chuc_vu (vi_tri, mo_ta) VALUES
(N'Admin', N'Quản trị viên hệ thống'),
(N'Nhân viên bán hàng', N'Nhân viên phụ trách bán hàng'),
(N'Nhân viên kho', N'Nhân viên phụ trách kho'),
(N'Quản lý', N'Quản lý cửa hàng');

-- 2. Tài khoản
INSERT INTO tai_khoan (id_chuc_vu, ma, email, mat_khau, ngay_tao, trang_thai) VALUES
(1, 'AD001', 'admin@gmail.com', '123456', GETDATE(), 1),
(2, 'NV001', 'nhanvien1@gmail.com', '123456', GETDATE(), 1),
(3, 'NV002', 'nhanvien2@gmail.com', '123456', GETDATE(), 1),
(4, 'QL001', 'quanly@gmail.com', '123456', GETDATE(), 1);

-- 3. Kiểu quạt
INSERT INTO kieu_quat (ten, trang_thai) VALUES
(N'Quạt trần', 1),
(N'Quạt đứng', 1),
(N'Quạt bàn', 1),
(N'Quạt treo tường', 1),
(N'Quạt hơi nước', 1);

-- 4. Nút bấm
INSERT INTO nut_bam (ten, trang_thai) VALUES
(N'Nút nhấn cơ', 1),
(N'Nút điện tử', 1),
(N'Remote điều khiển', 1),
(N'Cảm ứng', 1);

-- 5. Công suất
INSERT INTO cong_suat (ten, trang_thai) VALUES
('40W', 1),
('45W', 1),
('50W', 1),
('55W', 1),
('60W', 1);

-- 6. Màu sắc
INSERT INTO mau_sac (ten, trang_thai) VALUES
(N'Đen', 1),
(N'Trắng', 1),
(N'Bạc', 1),
(N'Xanh', 1),
(N'Vàng đồng', 1);

-- 7. Hãng
INSERT INTO hang (ten, trang_thai) VALUES
('Senko', 1),
('Asia', 1),
('Panasonic', 1),
('Samsung', 1),
('Xiaomi', 1);

-- 8. Sản phẩm
INSERT INTO san_pham (id_kieu_quat, ma, ten, mo_ta, ngay_tao, trang_thai) VALUES
(1, 'QT001', N'Quạt trần Royal 5 cánh', N'Quạt trần cao cấp 5 cánh', GETDATE(), 1),
(2, 'QD001', N'Quạt đứng Senko', N'Quạt đứng công nghệ Nhật Bản', GETDATE(), 1),
(3, 'QB001', N'Quạt bàn Xiaomi', N'Quạt bàn thông minh', GETDATE(), 1),
(4, 'QTT001', N'Quạt treo Asia', N'Quạt treo tường công nghiệp', GETDATE(), 1);

-- 9. Hình ảnh
INSERT INTO hinh_anh (hinh_anh) VALUES
('quat_tran_1.jpg'),
('quat_dung_1.jpg'),
('quat_ban_1.jpg'),
('quat_treo_1.jpg');

-- 10. Địa chỉ
INSERT INTO dia_chi (xa, huyen, tinh, so_nha_ngo_duong) VALUES
(N'Phường Minh Khai', N'Quận Hai Bà Trưng', N'Hà Nội', N'Số 123 Đường Minh Khai'),
(N'Phường Bến Nghé', N'Quận 1', N'TP.HCM', N'Số 456 Đường Lê Lợi'),
(N'Phường Hải Châu 1', N'Quận Hải Châu', N'Đà Nẵng', N'Số 789 Đường Nguyễn Văn Linh');

-- 11. Sản phẩm chi tiết
INSERT INTO san_pham_chi_tiet (id_mau_sac, id_san_pham, id_nut_bam, id_cong_suat, id_hang, id_hinh_anh, gia, so_luong, can_nang, mo_ta, ngay_tao, ngay_sua, nguoi_tao, trang_thai) VALUES
(1, 1, 1, 1, 1, 1, 2500000, 50, 3.5, N'Quạt trần cao cấp màu đen', GETDATE(), NULL, 'Admin', 1),
(2, 2, 2, 2, 2, 2, 1500000, 30, 2.5, N'Quạt đứng màu trắng', GETDATE(), NULL, 'Admin', 1),
(3, 3, 3, 3, 3, 3, 800000, 40, 1.5, N'Quạt bàn màu bạc', GETDATE(), NULL, 'Admin', 1);

-- 12. Khách hàng
INSERT INTO khach_hang (id_tai_khoan, id_dia_chi, ma, ten, gioi_tinh, so_dien_thoai, ngay_sinh, ngay_tao, trang_thai) VALUES
(NULL, 1, 'KH001', N'Nguyễn Văn A', 'Nam', '0987654321', '1990-01-01', GETDATE(), 1),
(NULL, 2, 'KH002', N'Trần Thị B', N'Nữ', '0987654322', '1992-02-02', GETDATE(), 1),
(NULL, 3, 'KH003', N'Lê Văn C', 'Nam', '0987654323', '1995-03-03', GETDATE(), 1);

-- 13. Nhân viên
INSERT INTO nhan_vien (id_chuc_vu, id_tai_khoan, id_dia_chi, ma, ten, gioi_tinh, ngay_sinh, so_dien_thoai, can_cuoc_cong_dan, ngay_tao, trang_thai) VALUES
(1, 1, 1, 'NV001', N'Phạm Văn Admin', 1, '1988-01-01', '0123456789', '001188123456', GETDATE(), 1),
(2, 2, 2, 'NV002', N'Nguyễn Thị Sale', 0, '1992-02-02', '0123456790', '001192123456', GETDATE(), 1),
(3, 3, 3, 'NV003', N'Trần Văn Stock', 1, '1990-03-03', '0123456791', '001190123456', GETDATE(), 1);

-- 14. Phiếu giảm giá
INSERT INTO phieu_giam_gia (ma, ten, ngay_bat_dau, ngay_ket_thuc, so_luong, loai_giam_gia, gia_tri_giam, ngay_tao, nguoi_tao, trang_thai) VALUES
('PGG001', N'Giảm 10%', '2024-01-01', '2024-12-31', 100, 1, 10, GETDATE(), 'Admin', 1),
('PGG002', N'Giảm 100k', '2024-01-01', '2024-12-31', 50, 0, 100000, GETDATE(), 'Admin', 1);

-- 15. Hóa đơn
INSERT INTO hoa_don (id_khach_hang, id_nhan_vien, id_phieu_giam_gia, id_dia_chi, ma, ten_nguoi_nhan, sdt_nguoi_nhan, tong_tien, tong_tien_sau_giam_gia, phi_van_chuyen, hinh_thuc_thanh_toan, ngay_tao, nguoi_tao, trang_thai) VALUES
(1, 1, 1, 1, 'HD001', N'Nguyễn Văn A', '0987654321', 2500000, 2250000, 30000, N'Momo', GETDATE(), 'Admin', 1),
(2, 2, 2, 2, 'HD002', N'Trần Thị B', '0987654322', 1500000, 1400000, 30000, N'Tiền mặt', GETDATE(), 'Admin', 1);

-- 16. Hóa đơn chi tiết
INSERT INTO hoa_don_chi_tiet (id_hoa_don, id_san_pham_chi_tiet, so_luong, gia_ban, gia_sau_giam, thanh_tien, trang_thai) VALUES
(1, 1, 1, 2500000, 2250000, 2250000, 1),
(2, 2, 1, 1500000, 1400000, 1400000, 1);

-- 17. Lịch sử hóa đơn
INSERT INTO lich_su_hoa_don (id_hoa_don, id_nhan_vien, trang_thai, ngay_tao, nguoi_tao, mo_ta) VALUES
(1, 1, 1, GETDATE(), 'Admin', N'Đơn hàng mới tạo'),
(1, 1, 2, GETDATE(), 'Admin', N'Đã xác nhận đơn hàng'),
(2, 2, 1, GETDATE(), 'Admin', N'Đơn hàng mới tạo');

-- 18. Momo transaction
INSERT INTO Momo_transaction (id_hoa_don, partner_code, order_id, amount, order_info, order_type, trans_id, response_time, message, pay_type, signature, ngay_tao, trang_thai) VALUES
(1, 'MOMOXXX', 'HD001', 2280000, N'Thanh toán đơn hàng HD001', 'momo_wallet', 12345678, 1679825718, N'Giao dịch thành công', 'app', 'xxx', GETDATE(), 1);

