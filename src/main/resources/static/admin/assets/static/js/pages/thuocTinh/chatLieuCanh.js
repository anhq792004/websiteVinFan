<!--    THông báo thêm-->
$('#addChatLieuCanh').on('submit', function (e) {
    e.preventDefault(); // Ngăn form thực hiện submit mặc định

    let nameAdd = $('#nameAdd').val();

    $.ajax({
        url: '/chat-lieu-canh/add',
        type: 'POST',
        data: {nameAdd: nameAdd},
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

<!--    lấy thoogn tin để cập nhật-->
$(document).on('click', '.detailModal', function () {
    let id = $(this).data('id');
    $('#id').val(id); // Gắn ID vào input ẩn trong form

    $.ajax({
        url: `/chat-lieu-canh/detail?id=${id}`,
        type: 'GET',
        success: function (response) {
            console.log("Response:", response);
            $('#chatLieuCanhId').val(response.id);
            $('#nameUpdate').val(response.ten);
        },
        error: function (xhr) {
            Swal.fire({
                icon: 'error',
                title: 'Không thể lấy thông tin chi tiết chất liệu cánh.',
                text: xhr.responseText
            });
        }
    });
});
<!--    cap nhật-->
$('#updateKieuQuat').on('submit', function (e) {
    e.preventDefault(); // Ngăn submit mặc định của form

    let id = $('#id').val();
    let nameUpdate = $('#nameUpdate').val();

    $.ajax({
        url: `/chat-lieu-canh/update`,
        type: 'POST',
        data: {
            id: id,
            nameUpdate: nameUpdate
        },
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
                title: xhr.responseText, // Thông báo lỗi từ server
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true
            });
        }
    });
});
