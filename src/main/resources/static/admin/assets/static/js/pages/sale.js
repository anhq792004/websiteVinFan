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
                            location.reload();
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
                timer: 1000000,
                timerProgressBar: true
            });
        }
    });
});

$(".btn-xoa-sanPham").click(function () {
    const sanPhamId = $(this).data("id");

    $.ajax({
        url: '/sale/xoa',
        type: 'POST',
        data: {
            idSP: sanPhamId
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
            });
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
            });
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
            });
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
