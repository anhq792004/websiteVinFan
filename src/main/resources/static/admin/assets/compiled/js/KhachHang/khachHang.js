

$(document).ready(function() {
    $('#addKHForm').on('submit', function(event) {
        event.preventDefault(); // Ngăn form submit mặc định

        // Thu thập dữ liệu từ form
        const formData = {
            ten: $('#name').val(),
            email: $('#email').val(),
            soDienThoai: $('#soDienThoai').val(),
            ngaySinh: $('#ngaySinh').val(),
            gioiTinh: $('#gioiTinh').val(),
            tinhThanhPho: $('#city').val(),
            quanHuyen: $('#district').val(),
            xaPhuong: $('#ward').val(),
            soNhaNgoDuong: $('#diaChiCuThe').val()
        };

        // Gửi Ajax request
        $.ajax({
            url: '/khach-hang/add',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formData),
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
});