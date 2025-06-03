let hoaDonId
let btn
$(document).ready(function () {
    $(".btn-xac-nhan").click(function () {
        btn = $(this);
        hoaDonId = $(this).closest("form").data("id"); // Lấy ID hóa đơn
// Vô hiệu hóa nút
        btn.prop("disabled", true);
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
                    url: "/hoa-don/xac-nhan",
                    type: "POST",
                    data: {id: hoaDonId}, // Gửi ID của hóa đơn
                    success: function (response) {
                        Swal.fire({
                            toast: true,
                            icon: 'success',
                            title: response, // Thông báo thành công từ server
                            position: 'top-end',
                            showConfirmButton: false,
                            timer: 1000,
                            timerProgressBar: true
                        }).then(() => {
                            location.reload(); // Reload lại trang sau khi nhấn OK
                        });
                    },
                    error: function () {
                        Swal.fire("Lỗi!", "Lỗi khi xác nhận hóa đơn", "error");
                        btn.prop("disabled", false);
                    }
                });
            }
        });
    });
});


//
$(document).ready(function () {
    $(".btn-giao-hang").click(function () {
        btn = $(this);
        hoaDonId = $(this).closest("form").data("id"); // Lấy ID hóa đơn
        // Vô hiệu hóa nút
        btn.prop("disabled", true);

        $.ajax({
            url: "/hoa-don/giao-hang",
            type: "POST",
            data: {id: hoaDonId}, // Gửi ID của hóa đơn
            success: function (response) {
                Swal.fire({
                    toast: true,
                    icon: 'success',
                    title: response, // Thông báo từ server
                    position: 'top-end',
                    showConfirmButton: false,
                    timer: 1000,
                    timerProgressBar: true
                }).then(() => {
                    location.reload(); // Reload lại trang sau khi thành công
                });
            },
            error: function () {
                Swal.fire("Lỗi!", "Lỗi khi giao hóa đơn", "error");
                btn.prop("disabled", false);

            }
        });
    });
});

$(document).ready(function () {
    $(".btn-hoan-thanh").click(function () {
        btn = $(this);
        hoaDonId = $(this).closest("form").data("id"); // Lấy ID hóa đơn
        // Vô hiệu hóa nút
        btn.prop("disabled", true);

        $.ajax({
            url: "/hoa-don/hoan-thanh",
            type: "POST",
            data: {id: hoaDonId}, // Gửi ID của hóa đơn
            success: function (response) {
                Swal.fire({
                    toast: true,
                    icon: 'success',
                    title: response, // Thông báo từ server
                    position: 'top-end',
                    showConfirmButton: false,
                    timer: 1000,
                    timerProgressBar: true
                }).then(() => {
                    location.reload(); // Reload lại trang sau khi thành công
                });
            },
            error: function () {
                Swal.fire("Lỗi!", "Lỗi khi hoàn thành hóa đơn", "error");
                btn.prop("disabled", false);

            }
        });
    });
});

$(document).ready(function () {
    $(".btn-huy").click(function () {
        hoaDonId = $(this).closest("form").data("id"); // Lấy ID hóa đơn

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
                    url: "/hoa-don/huy",
                    type: "POST",
                    data: {id: hoaDonId}, // Gửi ID của hóa đơn
                    success: function (response) {
                        Swal.fire({
                            toast: true,
                            icon: 'success',
                            title: response, // Thông báo thành công từ server
                            position: 'top-end',
                            showConfirmButton: false,
                            timer: 1000,
                            timerProgressBar: true
                        }).then(() => {
                            location.reload(); // Reload lại trang sau khi nhấn OK
                        });
                    },
                    error: function () {
                        Swal.fire("Lỗi!", "Lỗi khi xác nhận hóa đơn", "error");
                    }
                });
            }
        });
    });
});

// diaChi
document.addEventListener("DOMContentLoaded", function () {
    const citis = document.getElementById("city");
    const districts = document.getElementById("district");
    const wards = document.getElementById("ward");

    axios.get("https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json")
        .then(function (response) {
            let data = response.data;
            renderCity(data);
        })
        .catch(function (error) {
            console.error("Lỗi tải dữ liệu:", error);
        });

    function renderCity(data) {
        for (const x of data) {
            citis.options[citis.options.length] = new Option(x.Name, x.Id);
        }

        citis.onchange = function () {
            districts.length = 1;
            wards.length = 1;

            let selectedCity = data.find(n => n.Id === citis.value);
            if (selectedCity) {
                for (const k of selectedCity.Districts) {
                    districts.options[districts.options.length] = new Option(k.Name, k.Id);
                }
            }
        };

        districts.onchange = function () {
            wards.length = 1;

            let selectedCity = data.find(n => n.Id === citis.value);
            if (selectedCity) {
                let selectedDistrict = selectedCity.Districts.find(n => n.Id === districts.value);
                if (selectedDistrict) {
                    for (const w of selectedDistrict.Wards) {
                        wards.options[wards.options.length] = new Option(w.Name, w.Id);
                    }
                }
            }
        };
    }
});

$(".btn-add-sanPham").click(function () {
    const hoaDonId = $(this).data("id-hd");
    const sanPhamId = $(this).data("id-sp");
    const gia = $(this).data("gia");
    const soLuong = 1;

    $.ajax({
        url: '/hoa-don/addSP',
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


$(".btn-xoa-sanPham").click(function () {
    const sanPhamId = $(this).data("id");

    $.ajax({
        url: '/hoa-don/xoa',
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
        url: '/hoa-don/tangSoLuong',
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
        url: '/hoa-don/giamSoLuong',
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





