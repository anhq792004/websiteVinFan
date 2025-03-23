let hoaDonId
let btn
$(document).ready(function () {
    $(".btn-xac-nhan").click(function () {
        btn = $(this);
        hoaDonId = $(this).closest("form").data("id"); // Lấy ID hóa đơn
// Vô hiệu hóa nút
        btn.prop("disabled", true);
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
                        btn.prop("disabled", false);
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

$(document).ready(function () {
    let itemsPerPage = 5; // Số sản phẩm trên mỗi trang
    let items = $(".product-item"); // Lấy danh sách sản phẩm
    let numItems = items.length; // Tổng số sản phẩm
    let totalPages = Math.ceil(numItems / itemsPerPage); // Tổng số trang

    // Ẩn toàn bộ sản phẩm, chỉ hiển thị sản phẩm đầu tiên
    items.hide().slice(0, itemsPerPage).show();

    // Khởi tạo phân trang
    $("#pagination").pagination({
        items: numItems,
        itemsOnPage: itemsPerPage,
        displayedPages: 3, // Số trang hiển thị trên thanh phân trang
        edges: 1, // Số trang hiển thị ở đầu/cuối danh sách
        prevText: "«",
        nextText: "»",
        onPageClick: function (pageNumber) {
            let start = (pageNumber - 1) * itemsPerPage;
            let end = start + itemsPerPage;
            items.hide().slice(start, end).show();
        }
    });
});

// diaChi
document.addEventListener("DOMContentLoaded", function () {
    let citis = document.getElementById("city");
    let districts = document.getElementById("district");
    let wards = document.getElementById("ward");

    axios.get("https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json")
        .then(function (response) {
            let data = response.data;
            renderCity(data);
        })
        .catch(function (error) {
            console.error("Lỗi tải dữ liệu:", error);
        });

    function renderCity(data) {
        for (const x of data) {
            citis.options[citis.options.length] = new Option(x.Name, x.Id);
        }

        citis.onchange = function () {
            districts.length = 1;
            wards.length = 1;

            let selectedCity = data.find(n => n.Id === citis.value);
            if (selectedCity) {
                for (const k of selectedCity.Districts) {
                    districts.options[districts.options.length] = new Option(k.Name, k.Id);
                }
            }
        };

        districts.onchange = function () {
            wards.length = 1;

            let selectedCity = data.find(n => n.Id === citis.value);
            if (selectedCity) {
                let selectedDistrict = selectedCity.Districts.find(n => n.Id === districts.value);
                if (selectedDistrict) {
                    for (const w of selectedDistrict.Wards) {
                        wards.options[wards.options.length] = new Option(w.Name, w.Id);
                    }
                }
            }
        };
    }
});





