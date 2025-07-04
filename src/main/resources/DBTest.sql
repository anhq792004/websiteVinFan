USE [master]
GO
/****** Object:  Database [datn]    Script Date: 5/29/2025 6:58:06 AM ******/
CREATE DATABASE [datn]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'datn', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\datn.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'datn_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\datn_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [datn] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [datn].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [datn] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [datn] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [datn] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [datn] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [datn] SET ARITHABORT OFF 
GO
ALTER DATABASE [datn] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [datn] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [datn] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [datn] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [datn] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [datn] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [datn] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [datn] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [datn] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [datn] SET  ENABLE_BROKER 
GO
ALTER DATABASE [datn] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [datn] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [datn] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [datn] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [datn] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [datn] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [datn] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [datn] SET RECOVERY FULL 
GO
ALTER DATABASE [datn] SET  MULTI_USER 
GO
ALTER DATABASE [datn] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [datn] SET DB_CHAINING OFF 
GO
ALTER DATABASE [datn] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [datn] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [datn] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [datn] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'datn', N'ON'
GO
ALTER DATABASE [datn] SET QUERY_STORE = ON
GO
ALTER DATABASE [datn] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [datn]
GO
/****** Object:  Table [dbo].[chuc_vu]    Script Date: 5/29/2025 6:58:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[chuc_vu](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[vi_tri] [nvarchar](255) NULL,
	[mo_ta] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[cong_suat]    Script Date: 5/29/2025 6:58:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[cong_suat](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[ten] [nvarchar](255) NULL,
	[trang_thai] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[dia_chi]    Script Date: 5/29/2025 6:58:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[dia_chi](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[xa] [nvarchar](255) NULL,
	[huyen] [nvarchar](255) NULL,
	[tinh] [nvarchar](255) NULL,
	[so_nha_ngo_duong] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[hang]    Script Date: 5/29/2025 6:58:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[hang](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[ten] [nvarchar](255) NULL,
	[trang_thai] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[hinh_anh]    Script Date: 5/29/2025 6:58:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[hinh_anh](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[hinh_anh] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[hoa_don]    Script Date: 5/29/2025 6:58:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[hoa_don](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_khach_hang] [bigint] NULL,
	[id_nhan_vien] [bigint] NULL,
	[id_phieu_giam_gia] [bigint] NULL,
	[id_dia_chi] [bigint] NULL,
	[ma] [nvarchar](255) NULL,
	[ten_nguoi_nhan] [nvarchar](255) NULL,
	[sdt_nguoi_nhan] [varchar](255) NULL,
	[tong_tien] [numeric](38, 2) NULL,
	[tong_tien_sau_giam_gia] [numeric](38, 2) NULL,
	[phi_van_chuyen] [numeric](38, 2) NULL,
	[ghi_chu] [nvarchar](255) NULL,
	[loai_hoa_don] [bit] NULL,
	[hinh_thuc_thanh_toan] [varchar](255) NULL,
	[ngay_tao] [datetime] NULL,
	[ngay_sua] [datetime] NULL,
	[nguoi_tao] [nvarchar](255) NULL,
	[trang_thai] [int] NULL,
	[tien_khach_tra] [numeric](38, 2) NULL,
	[tien_thua] [numeric](38, 2) NULL,
	[phuong_thuc_thanh_toan] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[hoa_don_chi_tiet]    Script Date: 5/29/2025 6:58:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[hoa_don_chi_tiet](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_hoa_don] [bigint] NULL,
	[id_san_pham_chi_tiet] [bigint] NULL,
	[so_luong] [int] NULL,
	[gia_ban] [numeric](38, 2) NULL,
	[gia_sau_giam] [numeric](38, 2) NULL,
	[thanh_tien] [numeric](38, 2) NULL,
	[trang_thai] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[khach_hang]    Script Date: 5/29/2025 6:58:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[khach_hang](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_tai_khoan] [int] NULL,
	[id_dia_chi] [bigint] NULL,
	[ma] [nvarchar](255) NULL,
	[ten] [nvarchar](255) NULL,
	[gioi_tinh] [varchar](255) NULL,
	[so_dien_thoai] [varchar](255) NULL,
	[ngay_sinh] [datetime] NULL,
	[ngay_tao] [datetime] NULL,
	[trang_thai] [bit] NULL,
	[hinh_anh] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[kieu_quat]    Script Date: 5/29/2025 6:58:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[kieu_quat](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[ten] [nvarchar](255) NULL,
	[trang_thai] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[lich_su_hoa_don]    Script Date: 5/29/2025 6:58:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[lich_su_hoa_don](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_hoa_don] [bigint] NULL,
	[id_nhan_vien] [bigint] NULL,
	[trang_thai] [int] NULL,
	[ngay_tao] [datetime] NULL,
	[nguoi_tao] [nvarchar](255) NULL,
	[mo_ta] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[mau_sac]    Script Date: 5/29/2025 6:58:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[mau_sac](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[ten] [nvarchar](255) NULL,
	[trang_thai] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Momo_transaction]    Script Date: 5/29/2025 6:58:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Momo_transaction](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_hoa_don] [bigint] NULL,
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
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[nhan_vien]    Script Date: 5/29/2025 6:58:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[nhan_vien](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_chuc_vu] [bigint] NOT NULL,
	[id_tai_khoan] [int] NULL,
	[id_dia_chi] [bigint] NULL,
	[ma] [nvarchar](255) NULL,
	[ten] [nvarchar](255) NULL,
	[gioi_tinh] [bit] NULL,
	[ngay_sinh] [datetime] NULL,
	[so_dien_thoai] [varchar](255) NULL,
	[can_cuoc_cong_dan] [nvarchar](255) NULL,
	[ngay_tao] [datetime] NULL,
	[ngay_sua] [datetime] NULL,
	[trang_thai] [bit] NULL,
	[hinh_anh] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[nut_bam]    Script Date: 5/29/2025 6:58:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[nut_bam](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[ten] [nvarchar](255) NULL,
	[trang_thai] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[phieu_giam_gia]    Script Date: 5/29/2025 6:58:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[phieu_giam_gia](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[ma] [nvarchar](255) NULL,
	[ten] [nvarchar](255) NULL,
	[ngay_bat_dau] [datetime] NULL,
	[ngay_ket_thuc] [datetime] NULL,
	[so_luong] [int] NULL,
	[loai_giam_gia] [bit] NULL,
	[gia_tri_giam] [numeric](38, 2) NULL,
	[ngay_tao] [datetime] NULL,
	[nguoi_tao] [nvarchar](255) NULL,
	[trang_thai] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[san_pham]    Script Date: 5/29/2025 6:58:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[san_pham](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_kieu_quat] [bigint] NULL,
	[ma] [nvarchar](255) NULL,
	[ten] [nvarchar](255) NULL,
	[mo_ta] [text] NULL,
	[ngay_tao] [datetime] NULL,
	[trang_thai] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[san_pham_chi_tiet]    Script Date: 5/29/2025 6:58:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[san_pham_chi_tiet](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[id_mau_sac] [bigint] NULL,
	[id_san_pham] [bigint] NULL,
	[id_nut_bam] [bigint] NULL,
	[id_cong_suat] [bigint] NULL,
	[id_hang] [bigint] NULL,
	[id_hinh_anh] [bigint] NULL,
	[gia] [numeric](38, 2) NULL,
	[so_luong] [int] NULL,
	[can_nang] [float] NULL,
	[mo_ta] [text] NULL,
	[ngay_tao] [datetime] NULL,
	[ngay_sua] [datetime] NULL,
	[nguoi_tao] [nvarchar](255) NULL,
	[trang_thai] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tai_khoan]    Script Date: 5/29/2025 6:58:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tai_khoan](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_chuc_vu] [bigint] NULL,
	[ma] [nvarchar](255) NULL,
	[email] [nvarchar](255) NULL,
	[mat_khau] [nvarchar](255) NULL,
	[reset_token] [nvarchar](255) NULL,
	[ngay_tao] [varchar](255) NULL,
	[trang_thai] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[chuc_vu] ON 

INSERT [dbo].[chuc_vu] ([id], [vi_tri], [mo_ta]) VALUES (1, N'Admin', N'Quản trị viên hệ thống')
INSERT [dbo].[chuc_vu] ([id], [vi_tri], [mo_ta]) VALUES (2, N'Nhân viên bán hàng', N'Nhân viên phụ trách bán hàng')
INSERT [dbo].[chuc_vu] ([id], [vi_tri], [mo_ta]) VALUES (3, N'Nhân viên kho', N'Nhân viên phụ trách kho')
INSERT [dbo].[chuc_vu] ([id], [vi_tri], [mo_ta]) VALUES (4, N'Quản lý', N'Quản lý cửa hàng')
SET IDENTITY_INSERT [dbo].[chuc_vu] OFF
GO
SET IDENTITY_INSERT [dbo].[cong_suat] ON 

INSERT [dbo].[cong_suat] ([id], [ten], [trang_thai]) VALUES (1, N'40W', 1)
INSERT [dbo].[cong_suat] ([id], [ten], [trang_thai]) VALUES (2, N'45W', 1)
INSERT [dbo].[cong_suat] ([id], [ten], [trang_thai]) VALUES (3, N'50W', 1)
INSERT [dbo].[cong_suat] ([id], [ten], [trang_thai]) VALUES (4, N'55W', 1)
INSERT [dbo].[cong_suat] ([id], [ten], [trang_thai]) VALUES (5, N'60W', 1)
SET IDENTITY_INSERT [dbo].[cong_suat] OFF
GO
SET IDENTITY_INSERT [dbo].[dia_chi] ON 

INSERT [dbo].[dia_chi] ([id], [xa], [huyen], [tinh], [so_nha_ngo_duong]) VALUES (1, N'Phường Minh Khai', N'Quận Hai Bà Trưng', N'Hà Nội', N'Số 123 Đường Minh Khai')
INSERT [dbo].[dia_chi] ([id], [xa], [huyen], [tinh], [so_nha_ngo_duong]) VALUES (2, N'Phường Bến Nghé', N'Quận 1', N'TP.HCM', N'Số 456 Đường Lê Lợi')
INSERT [dbo].[dia_chi] ([id], [xa], [huyen], [tinh], [so_nha_ngo_duong]) VALUES (3, N'Phường Hải Châu 1', N'Quận Hải Châu', N'Đà Nẵng', N'Số 789 Đường Nguyễn Văn Linh')
SET IDENTITY_INSERT [dbo].[dia_chi] OFF
GO
SET IDENTITY_INSERT [dbo].[hang] ON 

INSERT [dbo].[hang] ([id], [ten], [trang_thai]) VALUES (1, N'Senko', 1)
INSERT [dbo].[hang] ([id], [ten], [trang_thai]) VALUES (2, N'Asia', 1)
INSERT [dbo].[hang] ([id], [ten], [trang_thai]) VALUES (3, N'Panasonic', 1)
INSERT [dbo].[hang] ([id], [ten], [trang_thai]) VALUES (4, N'Samsung', 1)
INSERT [dbo].[hang] ([id], [ten], [trang_thai]) VALUES (5, N'Xiaomi', 1)
SET IDENTITY_INSERT [dbo].[hang] OFF
GO
SET IDENTITY_INSERT [dbo].[hinh_anh] ON 

INSERT [dbo].[hinh_anh] ([id], [hinh_anh]) VALUES (1, N'quat_tran_1.jpg')
INSERT [dbo].[hinh_anh] ([id], [hinh_anh]) VALUES (2, N'quat_dung_1.jpg')
INSERT [dbo].[hinh_anh] ([id], [hinh_anh]) VALUES (3, N'quat_ban_1.jpg')
INSERT [dbo].[hinh_anh] ([id], [hinh_anh]) VALUES (4, N'quat_treo_1.jpg')
SET IDENTITY_INSERT [dbo].[hinh_anh] OFF
GO
SET IDENTITY_INSERT [dbo].[hoa_don] ON 

INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (1, 1, 1, 1, 1, N'HD001', N'Nguyễn Văn A', N'0987654321', CAST(2500000.00 AS Numeric(38, 2)), CAST(2250000.00 AS Numeric(38, 2)), CAST(30000.00 AS Numeric(38, 2)), NULL, NULL, N'Momo', CAST(N'2025-05-29T04:38:57.240' AS DateTime), NULL, N'Admin', 1, NULL, NULL, NULL)
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (2, 2, 2, 2, 2, N'HD002', N'Trần Thị B', N'0987654322', CAST(1500000.00 AS Numeric(38, 2)), CAST(1400000.00 AS Numeric(38, 2)), CAST(30000.00 AS Numeric(38, 2)), NULL, NULL, N'Ti?n m?t', CAST(N'2025-05-29T04:38:57.240' AS DateTime), NULL, N'Admin', 3, NULL, NULL, NULL)
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (3, 1, 1, NULL, NULL, N'HD001', N'Nguy?n Van A', N'0987654321', CAST(2500000.00 AS Numeric(38, 2)), CAST(2250000.00 AS Numeric(38, 2)), CAST(30000.00 AS Numeric(38, 2)), N'Ghi chú HD001', 1, N'Ti?n m?t', CAST(N'2025-05-29T04:38:57.247' AS DateTime), NULL, N'Admin', 1, NULL, NULL, NULL)
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (4, 1, 1, NULL, NULL, N'HD002', N'Nguy?n Van B', N'0987654322', CAST(1800000.00 AS Numeric(38, 2)), CAST(1700000.00 AS Numeric(38, 2)), CAST(30000.00 AS Numeric(38, 2)), N'Ghi chú HD002', 1, N'Chuy?n kho?n', CAST(N'2025-05-29T04:38:57.247' AS DateTime), NULL, N'Admin', 2, NULL, NULL, NULL)
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (5, 2, 2, NULL, NULL, N'HD003', N'Tr?n Th? C', N'0987654323', CAST(3200000.00 AS Numeric(38, 2)), CAST(3000000.00 AS Numeric(38, 2)), CAST(30000.00 AS Numeric(38, 2)), N'Ghi chú HD003', 1, N'Ti?n m?t', CAST(N'2025-05-29T04:38:57.247' AS DateTime), NULL, N'Admin', 3, NULL, NULL, NULL)
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (6, 2, 2, NULL, NULL, N'HD004', N'Tr?n Th? D', N'0987654324', CAST(1500000.00 AS Numeric(38, 2)), CAST(1450000.00 AS Numeric(38, 2)), CAST(30000.00 AS Numeric(38, 2)), N'Ghi chú HD004', 1, N'Momo', CAST(N'2025-05-29T04:38:57.247' AS DateTime), NULL, N'Admin', 4, NULL, NULL, NULL)
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (7, NULL, NULL, NULL, NULL, N'HD007', NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, CAST(N'2025-05-29T04:39:47.080' AS DateTime), NULL, NULL, 5, NULL, NULL, NULL)
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (8, NULL, NULL, NULL, NULL, N'HD008', NULL, NULL, CAST(5000000.00 AS Numeric(38, 2)), NULL, NULL, NULL, 1, NULL, CAST(N'2025-05-29T04:48:40.443' AS DateTime), NULL, NULL, 5, NULL, NULL, NULL)
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (9, NULL, NULL, NULL, NULL, N'HD009', NULL, NULL, CAST(2500000.00 AS Numeric(38, 2)), NULL, NULL, NULL, 1, NULL, CAST(N'2025-05-29T04:50:31.670' AS DateTime), CAST(N'2025-05-29T04:51:27.993' AS DateTime), NULL, 4, NULL, NULL, NULL)
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (10, NULL, NULL, 3, NULL, N'HD010', NULL, NULL, CAST(1500000.00 AS Numeric(38, 2)), CAST(1275000.00 AS Numeric(38, 2)), NULL, NULL, 1, NULL, CAST(N'2025-05-29T04:52:35.767' AS DateTime), CAST(N'2025-05-29T05:00:56.650' AS DateTime), NULL, 4, NULL, NULL, NULL)
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (11, NULL, NULL, NULL, NULL, N'HD011', NULL, NULL, CAST(8000000.00 AS Numeric(38, 2)), NULL, NULL, NULL, 1, NULL, CAST(N'2025-05-29T05:01:09.380' AS DateTime), NULL, NULL, 5, NULL, NULL, NULL)
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (12, 3, NULL, 3, NULL, N'HD012', N'Lê Văn C', N'0987654323', CAST(2500000.00 AS Numeric(38, 2)), CAST(2125000.00 AS Numeric(38, 2)), NULL, NULL, 1, NULL, CAST(N'2025-05-29T05:03:00.683' AS DateTime), CAST(N'2025-05-29T05:05:31.860' AS DateTime), NULL, 4, NULL, NULL, NULL)
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (13, NULL, NULL, 3, NULL, N'HD013', NULL, NULL, CAST(1300000.00 AS Numeric(38, 2)), CAST(1105000.00 AS Numeric(38, 2)), NULL, NULL, 1, NULL, CAST(N'2025-05-29T05:15:21.780' AS DateTime), CAST(N'2025-05-29T05:15:56.187' AS DateTime), NULL, 4, NULL, NULL, NULL)
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (14, NULL, NULL, 3, NULL, N'HD014', NULL, NULL, CAST(4600000.00 AS Numeric(38, 2)), CAST(3910000.00 AS Numeric(38, 2)), NULL, NULL, 1, NULL, CAST(N'2025-05-29T05:16:18.103' AS DateTime), NULL, NULL, 5, NULL, NULL, NULL)
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (15, NULL, NULL, 3, NULL, N'HD015', NULL, NULL, CAST(5000000.00 AS Numeric(38, 2)), CAST(2125000.00 AS Numeric(38, 2)), NULL, NULL, 1, NULL, CAST(N'2025-05-29T05:28:09.250' AS DateTime), CAST(N'2025-05-29T05:35:10.177' AS DateTime), NULL, 4, NULL, NULL, NULL)
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (16, 1, NULL, 3, NULL, N'HD016', N'Nguyễn Văn A', N'0987654321', CAST(4000000.00 AS Numeric(38, 2)), CAST(3400000.00 AS Numeric(38, 2)), NULL, NULL, 1, NULL, CAST(N'2025-05-29T05:35:26.117' AS DateTime), CAST(N'2025-05-29T05:43:51.897' AS DateTime), NULL, 4, NULL, NULL, NULL)
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (17, NULL, NULL, 3, NULL, N'HD017', NULL, NULL, CAST(4000000.00 AS Numeric(38, 2)), CAST(3400000.00 AS Numeric(38, 2)), NULL, NULL, 1, NULL, CAST(N'2025-05-29T05:45:12.983' AS DateTime), CAST(N'2025-05-29T05:58:48.697' AS DateTime), NULL, 4, NULL, NULL, NULL)
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (18, 1, NULL, NULL, NULL, N'HD018', N'Nguyễn Văn A', N'0987654321', CAST(2500000.00 AS Numeric(38, 2)), NULL, NULL, NULL, 1, NULL, CAST(N'2025-05-29T05:59:18.743' AS DateTime), NULL, NULL, 5, NULL, NULL, NULL)
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (19, NULL, NULL, NULL, NULL, N'HD019', NULL, NULL, CAST(2500000.00 AS Numeric(38, 2)), NULL, NULL, NULL, 1, NULL, CAST(N'2025-05-29T06:00:21.557' AS DateTime), CAST(N'2025-05-29T06:10:49.693' AS DateTime), NULL, 4, NULL, NULL, N'TIEN_MAT')
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (20, NULL, NULL, 3, NULL, N'HD020', NULL, NULL, CAST(2500000.00 AS Numeric(38, 2)), CAST(2125000.00 AS Numeric(38, 2)), NULL, NULL, 1, N'Momo', CAST(N'2025-05-29T06:10:52.357' AS DateTime), CAST(N'2025-05-29T06:11:51.383' AS DateTime), NULL, 4, NULL, NULL, N'MOMO')
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (21, NULL, NULL, NULL, NULL, N'HD021', NULL, NULL, CAST(2500000.00 AS Numeric(38, 2)), NULL, NULL, NULL, 1, N'Momo', CAST(N'2025-05-29T06:11:59.210' AS DateTime), CAST(N'2025-05-29T06:14:40.137' AS DateTime), NULL, 4, NULL, NULL, N'MOMO')
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (22, NULL, NULL, NULL, NULL, N'HD022', NULL, NULL, CAST(2500000.00 AS Numeric(38, 2)), NULL, NULL, NULL, 1, N'Momo', CAST(N'2025-05-29T06:20:00.633' AS DateTime), CAST(N'2025-05-29T06:21:51.703' AS DateTime), NULL, 4, NULL, NULL, N'MOMO')
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (23, NULL, NULL, NULL, NULL, N'HD023', NULL, NULL, CAST(800000.00 AS Numeric(38, 2)), NULL, NULL, NULL, 1, N'Momo', CAST(N'2025-05-29T06:22:20.727' AS DateTime), CAST(N'2025-05-29T06:23:18.440' AS DateTime), NULL, 4, NULL, NULL, N'MOMO')
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (24, NULL, NULL, NULL, NULL, N'HD024', NULL, NULL, CAST(800000.00 AS Numeric(38, 2)), NULL, NULL, NULL, 1, N'Momo', CAST(N'2025-05-29T06:23:31.253' AS DateTime), CAST(N'2025-05-29T06:23:56.710' AS DateTime), NULL, 4, NULL, NULL, N'MOMO')
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (25, NULL, NULL, NULL, NULL, N'HD025', NULL, NULL, CAST(2500000.00 AS Numeric(38, 2)), NULL, NULL, NULL, 1, NULL, CAST(N'2025-05-29T06:25:11.740' AS DateTime), CAST(N'2025-05-29T06:30:43.330' AS DateTime), NULL, 4, NULL, NULL, N'TIEN_MAT')
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (26, NULL, NULL, NULL, NULL, N'HD026', NULL, NULL, CAST(2500000.00 AS Numeric(38, 2)), NULL, NULL, NULL, 1, NULL, CAST(N'2025-05-29T06:33:34.777' AS DateTime), NULL, NULL, 5, NULL, NULL, NULL)
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (27, NULL, NULL, NULL, NULL, N'HD027', NULL, NULL, CAST(2500000.00 AS Numeric(38, 2)), NULL, NULL, NULL, 1, NULL, CAST(N'2025-05-29T06:35:23.890' AS DateTime), NULL, NULL, 5, NULL, NULL, NULL)
INSERT [dbo].[hoa_don] ([id], [id_khach_hang], [id_nhan_vien], [id_phieu_giam_gia], [id_dia_chi], [ma], [ten_nguoi_nhan], [sdt_nguoi_nhan], [tong_tien], [tong_tien_sau_giam_gia], [phi_van_chuyen], [ghi_chu], [loai_hoa_don], [hinh_thuc_thanh_toan], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai], [tien_khach_tra], [tien_thua], [phuong_thuc_thanh_toan]) VALUES (28, NULL, NULL, 3, NULL, N'HD028', NULL, NULL, CAST(7500000.00 AS Numeric(38, 2)), CAST(6375000.00 AS Numeric(38, 2)), NULL, NULL, 1, NULL, CAST(N'2025-05-29T06:35:36.450' AS DateTime), NULL, NULL, 0, NULL, NULL, NULL)
SET IDENTITY_INSERT [dbo].[hoa_don] OFF
GO
SET IDENTITY_INSERT [dbo].[hoa_don_chi_tiet] ON 

INSERT [dbo].[hoa_don_chi_tiet] ([id], [id_hoa_don], [id_san_pham_chi_tiet], [so_luong], [gia_ban], [gia_sau_giam], [thanh_tien], [trang_thai]) VALUES (22, 14, 8, 1, CAST(1300000.00 AS Numeric(38, 2)), NULL, CAST(1300000.00 AS Numeric(38, 2)), NULL)
INSERT [dbo].[hoa_don_chi_tiet] ([id], [id_hoa_don], [id_san_pham_chi_tiet], [so_luong], [gia_ban], [gia_sau_giam], [thanh_tien], [trang_thai]) VALUES (37, 16, 2, 1, CAST(1500000.00 AS Numeric(38, 2)), NULL, CAST(1500000.00 AS Numeric(38, 2)), NULL)
INSERT [dbo].[hoa_don_chi_tiet] ([id], [id_hoa_don], [id_san_pham_chi_tiet], [so_luong], [gia_ban], [gia_sau_giam], [thanh_tien], [trang_thai]) VALUES (41, 17, 2, 1, CAST(1500000.00 AS Numeric(38, 2)), NULL, CAST(1500000.00 AS Numeric(38, 2)), NULL)
INSERT [dbo].[hoa_don_chi_tiet] ([id], [id_hoa_don], [id_san_pham_chi_tiet], [so_luong], [gia_ban], [gia_sau_giam], [thanh_tien], [trang_thai]) VALUES (48, 23, 3, 1, CAST(800000.00 AS Numeric(38, 2)), NULL, CAST(800000.00 AS Numeric(38, 2)), NULL)
INSERT [dbo].[hoa_don_chi_tiet] ([id], [id_hoa_don], [id_san_pham_chi_tiet], [so_luong], [gia_ban], [gia_sau_giam], [thanh_tien], [trang_thai]) VALUES (49, 24, 3, 1, CAST(800000.00 AS Numeric(38, 2)), NULL, CAST(800000.00 AS Numeric(38, 2)), NULL)
INSERT [dbo].[hoa_don_chi_tiet] ([id], [id_hoa_don], [id_san_pham_chi_tiet], [so_luong], [gia_ban], [gia_sau_giam], [thanh_tien], [trang_thai]) VALUES (50, 25, 1, 1, CAST(2500000.00 AS Numeric(38, 2)), NULL, CAST(2500000.00 AS Numeric(38, 2)), NULL)
INSERT [dbo].[hoa_don_chi_tiet] ([id], [id_hoa_don], [id_san_pham_chi_tiet], [so_luong], [gia_ban], [gia_sau_giam], [thanh_tien], [trang_thai]) VALUES (51, 26, 1, 1, CAST(2500000.00 AS Numeric(38, 2)), NULL, CAST(2500000.00 AS Numeric(38, 2)), NULL)
INSERT [dbo].[hoa_don_chi_tiet] ([id], [id_hoa_don], [id_san_pham_chi_tiet], [so_luong], [gia_ban], [gia_sau_giam], [thanh_tien], [trang_thai]) VALUES (52, 27, 1, 1, CAST(2500000.00 AS Numeric(38, 2)), NULL, CAST(2500000.00 AS Numeric(38, 2)), NULL)
INSERT [dbo].[hoa_don_chi_tiet] ([id], [id_hoa_don], [id_san_pham_chi_tiet], [so_luong], [gia_ban], [gia_sau_giam], [thanh_tien], [trang_thai]) VALUES (53, 28, 1, 3, CAST(2500000.00 AS Numeric(38, 2)), NULL, CAST(7500000.00 AS Numeric(38, 2)), NULL)
SET IDENTITY_INSERT [dbo].[hoa_don_chi_tiet] OFF
GO
SET IDENTITY_INSERT [dbo].[khach_hang] ON 

INSERT [dbo].[khach_hang] ([id], [id_tai_khoan], [id_dia_chi], [ma], [ten], [gioi_tinh], [so_dien_thoai], [ngay_sinh], [ngay_tao], [trang_thai], [hinh_anh]) VALUES (1, NULL, 1, N'KH001', N'Nguyễn Văn A', N'Nam', N'0987654321', CAST(N'1990-01-01T00:00:00.000' AS DateTime), CAST(N'2025-05-29T04:38:57.233' AS DateTime), 1, NULL)
INSERT [dbo].[khach_hang] ([id], [id_tai_khoan], [id_dia_chi], [ma], [ten], [gioi_tinh], [so_dien_thoai], [ngay_sinh], [ngay_tao], [trang_thai], [hinh_anh]) VALUES (2, NULL, 2, N'KH002', N'Trần Thị B', N'N?', N'0987654322', CAST(N'1992-02-02T00:00:00.000' AS DateTime), CAST(N'2025-05-29T04:38:57.233' AS DateTime), 1, NULL)
INSERT [dbo].[khach_hang] ([id], [id_tai_khoan], [id_dia_chi], [ma], [ten], [gioi_tinh], [so_dien_thoai], [ngay_sinh], [ngay_tao], [trang_thai], [hinh_anh]) VALUES (3, NULL, 3, N'KH003', N'Lê Văn C', N'Nam', N'0987654323', CAST(N'1995-03-03T00:00:00.000' AS DateTime), CAST(N'2025-05-29T04:38:57.233' AS DateTime), 1, NULL)
SET IDENTITY_INSERT [dbo].[khach_hang] OFF
GO
SET IDENTITY_INSERT [dbo].[kieu_quat] ON 

INSERT [dbo].[kieu_quat] ([id], [ten], [trang_thai]) VALUES (1, N'Quạt trần', 1)
INSERT [dbo].[kieu_quat] ([id], [ten], [trang_thai]) VALUES (2, N'Quạt đứng', 1)
INSERT [dbo].[kieu_quat] ([id], [ten], [trang_thai]) VALUES (3, N'Quạt bàn', 1)
INSERT [dbo].[kieu_quat] ([id], [ten], [trang_thai]) VALUES (4, N'Quạt treo tường', 1)
INSERT [dbo].[kieu_quat] ([id], [ten], [trang_thai]) VALUES (5, N'Quạt hơi nước', 1)
SET IDENTITY_INSERT [dbo].[kieu_quat] OFF
GO
SET IDENTITY_INSERT [dbo].[lich_su_hoa_don] ON 

INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (1, 1, 1, 1, CAST(N'2025-05-29T04:38:57.247' AS DateTime), N'Admin', N'Đơn hàng mới tạo')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (2, 1, 1, 2, CAST(N'2025-05-29T04:38:57.247' AS DateTime), N'Admin', N'Đã xác nhận đơn hàng')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (3, 2, 2, 1, CAST(N'2025-05-29T04:38:57.247' AS DateTime), N'Admin', N'Đơn hàng mới tạo')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (4, 1, 1, 1, CAST(N'2025-05-29T04:38:57.250' AS DateTime), N'Admin', N'Ðon hàng m?i t?o')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (5, 2, 1, 2, CAST(N'2025-05-29T04:38:57.250' AS DateTime), N'Admin', N'Ðã xác nh?n don hàng')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (6, 3, 2, 3, CAST(N'2025-05-29T04:38:57.250' AS DateTime), N'Admin', N'Ðang giao hàng')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (7, 4, 2, 4, CAST(N'2025-05-29T04:38:57.250' AS DateTime), N'Admin', N'Ðã hoàn thành')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (8, 7, NULL, 0, CAST(N'2025-05-29T04:39:47.080' AS DateTime), NULL, NULL)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (9, 7, NULL, 5, CAST(N'2025-05-29T04:48:38.583' AS DateTime), NULL, N'Đơn hàng đã được hủy lúc 2025-05-29T04:48:38.582121')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (10, 8, NULL, 0, CAST(N'2025-05-29T04:48:40.443' AS DateTime), NULL, NULL)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (11, 8, NULL, 5, CAST(N'2025-05-29T04:50:19.513' AS DateTime), NULL, N'Đơn hàng đã được hủy lúc 2025-05-29T04:50:19.512631300')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (12, 9, NULL, 0, CAST(N'2025-05-29T04:50:31.670' AS DateTime), NULL, NULL)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (13, 9, NULL, 4, CAST(N'2025-05-29T04:51:27.993' AS DateTime), N'admin', NULL)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (14, 10, NULL, 0, CAST(N'2025-05-29T04:52:35.767' AS DateTime), NULL, NULL)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (15, 10, NULL, 4, CAST(N'2025-05-29T05:00:56.650' AS DateTime), N'admin', NULL)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (16, 11, NULL, 0, CAST(N'2025-05-29T05:01:09.380' AS DateTime), NULL, NULL)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (17, 11, NULL, 5, CAST(N'2025-05-29T05:02:57.960' AS DateTime), NULL, N'Đơn hàng đã được hủy lúc 2025-05-29T05:02:57.961074100')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (18, 12, NULL, 0, CAST(N'2025-05-29T05:03:00.683' AS DateTime), NULL, NULL)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (19, 12, NULL, 4, CAST(N'2025-05-29T05:05:31.860' AS DateTime), N'admin', NULL)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (20, 13, NULL, 0, CAST(N'2025-05-29T05:15:21.780' AS DateTime), NULL, NULL)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (21, 13, NULL, 4, CAST(N'2025-05-29T05:15:56.187' AS DateTime), N'admin', N'Thanh toán thành công và đã cập nhật số lượng trong kho')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (22, 14, NULL, 0, CAST(N'2025-05-29T05:16:18.103' AS DateTime), NULL, NULL)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (23, 14, NULL, 5, CAST(N'2025-05-29T05:28:06.540' AS DateTime), NULL, N'Đơn hàng đã được hủy lúc 2025-05-29T05:28:06.538654500')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (24, 15, NULL, 0, CAST(N'2025-05-29T05:28:09.250' AS DateTime), NULL, NULL)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (25, 15, NULL, 4, CAST(N'2025-05-29T05:35:10.177' AS DateTime), N'admin', N'Thanh toán thành công và đã cập nhật số lượng trong kho')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (26, 16, NULL, 0, CAST(N'2025-05-29T05:35:26.117' AS DateTime), NULL, NULL)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (27, 16, NULL, 4, CAST(N'2025-05-29T05:43:51.897' AS DateTime), N'admin', N'Thanh toán thành công và đã cập nhật số lượng trong kho')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (28, 2, NULL, 2, CAST(N'2025-05-29T05:44:50.373' AS DateTime), NULL, N'Admin đã xác nhận đơn hàng')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (29, 2, NULL, 3, CAST(N'2025-05-29T05:44:58.550' AS DateTime), NULL, N'Đơn hàng đã được gửi lúc 2025-05-29')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (30, 17, NULL, 0, CAST(N'2025-05-29T05:45:12.983' AS DateTime), NULL, NULL)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (31, 17, NULL, 4, CAST(N'2025-05-29T05:58:48.697' AS DateTime), N'admin', N'Thanh toán thành công')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (32, 18, NULL, 0, CAST(N'2025-05-29T05:59:18.743' AS DateTime), NULL, NULL)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (33, 18, NULL, 5, CAST(N'2025-05-29T06:00:19.743' AS DateTime), NULL, N'Đơn hàng đã được hủy lúc 2025-05-29T06:00:19.741907500')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (34, 19, NULL, 0, CAST(N'2025-05-29T06:00:21.557' AS DateTime), NULL, NULL)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (35, 19, NULL, 4, CAST(N'2025-05-29T06:10:49.693' AS DateTime), N'admin', N'Thanh toán thành công')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (36, 20, NULL, 0, CAST(N'2025-05-29T06:10:52.357' AS DateTime), NULL, NULL)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (37, 20, NULL, 4, CAST(N'2025-05-29T06:11:51.383' AS DateTime), N'admin', N'Thanh toán thành công')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (38, 21, NULL, 0, CAST(N'2025-05-29T06:11:59.210' AS DateTime), NULL, NULL)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (39, 21, NULL, 4, CAST(N'2025-05-29T06:14:40.137' AS DateTime), N'admin', N'Thanh toán thành công')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (40, 22, NULL, 0, CAST(N'2025-05-29T06:20:00.633' AS DateTime), NULL, NULL)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (41, 22, NULL, 4, CAST(N'2025-05-29T06:21:46.727' AS DateTime), N'admin', N'Thanh toán thành công')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (42, 22, NULL, 4, CAST(N'2025-05-29T06:21:51.703' AS DateTime), N'admin', N'Thanh toán thành công')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (43, 23, NULL, 0, CAST(N'2025-05-29T06:22:20.727' AS DateTime), NULL, NULL)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (44, 23, NULL, 4, CAST(N'2025-05-29T06:22:57.730' AS DateTime), N'admin', N'Thanh toán thành công')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (45, 23, NULL, 4, CAST(N'2025-05-29T06:23:18.440' AS DateTime), N'admin', N'Thanh toán thành công')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (46, 24, NULL, 0, CAST(N'2025-05-29T06:23:31.253' AS DateTime), NULL, NULL)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (47, 24, NULL, 4, CAST(N'2025-05-29T06:23:56.667' AS DateTime), N'admin', N'Thanh toán thành công')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (48, 24, NULL, 4, CAST(N'2025-05-29T06:23:56.710' AS DateTime), N'admin', N'Thanh toán thành công')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (49, 25, NULL, 0, CAST(N'2025-05-29T06:25:11.740' AS DateTime), NULL, NULL)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (50, 25, NULL, 4, CAST(N'2025-05-29T06:30:43.330' AS DateTime), N'admin', N'Thanh toán thành công')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (51, 26, NULL, 0, CAST(N'2025-05-29T06:33:34.777' AS DateTime), NULL, NULL)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (52, 26, NULL, 5, CAST(N'2025-05-29T06:35:19.410' AS DateTime), NULL, N'Đơn hàng đã được hủy lúc 2025-05-29T06:35:19.410157300')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (53, 27, NULL, 0, CAST(N'2025-05-29T06:35:23.890' AS DateTime), NULL, NULL)
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (54, 27, NULL, 5, CAST(N'2025-05-29T06:35:33.667' AS DateTime), NULL, N'Đơn hàng đã được hủy lúc 2025-05-29T06:35:33.666889500')
INSERT [dbo].[lich_su_hoa_don] ([id], [id_hoa_don], [id_nhan_vien], [trang_thai], [ngay_tao], [nguoi_tao], [mo_ta]) VALUES (55, 28, NULL, 0, CAST(N'2025-05-29T06:35:36.450' AS DateTime), NULL, NULL)
SET IDENTITY_INSERT [dbo].[lich_su_hoa_don] OFF
GO
SET IDENTITY_INSERT [dbo].[mau_sac] ON 

INSERT [dbo].[mau_sac] ([id], [ten], [trang_thai]) VALUES (1, N'Đen', 1)
INSERT [dbo].[mau_sac] ([id], [ten], [trang_thai]) VALUES (2, N'Trắng', 1)
INSERT [dbo].[mau_sac] ([id], [ten], [trang_thai]) VALUES (3, N'Bạc', 1)
INSERT [dbo].[mau_sac] ([id], [ten], [trang_thai]) VALUES (4, N'Xanh', 1)
INSERT [dbo].[mau_sac] ([id], [ten], [trang_thai]) VALUES (5, N'Vàng đồng', 1)
SET IDENTITY_INSERT [dbo].[mau_sac] OFF
GO
SET IDENTITY_INSERT [dbo].[Momo_transaction] ON 

INSERT [dbo].[Momo_transaction] ([id], [id_hoa_don], [partner_code], [order_id], [amount], [order_info], [order_type], [trans_id], [response_time], [message], [pay_type], [signature], [ngay_tao], [trang_thai], [extra_data], [request_id], [result_code], [redirect_url], [ipn_url], [request_type], [pay_url]) VALUES (1, 1, N'MOMOXXX', N'HD001', CAST(2280000 AS Decimal(18, 0)), N'Thanh toán đơn hàng HD001', N'momo_wallet', 12345678, 1679825718, N'Giao dịch thành công', N'app', N'xxx', CAST(N'2025-05-29T04:38:57.250' AS DateTime), 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[Momo_transaction] ([id], [id_hoa_don], [partner_code], [order_id], [amount], [order_info], [order_type], [trans_id], [response_time], [message], [pay_type], [signature], [ngay_tao], [trang_thai], [extra_data], [request_id], [result_code], [redirect_url], [ipn_url], [request_type], [pay_url]) VALUES (2, 20, N'MOMOTEST', N'HD020', CAST(2125000 AS Decimal(18, 0)), N'Thanh toán đơn hàng HD020', N'momo_wallet', 1748473911377, 1748473861662, N'Giao dịch thành công', N'app', N'c2f6b67e-2327-49dd-a1d3-353bcee85499', CAST(N'2025-05-29T06:11:01.663' AS DateTime), 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[Momo_transaction] ([id], [id_hoa_don], [partner_code], [order_id], [amount], [order_info], [order_type], [trans_id], [response_time], [message], [pay_type], [signature], [ngay_tao], [trang_thai], [extra_data], [request_id], [result_code], [redirect_url], [ipn_url], [request_type], [pay_url]) VALUES (3, 21, N'MOMOTEST', N'HD021', CAST(2500000 AS Decimal(18, 0)), N'Thanh toán đơn hàng HD021', N'momo_wallet', 1748474080129, 1748474075082, N'Giao dịch thành công', N'app', N'df26d02c-df4a-4ba6-ac40-314251ac291e', CAST(N'2025-05-29T06:14:35.083' AS DateTime), 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[Momo_transaction] ([id], [id_hoa_don], [partner_code], [order_id], [amount], [order_info], [order_type], [trans_id], [response_time], [message], [pay_type], [signature], [ngay_tao], [trang_thai], [extra_data], [request_id], [result_code], [redirect_url], [ipn_url], [request_type], [pay_url]) VALUES (4, 22, N'MOMO', N'HDHD022_1748474489533', CAST(2500000 AS Decimal(18, 0)), N'Thanh toán đơn hàng HD022', N'momo_wallet', 1748474511698, 1748474511698, N'Giao dịch thành công', NULL, N'41c65317617c14429831e6ba587fabedb4586499b8261c328212ec18931530f0', CAST(N'2025-05-29T06:21:29.533' AS DateTime), 1, N'', N'RQHD022_1748474489533', 0, N'http://localhost:8080/payment/momo-return', N'http://localhost:8080/payment/momo-notify', N'captureWallet', N'https://test-payment.momo.vn/v2/gateway/pay?t=TU9NT3xIREhEMDIyXzE3NDg0NzQ0ODk1MzM&s=0a0e9d7cd27465ac88ed38624fa965c6fcc458c7395396f622f9aaefc0a4e096')
INSERT [dbo].[Momo_transaction] ([id], [id_hoa_don], [partner_code], [order_id], [amount], [order_info], [order_type], [trans_id], [response_time], [message], [pay_type], [signature], [ngay_tao], [trang_thai], [extra_data], [request_id], [result_code], [redirect_url], [ipn_url], [request_type], [pay_url]) VALUES (5, 23, N'MOMO', N'HDHD023_1748474561482', CAST(800000 AS Decimal(18, 0)), N'Thanh toán đơn hàng HD023', N'momo_wallet', 1748474598434, 1748474598434, N'Giao dịch thành công', NULL, N'c3c61fee37e37be6b407d7fb8e15f4d06524e3a729fb1e6d9895fb25880bdc0a', CAST(N'2025-05-29T06:22:41.483' AS DateTime), 1, N'', N'RQHD023_1748474561482', 0, N'http://localhost:8080/payment/momo-return', N'http://localhost:8080/payment/momo-notify', N'captureWallet', N'https://test-payment.momo.vn/v2/gateway/pay?t=TU9NT3xIREhEMDIzXzE3NDg0NzQ1NjE0ODI&s=5f97883cc15d2609363019cbe99dcbbaac226205809e8c68e07123ce0dca705b')
INSERT [dbo].[Momo_transaction] ([id], [id_hoa_don], [partner_code], [order_id], [amount], [order_info], [order_type], [trans_id], [response_time], [message], [pay_type], [signature], [ngay_tao], [trang_thai], [extra_data], [request_id], [result_code], [redirect_url], [ipn_url], [request_type], [pay_url]) VALUES (6, 24, N'MOMO', N'HDHD024_1748474618325', CAST(800000 AS Decimal(18, 0)), N'Thanh toán đơn hàng HD024', N'momo_wallet', 1748474636707, 1748474636707, N'Giao dịch thành công', NULL, N'01ad8c6a0cbfb6cfb2d09a469ac3cfa12eb29f9cebbe4de45d97d78eb633ec6d', CAST(N'2025-05-29T06:23:38.327' AS DateTime), 1, N'', N'RQHD024_1748474618325', 0, N'http://localhost:8080/payment/momo-return', N'http://localhost:8080/payment/momo-notify', N'captureWallet', N'https://test-payment.momo.vn/v2/gateway/pay?t=TU9NT3xIREhEMDI0XzE3NDg0NzQ2MTgzMjU&s=3bc4907495afbf3b42c23cd17f5d67a4b80c1bb42b5d5c8a4e36373fa93e4828')
SET IDENTITY_INSERT [dbo].[Momo_transaction] OFF
GO
SET IDENTITY_INSERT [dbo].[nhan_vien] ON 

INSERT [dbo].[nhan_vien] ([id], [id_chuc_vu], [id_tai_khoan], [id_dia_chi], [ma], [ten], [gioi_tinh], [ngay_sinh], [so_dien_thoai], [can_cuoc_cong_dan], [ngay_tao], [ngay_sua], [trang_thai], [hinh_anh]) VALUES (1, 1, 1, 1, N'NV001', N'Phạm Văn Admin', 1, CAST(N'1988-01-01T00:00:00.000' AS DateTime), N'0123456789', N'001188123456', CAST(N'2025-05-29T04:38:57.237' AS DateTime), NULL, 1, NULL)
INSERT [dbo].[nhan_vien] ([id], [id_chuc_vu], [id_tai_khoan], [id_dia_chi], [ma], [ten], [gioi_tinh], [ngay_sinh], [so_dien_thoai], [can_cuoc_cong_dan], [ngay_tao], [ngay_sua], [trang_thai], [hinh_anh]) VALUES (2, 2, 2, 2, N'NV002', N'Nguyễn Thị Sale', 0, CAST(N'1992-02-02T00:00:00.000' AS DateTime), N'0123456790', N'001192123456', CAST(N'2025-05-29T04:38:57.237' AS DateTime), NULL, 1, NULL)
INSERT [dbo].[nhan_vien] ([id], [id_chuc_vu], [id_tai_khoan], [id_dia_chi], [ma], [ten], [gioi_tinh], [ngay_sinh], [so_dien_thoai], [can_cuoc_cong_dan], [ngay_tao], [ngay_sua], [trang_thai], [hinh_anh]) VALUES (3, 3, 3, 3, N'NV003', N'Trần Văn Stock', 1, CAST(N'1990-03-03T00:00:00.000' AS DateTime), N'0123456791', N'001190123456', CAST(N'2025-05-29T04:38:57.237' AS DateTime), NULL, 1, NULL)
SET IDENTITY_INSERT [dbo].[nhan_vien] OFF
GO
SET IDENTITY_INSERT [dbo].[nut_bam] ON 

INSERT [dbo].[nut_bam] ([id], [ten], [trang_thai]) VALUES (1, N'Nút nhấn cơ', 1)
INSERT [dbo].[nut_bam] ([id], [ten], [trang_thai]) VALUES (2, N'Nút điện tử', 1)
INSERT [dbo].[nut_bam] ([id], [ten], [trang_thai]) VALUES (3, N'Remote điều khiển', 1)
INSERT [dbo].[nut_bam] ([id], [ten], [trang_thai]) VALUES (4, N'Cảm ứng', 1)
SET IDENTITY_INSERT [dbo].[nut_bam] OFF
GO
SET IDENTITY_INSERT [dbo].[phieu_giam_gia] ON 

INSERT [dbo].[phieu_giam_gia] ([id], [ma], [ten], [ngay_bat_dau], [ngay_ket_thuc], [so_luong], [loai_giam_gia], [gia_tri_giam], [ngay_tao], [nguoi_tao], [trang_thai]) VALUES (1, N'PGG001', N'Giảm 10%', CAST(N'2024-01-01T00:00:00.000' AS DateTime), CAST(N'2024-12-31T00:00:00.000' AS DateTime), 100, 1, CAST(10.00 AS Numeric(38, 2)), CAST(N'2025-05-29T04:38:57.237' AS DateTime), N'Admin', 0)
INSERT [dbo].[phieu_giam_gia] ([id], [ma], [ten], [ngay_bat_dau], [ngay_ket_thuc], [so_luong], [loai_giam_gia], [gia_tri_giam], [ngay_tao], [nguoi_tao], [trang_thai]) VALUES (2, N'PGG002', N'Giảm 100k', CAST(N'2024-01-01T00:00:00.000' AS DateTime), CAST(N'2024-12-31T00:00:00.000' AS DateTime), 50, 0, CAST(100000.00 AS Numeric(38, 2)), CAST(N'2025-05-29T04:38:57.237' AS DateTime), N'Admin', 0)
INSERT [dbo].[phieu_giam_gia] ([id], [ma], [ten], [ngay_bat_dau], [ngay_ket_thuc], [so_luong], [loai_giam_gia], [gia_tri_giam], [ngay_tao], [nguoi_tao], [trang_thai]) VALUES (3, N'PGG003', N'SALE LASTWEEK', CAST(N'2025-05-28T00:00:00.000' AS DateTime), CAST(N'2025-05-31T00:00:00.000' AS DateTime), 144, 1, CAST(15.00 AS Numeric(38, 2)), CAST(N'2025-05-29T04:54:41.323' AS DateTime), NULL, 1)
SET IDENTITY_INSERT [dbo].[phieu_giam_gia] OFF
GO
SET IDENTITY_INSERT [dbo].[san_pham] ON 

INSERT [dbo].[san_pham] ([id], [id_kieu_quat], [ma], [ten], [mo_ta], [ngay_tao], [trang_thai]) VALUES (1, 1, N'QT001', N'Quạt trần Royal 5 cánh', N'Qu?t tr?n cao c?p 5 cánh', CAST(N'2025-05-29T04:38:57.220' AS DateTime), 1)
INSERT [dbo].[san_pham] ([id], [id_kieu_quat], [ma], [ten], [mo_ta], [ngay_tao], [trang_thai]) VALUES (2, 2, N'QD001', N'Quạt đứng Senko', N'Qu?t d?ng công ngh? Nh?t B?n', CAST(N'2025-05-29T04:38:57.220' AS DateTime), 1)
INSERT [dbo].[san_pham] ([id], [id_kieu_quat], [ma], [ten], [mo_ta], [ngay_tao], [trang_thai]) VALUES (3, 3, N'QB001', N'Quạt bàn Xiaomi', N'Qu?t bàn thông minh', CAST(N'2025-05-29T04:38:57.220' AS DateTime), 1)
INSERT [dbo].[san_pham] ([id], [id_kieu_quat], [ma], [ten], [mo_ta], [ngay_tao], [trang_thai]) VALUES (4, 4, N'QTT001', N'Quạt treo Asia', N'Qu?t treo tu?ng công nghi?p', CAST(N'2025-05-29T04:38:57.220' AS DateTime), 1)
SET IDENTITY_INSERT [dbo].[san_pham] OFF
GO
SET IDENTITY_INSERT [dbo].[san_pham_chi_tiet] ON 

INSERT [dbo].[san_pham_chi_tiet] ([id], [id_mau_sac], [id_san_pham], [id_nut_bam], [id_cong_suat], [id_hang], [id_hinh_anh], [gia], [so_luong], [can_nang], [mo_ta], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai]) VALUES (1, 1, 1, 1, 1, 1, 1, CAST(2500000.00 AS Numeric(38, 2)), 39, 3.5, N'Qu?t tr?n cao c?p màu den', CAST(N'2025-05-29T04:38:57.230' AS DateTime), NULL, N'Admin', 1)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_mau_sac], [id_san_pham], [id_nut_bam], [id_cong_suat], [id_hang], [id_hinh_anh], [gia], [so_luong], [can_nang], [mo_ta], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai]) VALUES (2, 2, 2, 2, 2, 2, 2, CAST(1500000.00 AS Numeric(38, 2)), 28, 2.5, N'Qu?t d?ng màu tr?ng', CAST(N'2025-05-29T04:38:57.230' AS DateTime), NULL, N'Admin', 1)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_mau_sac], [id_san_pham], [id_nut_bam], [id_cong_suat], [id_hang], [id_hinh_anh], [gia], [so_luong], [can_nang], [mo_ta], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai]) VALUES (3, 3, 3, 3, 3, 3, 3, CAST(800000.00 AS Numeric(38, 2)), 38, 1.5, N'Qu?t bàn màu b?c', CAST(N'2025-05-29T04:38:57.230' AS DateTime), NULL, N'Admin', 1)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_mau_sac], [id_san_pham], [id_nut_bam], [id_cong_suat], [id_hang], [id_hinh_anh], [gia], [so_luong], [can_nang], [mo_ta], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai]) VALUES (4, 1, 4, 1, 2, 1, 4, CAST(1200000.00 AS Numeric(38, 2)), 25, 2, N'Qu?t treo tu?ng màu den', CAST(N'2025-05-29T04:38:57.230' AS DateTime), NULL, N'Admin', 1)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_mau_sac], [id_san_pham], [id_nut_bam], [id_cong_suat], [id_hang], [id_hinh_anh], [gia], [so_luong], [can_nang], [mo_ta], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai]) VALUES (5, 2, 1, 2, 3, 2, 1, CAST(2700000.00 AS Numeric(38, 2)), 20, 3.8, N'Qu?t tr?n cao c?p màu tr?ng', CAST(N'2025-05-29T04:38:57.230' AS DateTime), NULL, N'Admin', 1)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_mau_sac], [id_san_pham], [id_nut_bam], [id_cong_suat], [id_hang], [id_hinh_anh], [gia], [so_luong], [can_nang], [mo_ta], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai]) VALUES (6, 3, 2, 3, 1, 3, 2, CAST(1600000.00 AS Numeric(38, 2)), 35, 2.3, N'Qu?t d?ng màu b?c', CAST(N'2025-05-29T04:38:57.230' AS DateTime), NULL, N'Admin', 1)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_mau_sac], [id_san_pham], [id_nut_bam], [id_cong_suat], [id_hang], [id_hinh_anh], [gia], [so_luong], [can_nang], [mo_ta], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai]) VALUES (7, 1, 3, 1, 2, 1, 3, CAST(900000.00 AS Numeric(38, 2)), 45, 1.6, N'Qu?t bàn màu den', CAST(N'2025-05-29T04:38:57.230' AS DateTime), NULL, N'Admin', 1)
INSERT [dbo].[san_pham_chi_tiet] ([id], [id_mau_sac], [id_san_pham], [id_nut_bam], [id_cong_suat], [id_hang], [id_hinh_anh], [gia], [so_luong], [can_nang], [mo_ta], [ngay_tao], [ngay_sua], [nguoi_tao], [trang_thai]) VALUES (8, 2, 4, 2, 3, 2, 4, CAST(1300000.00 AS Numeric(38, 2)), 29, 2.2000000476837158, N'Qu?t treo tu?ng màu tr?ng', CAST(N'2025-05-29T04:38:57.230' AS DateTime), NULL, N'Admin', 1)
SET IDENTITY_INSERT [dbo].[san_pham_chi_tiet] OFF
GO
SET IDENTITY_INSERT [dbo].[tai_khoan] ON 

INSERT [dbo].[tai_khoan] ([id], [id_chuc_vu], [ma], [email], [mat_khau], [reset_token], [ngay_tao], [trang_thai]) VALUES (1, 1, N'AD001', N'admin@gmail.com', N'123456', NULL, N'May 29 2025  4:38AM', 1)
INSERT [dbo].[tai_khoan] ([id], [id_chuc_vu], [ma], [email], [mat_khau], [reset_token], [ngay_tao], [trang_thai]) VALUES (2, 2, N'NV001', N'nhanvien1@gmail.com', N'123456', NULL, N'May 29 2025  4:38AM', 1)
INSERT [dbo].[tai_khoan] ([id], [id_chuc_vu], [ma], [email], [mat_khau], [reset_token], [ngay_tao], [trang_thai]) VALUES (3, 3, N'NV002', N'nhanvien2@gmail.com', N'123456', NULL, N'May 29 2025  4:38AM', 1)
INSERT [dbo].[tai_khoan] ([id], [id_chuc_vu], [ma], [email], [mat_khau], [reset_token], [ngay_tao], [trang_thai]) VALUES (4, 4, N'QL001', N'quanly@gmail.com', N'123456', NULL, N'May 29 2025  4:38AM', 1)
SET IDENTITY_INSERT [dbo].[tai_khoan] OFF
GO
ALTER TABLE [dbo].[hoa_don]  WITH CHECK ADD FOREIGN KEY([id_dia_chi])
REFERENCES [dbo].[dia_chi] ([id])
GO
ALTER TABLE [dbo].[hoa_don]  WITH CHECK ADD FOREIGN KEY([id_khach_hang])
REFERENCES [dbo].[khach_hang] ([id])
GO
ALTER TABLE [dbo].[hoa_don]  WITH CHECK ADD FOREIGN KEY([id_nhan_vien])
REFERENCES [dbo].[nhan_vien] ([id])
GO
ALTER TABLE [dbo].[hoa_don]  WITH CHECK ADD FOREIGN KEY([id_phieu_giam_gia])
REFERENCES [dbo].[phieu_giam_gia] ([id])
GO
ALTER TABLE [dbo].[hoa_don_chi_tiet]  WITH CHECK ADD FOREIGN KEY([id_hoa_don])
REFERENCES [dbo].[hoa_don] ([id])
GO
ALTER TABLE [dbo].[hoa_don_chi_tiet]  WITH CHECK ADD FOREIGN KEY([id_san_pham_chi_tiet])
REFERENCES [dbo].[san_pham_chi_tiet] ([id])
GO
ALTER TABLE [dbo].[khach_hang]  WITH CHECK ADD FOREIGN KEY([id_dia_chi])
REFERENCES [dbo].[dia_chi] ([id])
GO
ALTER TABLE [dbo].[khach_hang]  WITH CHECK ADD FOREIGN KEY([id_tai_khoan])
REFERENCES [dbo].[tai_khoan] ([id])
GO
ALTER TABLE [dbo].[lich_su_hoa_don]  WITH CHECK ADD FOREIGN KEY([id_hoa_don])
REFERENCES [dbo].[hoa_don] ([id])
GO
ALTER TABLE [dbo].[lich_su_hoa_don]  WITH CHECK ADD FOREIGN KEY([id_nhan_vien])
REFERENCES [dbo].[nhan_vien] ([id])
GO
ALTER TABLE [dbo].[Momo_transaction]  WITH CHECK ADD FOREIGN KEY([id_hoa_don])
REFERENCES [dbo].[hoa_don] ([id])
GO
ALTER TABLE [dbo].[nhan_vien]  WITH CHECK ADD FOREIGN KEY([id_chuc_vu])
REFERENCES [dbo].[chuc_vu] ([id])
GO
ALTER TABLE [dbo].[nhan_vien]  WITH CHECK ADD FOREIGN KEY([id_dia_chi])
REFERENCES [dbo].[dia_chi] ([id])
GO
ALTER TABLE [dbo].[nhan_vien]  WITH CHECK ADD FOREIGN KEY([id_tai_khoan])
REFERENCES [dbo].[tai_khoan] ([id])
GO
ALTER TABLE [dbo].[san_pham]  WITH CHECK ADD FOREIGN KEY([id_kieu_quat])
REFERENCES [dbo].[kieu_quat] ([id])
GO
ALTER TABLE [dbo].[san_pham_chi_tiet]  WITH CHECK ADD FOREIGN KEY([id_cong_suat])
REFERENCES [dbo].[cong_suat] ([id])
GO
ALTER TABLE [dbo].[san_pham_chi_tiet]  WITH CHECK ADD FOREIGN KEY([id_hang])
REFERENCES [dbo].[hang] ([id])
GO
ALTER TABLE [dbo].[san_pham_chi_tiet]  WITH CHECK ADD FOREIGN KEY([id_hinh_anh])
REFERENCES [dbo].[hinh_anh] ([id])
GO
ALTER TABLE [dbo].[san_pham_chi_tiet]  WITH CHECK ADD FOREIGN KEY([id_mau_sac])
REFERENCES [dbo].[mau_sac] ([id])
GO
ALTER TABLE [dbo].[san_pham_chi_tiet]  WITH CHECK ADD FOREIGN KEY([id_nut_bam])
REFERENCES [dbo].[nut_bam] ([id])
GO
ALTER TABLE [dbo].[san_pham_chi_tiet]  WITH CHECK ADD FOREIGN KEY([id_san_pham])
REFERENCES [dbo].[san_pham] ([id])
GO
ALTER TABLE [dbo].[tai_khoan]  WITH CHECK ADD FOREIGN KEY([id_chuc_vu])
REFERENCES [dbo].[chuc_vu] ([id])
GO
USE [master]
GO
ALTER DATABASE [datn] SET  READ_WRITE 
GO
