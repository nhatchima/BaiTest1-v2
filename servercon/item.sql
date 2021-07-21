-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th7 19, 2021 lúc 04:45 AM
-- Phiên bản máy phục vụ: 10.4.19-MariaDB
-- Phiên bản PHP: 7.3.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `item`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitietdonhang`
--

CREATE TABLE `chitietdonhang` (
  `id` int(11) NOT NULL,
  `madonhang` int(11) NOT NULL,
  `masanpham` int(11) NOT NULL,
  `tensanpham` varchar(255) NOT NULL,
  `giasanpham` int(11) NOT NULL,
  `soluongsanpham` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `donhang`
--

CREATE TABLE `donhang` (
  `id` int(11) NOT NULL,
  `tenkhachhang` varchar(255) NOT NULL,
  `sodienthoai` int(11) NOT NULL,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `donhang`
--

INSERT INTO `donhang` (`id`, `tenkhachhang`, `sodienthoai`, `email`) VALUES
(1, 'dđ', 21312312, 'sầ'),
(2, 'dđ', 21312312, 'sầ'),
(3, 'dđ', 21312312, 'sầ'),
(4, 'dđ', 21312312, 'sầ'),
(5, 'dđ', 21312312, 'sầ'),
(6, 'dđ', 21312312, 'sầ'),
(7, 'dđ', 21312312, 'sầ'),
(8, 'dđ', 21312312, 'sầ'),
(9, 'dđ', 21312312, 'sầ'),
(10, 'dđ', 21312312, 'sầ'),
(11, 'dđ', 21312312, 'sầ'),
(12, 'dđ', 21312312, 'sầ'),
(13, 'dđ', 21312312, 'sầ'),
(14, 'dat', 12345678, 'datnguyen'),
(15, 'dat', 12345678, 'datnguyen'),
(16, 'dat', 12345678, 'datnguyen'),
(17, 'dat', 12345678, 'datnguyen'),
(18, 'dat', 12345678, 'datnguyen'),
(19, 'dat', 12345678, 'datnguyen'),
(20, 'dat', 12345678, 'datnguyen'),
(21, 'dat', 12345678, 'datnguyen'),
(22, 'dat', 12345678, 'datnguyen'),
(23, 'dat', 12345678, 'datnguyen'),
(24, 'dat', 12345678, 'datnguyen'),
(25, 'dat', 12345678, 'datnguyen'),
(26, 'dat', 12345678, 'datnguyen'),
(27, 'dat', 12345678, 'datnguyen'),
(28, 'dat', 12345678, 'datnguyen'),
(29, 'dat', 12345678, 'datnguyen'),
(30, 'dat', 123124, 'dat'),
(31, 'dat', 123124, 'dat'),
(32, 'dat', 123124, 'dat');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `loaivatpham`
--

CREATE TABLE `loaivatpham` (
  `idloaivatpham` int(11) NOT NULL,
  `tenloaivatpham` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `hinhloaivatpham` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `loaivatpham`
--

INSERT INTO `loaivatpham` (`idloaivatpham`, `tenloaivatpham`, `hinhloaivatpham`) VALUES
(1, 'Kiếm', 'https://www.tosbase.com/content/img/icons/items/icon_item_sword_30.png'),
(2, 'Khiên', 'https://static.wikia.nocookie.net/unisonleague/images/b/bd/Item-Granvia_Shield_Crest_Render.png/revision/latest?cb=20170929072425');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `vatpham`
--

CREATE TABLE `vatpham` (
  `id` int(11) NOT NULL,
  `idloaivatpham` int(3) NOT NULL,
  `tenvatpham` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `giavatpham` int(15) NOT NULL,
  `anhvatpham` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `vatpham`
--

INSERT INTO `vatpham` (`id`, `idloaivatpham`, `tenvatpham`, `giavatpham`, `anhvatpham`) VALUES
(1, 1, 'Kiếm Gỗ', 100000, 'https://opengameart.org/sites/default/files/short_sword_game_item.jpg'),
(2, 1, 'Katana', 200000, 'https://m.media-amazon.com/images/I/61zuPj4FLZL._AC_SX679_.jpg'),
(3, 1, 'Kiếm Laser', 300000, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTb9uyTY40d5KTRnBc_g_jFb7KSq19Z15-mkqXVhS7TpyWiCzfSAEaxyatWgReWe6ruPxY&usqp=CAU'),
(4, 2, 'Khiên Gỗ', 100000, 'http://www.tosbase.com/content/img/icons/items/icon_item_shield_8.png'),
(5, 2, 'Khiên Sắt', 200000, 'http://www.tosbase.com/content/img/icons/items/icon_item_shield_14.png'),
(6, 2, 'Khiên Vàng', 300000, 'https://static.wikia.nocookie.net/kidicarusfanon/images/4/43/Shield.jpg/revision/latest/scale-to-width-down/225?cb=20120603020029');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `donhang`
--
ALTER TABLE `donhang`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `loaivatpham`
--
ALTER TABLE `loaivatpham`
  ADD PRIMARY KEY (`idloaivatpham`);

--
-- Chỉ mục cho bảng `vatpham`
--
ALTER TABLE `vatpham`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `donhang`
--
ALTER TABLE `donhang`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT cho bảng `loaivatpham`
--
ALTER TABLE `loaivatpham`
  MODIFY `idloaivatpham` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `vatpham`
--
ALTER TABLE `vatpham`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
