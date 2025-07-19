$(document).ready(function() {

    $('#addNhanVienForm').on('submit', function(event) {
        event.preventDefault();

        const formData = {
            ten: $('#ten').val(),
            cccd: $('#cccd').val(),
            email: $('#email').val(),
            soDienThoai: $('#sdt').val(),
            ngaySinh: $('#ngaySinh').val(),
            gioiTinh: $('input[name="gioiTinh"]:checked').val(),
            tinhThanhPho: $('#city').val(),
            quanHuyen: $('#district').val(),
            xaPhuong: $('#ward').val(),
            soNhaNgoDuong: $('#diaChiCuThe').val()
        };

        $.ajax({
            url: '/admin/nhan-vien/them',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                Swal.fire({
                    toast: true,
                    icon: 'success',
                    title: response.message || 'Thêm nhân viên thành công',
                    position: 'top-end',
                    showConfirmButton: false,
                    timer: 1000,
                    timerProgressBar: true
                }).then(() => {
                    window.location.href = '/admin/nhan-vien/index';
                });
            },
            error: function(xhr) {
                Swal.fire({
                    toast: true,
                    icon: 'error',
                    title: xhr.responseText || 'Lỗi khi thêm nhân viên',
                    position: 'top-end',
                    showConfirmButton: false,
                    timer: 3000,
                    timerProgressBar: true
                });
            }
        });
    });
});


$('.changeStatusNhanVien').on('click', function () {
    const id = $(this).data('id');

    $.ajax({
        url: '/admin/nhan-vien/change-status',
        type: 'POST',
        data: {id: id},
        success: function (response) {
            if (response === "Cập nhật trạng thái thành công.") {
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
            } else {
                Swal.fire({
                    toast: true,
                    icon: 'error',
                    title: response,
                    position: 'top-end',
                    showConfirmButton: false,
                    timer: 3000,
                    timerProgressBar: true
                });
            }
        },
        error: function (xhr) {
            Swal.fire({
                toast: true,
                icon: 'error',
                title: "Có lỗi xảy ra khi cập nhật trạng thái nhân viên",
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true
            });
        }
    });
});


