// Xử lý thêm mới chất liệu khung
$('#addChatLieuKhung').on('submit', function (e) {
    e.preventDefault(); // Ngăn form thực hiện submit mặc định

    const name = $('#name').val();

    // Kiểm tra tên có trống không
    if (!name || name.trim() === '') {
        Swal.fire({
            toast: true,
            icon: 'error',
            title: 'Tên chất liệu khung không được để trống',
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true
        });
        return;
    }

    $.ajax({
        url: '/chat-lieu-khung/add',
        type: 'POST',
        data: {name: name},
        success: function (response) {
            if (response === "Chất liệu khung thêm mới thành công.") {
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
                title: "Có lỗi xảy ra khi thêm mới chất liệu khung",
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true
            });
        }
    });
});
// Xử lý thay đổi trạng thái chất liệu khung
$('.changeStatusChatLieuKhung').on('click', function () {
    const id = $(this).data('id');

    $.ajax({
        url: '/chat-lieu-khung/change-status',
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
                title: "Có lỗi xảy ra khi cập nhật trạng thái chất liệu khung",
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true
            });
        }
    });
});

// Lấy thông tin để cập nhật
$('.hienThiThongTinModal').on('click', function () {
    let id = $(this).data('id');
    const ten = $(this).closest('tr').find('td:eq(1)').text();
    
    // Điền thông tin trực tiếp vào form
    $('#id').val(id);
    $('#nameUpdate').val(ten);
});

// Cập nhật
$('#updateChatLieuKhung').on('submit', function (e) {
    e.preventDefault();

    let id = $('#id').val();
    let nameUpdate = $('#nameUpdate').val();
    
    // Kiểm tra tên có trống không
    if (!nameUpdate || nameUpdate.trim() === '') {
        Swal.fire({
            toast: true,
            icon: 'error',
            title: 'Tên chất liệu khung không được để trống',
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true
        });
        return;
    }

    $.ajax({
        url: '/chat-lieu-khung/update',
        type: 'POST',
        data: {
            id: id,
            nameUpdate: nameUpdate
        },
        success: function (response){
            Swal.fire({
                toast: true,
                icon: 'success',
                title: response,
                position: 'top-end',
                showConfirmButton: false,
                timer: 1000,
                timerProgressBar: true
            })
            .then(() => {
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


