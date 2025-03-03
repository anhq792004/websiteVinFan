let hoaDonId
let btn

$(document).ready(function () {
    $(".btn-xac-nhan").click(function () {
        Swal.fire({
            title: "Xác nhận hóa đơn?",
            text: "Bạn có chắc chắn muốn xác nhận hóa đơn này?",
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



