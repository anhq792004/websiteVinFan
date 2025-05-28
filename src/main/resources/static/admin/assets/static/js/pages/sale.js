$('#taoHoaDon').on('submit', function (e) {
    e.preventDefault(); // Ngăn form thực hiện submit mặc định

    $.ajax({
        url: '/sale/tao-hoa-don',
        type: 'POST',
        success: function (response) {
            Swal.fire({
                toast: true,
                icon: 'success',
                title: response, // Thông báo thành công từ server
                position: 'top-end',
                showConfirmButton: false,
                timer: 500,
                timerProgressBar: true
            }).then(() => {
                location.reload();
            });
        },
        error: function (xhr) {
            Swal.fire({
                toast: true,
                icon: 'error',
                title: xhr.responseText, // Thông báo lỗi trả về từ controller
                position: 'top-end',
                showConfirmButton: false,
                timer: 2000,
                timerProgressBar: true
            }).then(() => {
                location.reload();
            });
        }
    });
});

$(document).ready(function () {
    $(".huyHoaDon").click(function () {
        const hoaDonId = $(this).data("id"); // Lấy ID từ data-id trên thẻ a

        Swal.fire({
            title: "Hủy hóa đơn?",
            text: "Bạn có chắc chắn muốn hủy hóa đơn này?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Xác nhận"
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    url: "/sale/huy",
                    type: "POST",
                    data: {id: hoaDonId},
                    success: function (response) {
                        Swal.fire({
                            toast: true,
                            icon: 'success',
                            title: response,
                            position: 'top-end',
                            showConfirmButton: false,
                            timer: 1000,
                            timerProgressBar: true
                        }).then(() => {
                            window.location.href = "/sale/index";
                        });
                    },
                    error: function () {
                        Swal.fire("Lỗi!", "Lỗi khi xác nhận hủy hóa đơn", "error");
                    }
                });
            }
        });
    });
});

$(".btn-add-sanPham").click(function () {
    const hoaDonId = $(this).data("id-hd");
    const sanPhamId = $(this).data("id-sp");
    const gia = $(this).data("gia");
    const soLuong = 1;

    $.ajax({
        url: '/sale/addSP',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            idHD: hoaDonId,
            idSP: sanPhamId,
            gia: gia,
            soLuong: soLuong
        }),
        success: function (response) {
            Swal.fire({
                toast: true,
                icon: 'success',
                title: response,
                position: 'top-end',
                showConfirmButton: false,
                timer: 500,
                timerProgressBar: true
            }).then(() => {
                location.reload();
            });
        },
        error: function (xhr) {
            Swal.fire({
                toast: true,
                icon: 'error',
                title: xhr.responseText,
                position: 'top-end',
                showConfirmButton: false,
                timer: 1000,
                timerProgressBar: true
            });
        }
    });
});

$(".btn-add-khachHang").click(function () {
    const hoaDonId = $(this).data("id-hd");
    const sanPhamId = $(this).data("id-sp");
    const ten = $(this).data("ten");
    const sdt = $(this).data("sdt");

    $.ajax({
        url: '/sale/addKH',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            idHD: hoaDonId,
            idSP: sanPhamId,
            ten: ten,
            sdt: sdt
        }),
        success: function (response) {
            Swal.fire({
                toast: true,
                icon: 'success',
                title: response,
                position: 'top-end',
                showConfirmButton: false,
                timer: 500,
                timerProgressBar: true
            }).then(() => {
                location.reload();
            });
        },
        error: function (xhr) {
            Swal.fire({
                toast: true,
                icon: 'error',
                title: xhr.responseText,
                position: 'top-end',
                showConfirmButton: false,
                timer: 1000,
                timerProgressBar: true
            });
        }
    });
});

$(".btn-xoa-sanPham").click(function () {
    const sanPhamId = $(this).data("id-sp");
    const hoaDonId = $(this).data("id-hd");

    $.ajax({
        url: '/sale/xoa',
        type: 'POST',
        data: {
            idSP: sanPhamId,
            idHD: hoaDonId
        },
        success: function (response) {
            Swal.fire({
                toast: true,
                icon: 'success',
                title: response,
                position: 'top-end',
                showConfirmButton: false,
                timer: 1000,
                timerProgressBar: true
            }).then(() => {
                location.reload();
            });
        },
        error: function (xhr) {
            Swal.fire({
                toast: true,
                icon: 'error',
                title: xhr.responseText,
                position: 'top-end',
                showConfirmButton: false,
                timer: 1500,
                timerProgressBar: true
            }).then(() => location.reload());
        }
    });
});

$(".quantity-btn").click(function () {
    const idSP = $(this).closest("form").data("idsp");  // lấy từ data-idsp
    const idHD = $(this).closest("form").data("idhd");  // lấy từ data-idhd

    $.ajax({
        url: '/sale/tangSoLuong',
        type: 'POST',
        data: {
            idSP: idSP,
            idHD: idHD,
        },
        success: function (response) {
            Swal.fire({
                toast: true,
                icon: 'success',
                title: response,
                position: 'top-end',
                showConfirmButton: false,
                timer: 1000
            }).then(() => location.reload());
        },
        error: function (xhr) {
            Swal.fire({
                toast: true,
                icon: 'error',
                title: xhr.responseText,
                position: 'top-end',
                showConfirmButton: false,
                timer: 1000
            })
                .then(() => location.reload());
        }
    });
});

$(".quantity-btn-1").click(function () {
    const idSP = $(this).closest("form").data("idsp");  // lấy từ data-idsp
    const idHD = $(this).closest("form").data("idhd");  // lấy từ data-idhd

    $.ajax({
        url: '/sale/giamSoLuong',
        type: 'POST',
        data: {
            idSP: idSP,
            idHD: idHD,
        },
        success: function (response) {
            Swal.fire({
                toast: true,
                icon: 'success',
                title: response,
                position: 'top-end',
                showConfirmButton: false,
                timer: 1000
            })
                .then(() => location.reload());
        },
        error: function (xhr) {
            Swal.fire({
                toast: true,
                icon: 'error',
                title: xhr.responseText,
                position: 'top-end',
                showConfirmButton: false,
                timer: 1000
            })
                .then(() => location.reload());
        }
    });
});

$(document).ready(function () {
    $('.update-so-luong-form').on('submit', function (e) {
        e.preventDefault();

        const form = $(this);
        const idsp = form.data('idsp');
        const idhd = form.data('idhd');
        const soLuong = form.find('.quantity-input').val();
        const gia = form.data('gia');

        $.ajax({
            url: `/sale/updateSoLuong`,
            type: 'POST',
            data: {
                idsp: idsp,
                idhd: idhd,
                soLuong: soLuong,
                gia: gia
            },
            success: function (response) {
                Swal.fire({
                    toast: true,
                    icon: 'success',
                    title: response,
                    position: 'top-end',
                    showConfirmButton: false,
                    timer: 1000,
                    timerProgressBar: true
                }).then(() => {
                    location.reload();
                });
            },
            error: function (xhr) {
                Swal.fire({
                    toast: true,
                    icon: 'error',
                    title: xhr.responseText,
                    position: 'top-end',
                    showConfirmButton: false,
                    timer: 3000,
                    timerProgressBar: true
                });
            }
        });
    });
});

$(document).ready(function () {
    $('#btnThanhToan').click(function () {
        const idHD = $(this).data('id'); // lấy giá trị id hóa đơn từ nút

        $.ajax({
            url: '/sale/thanh-toan',
            type: 'POST',
            data: {idHD: idHD}, // gửi dữ liệu lên server
            success: function (response) {
                Swal.fire({
                    toast: true,
                    icon: 'success',
                    title: response,
                    position: 'top-end',
                    showConfirmButton: false,
                    timer: 1000,
                    timerProgressBar: true
                }).then(() => {
                    window.location.href = '/sale/index';
                });
            },
            error: function (xhr) {
                Swal.fire({
                    toast: true,
                    icon: 'error',
                    title: xhr.responseText,
                    position: 'top-end',
                    showConfirmButton: false,
                    timer: 3000,
                    timerProgressBar: true
                });
            }
        });
    });
});
//tính tiền thừa
document.addEventListener("DOMContentLoaded", function () {
    const inputTienKhachTra = document.getElementById('tienKhachTra');
    const pTienThanhToan = document.getElementById('tienThanhToan');
    const pTienThua = document.getElementById('tienThua');

    if (!pTienThanhToan) {
        console.error("Không tìm thấy phần tử 'tienThanhToan'");
        return;
    }

    inputTienKhachTra.addEventListener('input', function () {
        const tienKhachTra = parseFloat(inputTienKhachTra.value) || 0;
        const tienThanhToan = parseFloat(pTienThanhToan.getAttribute('data-value')) || 0;
        const tienThua = tienKhachTra - tienThanhToan;

        // Format tiền thừa (có thể âm)
        const tienThuaFormatted = tienThua.toLocaleString('vi-VN', {minimumFractionDigits: 0}) + ' ₫';
        pTienThua.innerText = tienThuaFormatted;

        // Optional: đổi màu nếu âm
        if (tienThua < 0) {
            pTienThua.classList.add("text-danger");
        } else {
            pTienThua.classList.remove("text-danger");
        }
    });
});
// button ẩn form
document.addEventListener('DOMContentLoaded', function () {
    const toggle = document.getElementById('toggleForm');
    const form = document.getElementById('shippingForm');
    const btn = document.getElementById('btnDiaChi');

    toggle.addEventListener('change', function () {
        if (toggle.checked) {
            form.style.visibility = 'visible';
            btn.style.visibility = 'visible';
        } else {
            form.style.visibility = 'hidden';
            btn.style.visibility = 'hidden';
        }
    });
});

