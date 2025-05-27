// Xử lý thêm mới chế độ gió
$(document).ready(function() {
    $('#addCheDoGio').on('submit', function (e) {
        e.preventDefault(); // Ngăn form thực hiện submit mặc định

        const name = $('#name').val();

        // Kiểm tra tên có trống không
        if (!name || name.trim() === '') {
            Swal.fire({
                toast: true,
                icon: 'error',
                title: 'Tên chế độ gió không được để trống',
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true
            });
            return;
        }

        $.ajax({
            url: '/che-do-gio/add',
            type: 'POST',
            data: {name: name},
            success: function (response) {
                if (response === "Chế độ gió thêm mới thành công.") {
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
                    title: "Có lỗi xảy ra khi thêm mới chế độ gió",
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
        const ten = $(this).closest('tr').find('td:eq(1)').text();
        
        $('#id').val(id);
        $('#updateName').val(ten);
    });

    // Xử lý cập nhật chế độ gió khi nhấn nút Lưu
    $('#btnSaveUpdate').on('click', function () {
        const id = $('#id').val();
        const name = $('#updateName').val();

        // Kiểm tra tên có trống không
        if (!name || name.trim() === '') {
            Swal.fire({
                toast: true,
                icon: 'error',
                title: 'Tên chế độ gió không được để trống',
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true
            });
            return;
        }

        // Gửi request AJAX
        $.ajax({
            url: '/che-do-gio/update',
            type: 'POST',
            data: {id: id, name: name},
            success: function (response) {
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
                    location.reload();
                });
            },
            error: function (xhr) {
                Swal.fire({
                    toast: true,
                    icon: 'error',
                    title: "Có lỗi xảy ra khi cập nhật chế độ gió",
                    position: 'top-end',
                    showConfirmButton: false,
                    timer: 3000,
                    timerProgressBar: true
                });
            }
        });
    });

    // Xử lý form submit để ngăn chặn hành vi mặc định
    $('#updateCheDoGio').on('submit', function (e) {
        e.preventDefault(); // Ngăn form thực hiện submit mặc định
        e.stopPropagation(); // Dừng sự kiện lan truyền lên các phần tử cha
        return false; // Ngăn chặn form submit mặc định
    });

    // Xử lý thay đổi trạng thái chế độ gió
    $('.changeStatusCheDoGio').on('click', function () {
        const id = $(this).data('id');

        $.ajax({
            url: '/che-do-gio/change-status',
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
                    title: "Có lỗi xảy ra khi cập nhật trạng thái chế độ gió",
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
        window.location.href = `/che-do-gio/index?name=${name}&trangThai=${trangThai}`;
    });
});