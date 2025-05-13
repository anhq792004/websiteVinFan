// Xử lý thêm mới kiểu quạt
$('#addKieuQuat').on('submit', function (e) {
    e.preventDefault(); // Ngăn form thực hiện submit mặc định

    const name = $('#name').val();

    // Kiểm tra tên có trống không
    if (!name || name.trim() === '') {
        Swal.fire({
            toast: true,
            icon: 'error',
            title: 'Tên kiểu quạt không được để trống',
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true
        });
        return;
    }

    $.ajax({
        url: '/kieu-quat/add',
        type: 'POST',
        data: {name: name},
        success: function (response) {
            if (response === "Kiểu quạt thêm mới thành công.") {
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
                title: "Có lỗi xảy ra khi thêm mới kiểu quạt",
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true
            });
        }
    });
});

// Xử lý cập nhật kiểu quạt
$('#updateKieuQuat').on('submit', function (e) {
    e.preventDefault(); // Ngăn form thực hiện submit mặc định

    const id = $('#id').val();
    const name = $('#name').val();

    $.ajax({
        url: '/kieu-quat/update',
        type: 'POST',
        data: {id: id, name: name},
        success: function (response) {
            if (response === "Cập nhật kiểu quạt thành công.") {
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
                title: "Có lỗi xảy ra khi cập nhật kiểu quạt",
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true
            });
        }
    });
});

// Xử lý thay đổi trạng thái
$('.changeStatusKieuQuat').on('click', function() {
    const id = $(this).data('id');

    // Gửi request thay đổi trạng thái
    $.ajax({
        url: '/kieu-quat/change-status',
        type: 'POST',
        data: {id: id},
        success: function(response) {
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
                    window.location.reload();
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
        error: function(xhr) {
            console.error('Error:', xhr);
            Swal.fire({
                toast: true,
                icon: 'error',
                title: "Có lỗi xảy ra khi cập nhật trạng thái",
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true
            });
        }
    });
});
