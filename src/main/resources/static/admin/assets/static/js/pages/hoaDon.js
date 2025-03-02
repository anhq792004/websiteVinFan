$('#xacNhan').on('submit', function (e) {
    e.preventDefault(); // Ngăn form thực hiện submit mặc định

    const id = $(this).find('input[name="id"]').val(); // Lấy ID từ input hidden

    $.ajax({
        url: '/hoa-don/xac-nhan',
        type: 'POST',
        data: { id: id }, // Gửi ID của hóa đơn
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
                // Chuyển hướng đến trang chi tiết hóa đơn sau khi xác nhận thành công
                window.location.href = "/hoa-don/detail?id=" + id;
            });
        },
        error: function (xhr) {
            Swal.fire({
                toast: true,
                icon: 'error',
                title: xhr.responseText, // Thông báo lỗi từ server
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true
            });
        }
    });
});



//
$('#hoanThanh').on('submit', function (e) {
    e.preventDefault(); // Ngăn form thực hiện submit mặc định

    const hoanThanh = $('#hoanThanh').val();

    $.ajax({
        url: '/hoa-don/hoan-thanh',
        type: 'POST',
        data: {hoanThanh: hoanThanh},
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
                timer: 3000,
                timerProgressBar: true
            });
        }
    });
});
