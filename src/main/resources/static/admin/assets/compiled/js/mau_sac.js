// Xử lý thêm mới màu sắc
$('#addMauSac').on('submit', function (e) {
    e.preventDefault(); // Ngăn form thực hiện submit mặc định

    const name = $('#name').val();

    // Kiểm tra tên có trống không
    if (!name || name.trim() === '') {
        Swal.fire({
            toast: true,
            icon: 'error',
            title: 'Tên màu sắc không được để trống',
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true
        });
        return;
    }

    $.ajax({
        url: '/mau-sac/add',
        type: 'POST',
        data: {name: name},
        success: function (response) {
            if (response === "Màu sắc thêm mới thành công.") {
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
                title: "Có lỗi xảy ra khi thêm mới màu sắc",
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true
            });
        }
    });
});

// Xử lý hiển thị thông tin khi click vào nút chỉnh sửa
$('.hienThiThongTinModal').on('click', function () {
    const id = $(this).data('id');
    $.ajax({
        url: '/mau-sac/find-by-id',
        type: 'GET',
        data: {id: id},
        success: function (response) {
            $('#modalChinhSua #id').val(response.id);
            $('#modalChinhSua #updateName').val(response.ten);
        },
        error: function (xhr) {
            Swal.fire({
                toast: true,
                icon: 'error',
                title: "Có lỗi xảy ra khi lấy thông tin màu sắc",
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true
            });
        }
    });
});

// Xử lý cập nhật màu sắc
$('#updateMauSac').on('submit', function (e) {
    e.preventDefault(); // Ngăn form thực hiện submit mặc định

    const id = $('#modalChinhSua #id').val();
    const name = $('#modalChinhSua #updateName').val();

    // Kiểm tra tên có trống không
    if (!name || name.trim() === '') {
        Swal.fire({
            toast: true,
            icon: 'error',
            title: 'Tên màu sắc không được để trống',
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true
        });
        return;
    }

    // Gửi request AJAX
    $.ajax({
        url: '/mau-sac/update',
        type: 'POST',
        data: {id: id, name: name},
        success: function (response) {
            if (response === "Cập nhật màu sắc thành công.") {
                $('#modalChinhSua').modal('hide');
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
        error: function (xhr) {
            Swal.fire({
                toast: true,
                icon: 'error',
                title: "Có lỗi xảy ra khi cập nhật màu sắc",
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true
            });
        }
    });
});

// Xử lý thay đổi trạng thái màu sắc
$('.changeStatusMauSac').on('click', function () {
    const id = $(this).data('id');

    $.ajax({
        url: '/mau-sac/change-status',
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
                title: "Có lỗi xảy ra khi cập nhật trạng thái màu sắc",
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true
            });
        }
    });
});

// Xử lý tìm kiếm
$('#searchInput, #trangThai').on('change', function(e) {
    e.preventDefault(); // Ngăn chặn sự kiện mặc định

    const name = $('#searchInput').val();
    const trangThai = $('#trangThai').val();

    // Submit form tìm kiếm
    window.location.href = `/mau-sac/index?name=${name}&status=${trangThai}`;
});