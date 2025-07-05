// if (typeof jQuery === "undefined") {
//     console.error("jQuery is not loaded. Please include jQuery before running this script.");
// } else

//
//         // Xử lý submit form
//         $("#addNhanVienForm").submit(function (event) {
//             event.preventDefault();
//             const $form = $(this);
//             if (!$form[0].checkValidity()) {
//                 $form[0].reportValidity();
//                 return;
//             }
//
//             const formData = new FormData($form[0]);
//             // Validate fields
//             const email = formData.get("email");
//             const soDienThoai = formData.get("soDienThoai");
//             const soCCCD = formData.get("soCCCD");
//             const gioiTinh = formData.get("gioiTinh");
//             const city = formData.get("tinhThanhPho");
//             const district = formData.get("quanHuyen");
//             const ward = formData.get("xaPhuong");
//
//             if (!gioiTinh) {
//                 showToast("error", "Vui lòng chọn giới tính");
//                 return;
//             }
//             if (!city || !district || !ward) {
//                 showToast("error", "Vui lòng chọn đầy đủ địa chỉ (tỉnh/thành phố, quận/huyện, xã/phường)");
//                 return;
//             }
//
//             const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
//             if (!emailRegex.test(email)) {
//                 showToast("error", "Email không hợp lệ");
//                 return;
//             }
//             const phoneRegex = /^0[0-9]{9}$/;
//             if (!phoneRegex.test(soDienThoai)) {
//                 showToast("error", "Số điện thoại không hợp lệ (phải bắt đầu bằng 0 và có 10 chữ số)");
//                 return;
//             }
//             const cccdRegex = /^[0-9]{9,12}$/;
//             if (!cccdRegex.test(soCCCD)) {
//                 showToast("error", "Số CCCD không hợp lệ (phải có 9-12 chữ số)");
//                 return;
//             }
//
//             // Convert FormData to plain object for JSON
//             const formDataObj = {};
//             formData.forEach((value, key) => {
//                 formDataObj[key] = value;
//             });
//             // Add default values if needed
//             formDataObj.trangThai = formDataObj.trangThai || true;
//             formDataObj.matKhau = formDataObj.matKhau || "defaultPassword123"; // Cảnh báo: Xử lý mật khẩu an toàn hơn
//
//             $.ajax({
//                 url: "/admin/nhan-vien/them",
//                 type: "POST",
//                 processData: false, // Không xử lý dữ liệu
//                 contentType: false, // Để server tự phát hiện content type
//                 data: formData, // Gửi FormData trực tiếp
//                 success: function (response) {
//                     $("#addNhanVienModal").modal("hide");
//                     showToast("success", response.message || "Thêm nhân viên thành công");
//
//                     const newRow = {
//                         id: response.id || 'N/A',
//                         anh: response.anh || 'N/A',
//                         ma: response.ma || 'N/A',
//                         hoVaTen: formData.get("hoVaTen") || 'N/A',
//                         email: formData.get("email") || 'N/A',
//                         ngaySinh: formData.get("ngaySinh") || 'N/A',
//                         gioiTinh: formData.get("gioiTinh") === "true" ? 'Nam' : 'Nữ',
//                         diaChi: `${formData.get("tinhThanhPho") || 'N/A'} - ${formData.get("quanHuyen") || 'N/A'}`
//                     };
//                     if ($.fn.DataTable && $.fn.DataTable.isDataTable('table.dataTable-table')) {
//                         $('table.dataTable-table').DataTable().row.add([
//                             newRow.id,
//                             newRow.anh,
//                             newRow.ma,
//                             newRow.hoVaTen,
//                             newRow.email,
//                             newRow.ngaySinh,
//                             newRow.gioiTinh,
//                             newRow.diaChi,
//                             '<button class="btn btn-sm btn-warning">Sửa</button>'
//                         ]).draw();
//                     } else {
//                         $("table.dataTable-table tbody").append(`
//                 <tr>
//                     <td>${newRow.id}</td>
//                     <td>${newRow.anh}</td>
//                     <td>${newRow.ma}</td>
//                     <td>${newRow.hoVaTen}</td>
//                     <td>${newRow.email}</td>
//                     <td>${newRow.ngaySinh}</td>
//                     <td>${newRow.gioiTinh}</td>
//                     <td>${newRow.diaChi}</td>
//                     <td><button class="btn btn-sm btn-warning">Sửa</button></td>
//                 </tr>
//             `);
//                     }
//                 },
//                 error: function (xhr, status, error) {
//                     console.error("Error saving nhan vien", xhr.responseText, status, error);
//                     let errorMessage = "Lỗi khi thêm nhân viên";
//                     if (xhr.responseJSON) {
//                         if (xhr.responseJSON.message) {
//                             errorMessage = xhr.responseJSON.message;
//                         } else if (xhr.responseJSON.errors) {
//                             errorMessage = Object.values(xhr.responseJSON.errors).flat().join(", ");
//                         }
//                     } else if (xhr.responseText) {
//                         errorMessage = xhr.responseText;
//                     } else if (xhr.status) {
//                         errorMessage += ` (HTTP ${xhr.status})`;
//                     }
//                     showToast("error", errorMessage);
//                 }
//             });
//         });
//
//         function showToast(icon, title) {
//             Swal.fire({
//                 toast: true,
//                 icon: icon,
//                 title: title,
//                 position: 'top-end',
//                 showConfirmButton: false,
//                 timer: 3000,
//                 timerProgressBar: true,
//                 didOpen: (toast) => {
//                     toast.addEventListener('mouseenter', Swal.stopTimer);
//                     toast.addEventListener('mouseleave', Swal.resumeTimer);
//                 }
//             });
//         }
// // Xử lý dropdown địa chỉ
//         let citis = document.getElementById("city");
//         let districts = document.getElementById("district");
//         let wards = document.getElementById("ward");
//
//         if (!citis || !districts || !wards) {
//             console.error("Một hoặc nhiều phần tử địa chỉ (city, district, ward) không tồn tại trong DOM!");
//             showToast("error", "Lỗi tải giao diện địa chỉ");
//             return;
//         }
//
//         axios.get("https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json")
//             .then(function (response) {
//                 let data = response.data;
//                 renderCity(data);
//             })
//             .catch(function (error) {
//                 console.error("Lỗi tải dữ liệu:", error);
//                 showToast("error", "Lỗi tải dữ liệu địa chỉ");
//             });
//
//         function renderCity(data) {
//             if (!citis) {
//                 console.error("Phần tử city không tồn tại!");
//                 return;
//             }
//             for (const x of data) {
//                 citis.options[citis.options.length] = new Option(x.Name, x.Id);
//             }
//
//             citis.onchange = function () {
//                 districts.length = 1;
//                 wards.length = 1;
//                 let selectedCity = data.find(n => n.Id === citis.value);
//                 if (selectedCity) {
//                     for (const k of selectedCity.Districts) {
//                         districts.options[districts.options.length] = new Option(k.Name, k.Id);
//                     }
//                 }
//             };
//
//             districts.onchange = function () {
//                 wards.length = 1;
//                 let selectedCity = data.find(n => n.Id === citis.value);
//                 if (selectedCity) {
//                     let selectedDistrict = selectedCity.Districts.find(n => n.Id === districts.value);
//                     if (selectedDistrict) {
//                         for (const w of selectedDistrict.Wards) {
//                             wards.options[wards.options.length] = new Option(w.Name, w.Id);
//                         }
//                     }
//                 }
//             };
//         }
//     });
// }
$(document).ready(function() {
    // let tinhMap = {}; // Lưu ánh xạ ID -> tên tỉnh
    // axios.get('https://api.example.com/tinh').then(response => {
    //     response.data.forEach(tinh => {
    //         tinhMap[tinh.id] = tinh.name;
    //         $('#city').append(`<option value="${tinh.id}">${tinh.name}</option>`);
    //     });
    // });
    // let huyenMap = {}; // Lưu ánh xạ ID -> tên huyện
    // axios.get('https://api.example.com/huyen').then(response => {
    //     response.data.forEach(huyen => {
    //         huyenMap[huyen.id] = tinh.name;
    //         $('#city').append(`<option value="${huyen.id}">${huyen.name}</option>`);
    //     });
    // });
    // let xaMap = {}; // Lưu ánh xạ ID -> tên xã
    // axios.get('https://api.example.com/xa').then(response => {
    //     response.data.forEach(xa => {
    //         xaMap[xa.id] = xa.name;
    //         $('#city').append(`<option value="${xa.id}">${xa.name}</option>`);
    //     });
    // });
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
                    timer: 3000,
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

