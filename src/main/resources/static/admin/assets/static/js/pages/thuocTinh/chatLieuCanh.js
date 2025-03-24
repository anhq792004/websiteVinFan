<!--    THông báo thêm-->
$('#addChatLieuCanh').on('submit', function (e) {
    e.preventDefault(); // Ngăn form thực hiện submit mặc định

    const name = $('#name').val();

    $.ajax({
        url: '/chat-lieu-canh/add',
        type: 'POST',
        data: {name: name},
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
