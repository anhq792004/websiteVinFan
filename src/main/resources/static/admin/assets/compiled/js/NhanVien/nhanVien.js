// Đảm bảo jQuery được tải trước khi chạy mã
if (typeof jQuery === "undefined") {
    console.error("jQuery is not loaded. Please include jQuery before running this script.");
} else {
    $(document).ready(function () {
        // Load chức vụ
        function loadChucVu() {
            $.ajax({
                url: "/admin/nhan-vien/api/chuc-vu",
                type: "GET",
                success: function (data) {
                    let options = '<option value="">-- Chọn chức vụ --</option>';
                    data.forEach(function (item) {
                        options += `<option value="${item.id}">${item.viTri}</option>`;
                    });
                    $("#chucVu").html(options);
                    if ($("#chucVu").data("selected")) {
                        $("#chucVu").val($("#chucVu").data("selected"));
                    }
                },
                error: function (error) {
                    console.error("Error loading chức vụ", error);
                    showToast('error', "Lỗi khi tải danh sách chức vụ");
                }
            });
        }
        loadChucVu();

        // Thêm nhân viên mới
        $("#saveNhanVienBtn").click(function () {
            const $form = $("#addNhanVienForm");
            if (!$form[0].checkValidity()) {
                $form[0].reportValidity();
                return;
            }
            const formData = getFormData($form);

            // Kiểm tra dữ liệu trước khi gửi
            if (!formData.gioiTinh) {
                showToast("error", "Vui lòng chọn giới tính");
                return;
            }
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(formData.email)) {
                showToast("error", "Email không hợp lệ");
                return;
            }
            const phoneRegex = /^0[0-9]{9}$/;
            if (!phoneRegex.test(formData.soDienThoai)) {
                showToast("error", "Số điện thoại không hợp lệ (phải bắt đầu bằng 0 và có 10 chữ số)");
                return;
            }
            const cccdRegex = /^[0-9]{9,12}$/;
            if (!cccdRegex.test(formData.soCCCD)) {
                showToast("error", "Số CCCD không hợp lệ (phải có 9-12 chữ số)");
                return;
            }

            // Thêm các trường bắt buộc mà server có thể yêu cầu
            formData.chucVu = formData.chucVu || { id: 1, viTri: "Nhân viên" };
            formData.trangThai = formData.trangThai !== undefined ? formData.trangThai : true;
            formData.matKhau = formData.matKhau || "defaultPassword123";

            $.ajax({
                url: "/admin/nhan-vien/them",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(formData),
                success: function (response) {
                    $("#addNhanVienModal").modal("hide");
                    let message = "Thêm nhân viên thành công";
                    if (response && typeof response === "object") {
                        message = response.message || message;
                    }
                    showToast("success", message);

                    const newRow = `
                        <tr>
                            <td>${response && response.stt ? response.stt : 'N/A'}</td>
                            <td>${response && response.anh ? response.anh : 'N/A'}</td>
                            <td>${response && response.ma ? response.ma : 'N/A'}</td>
                            <td>${formData.hoVaTen || 'N/A'}</td>
                            <td>${formData.email || 'N/A'}</td>
                            <td>${formData.ngaySinh || 'N/A'}</td>
                            <td>${formData.gioiTinh === true ? 'Nam' : 'Nữ'}</td>
                            <td>${formData.chucVu && formData.chucVu.viTri ? formData.chucVu.viTri : 'N/A'}</td>
                            <td>${formData.tinhThanhPho || 'N/A'} - ${formData.quanHuyen || 'N/A'}</td>
                            <td><button class="btn btn-sm btn-warning">Sửa</button></td>
                        </tr>`;
                    $("table.dataTable-table tbody").append(newRow);
                },
                error: function (xhr, status, error) {
                    console.error("Error saving nhan vien", xhr, status, error);
                    let errorMessage = "Lỗi khi thêm nhân viên";
                    if (xhr.responseJSON) {
                        if (xhr.responseJSON.message) {
                            errorMessage = xhr.responseJSON.message;
                        } else if (xhr.responseJSON.errors) {
                            errorMessage = Object.values(xhr.responseJSON.errors).join(", ");
                        }
                    } else if (xhr.responseText) {
                        errorMessage = xhr.responseText;
                    } else if (xhr.status) {
                        errorMessage += ` (HTTP ${xhr.status})`;
                    }
                    showToast("error", errorMessage);
                }
            });
        });

        function getFormData($form) {
            let unindexed_array = $form.serializeArray();
            let indexed_array = {};

            $.map(unindexed_array, function(n, i) {
                if (n['name'].includes('.')) {
                    const parts = n['name'].split('.');
                    if (!indexed_array[parts[0]]) {
                        indexed_array[parts[0]] = {};
                    }
                    indexed_array[parts[0]][parts[1]] = n['value'];
                } else {
                    indexed_array[n['name']] = n['value'];
                }
            });

            // Chuyển đổi gioiTinh từ String sang Boolean
            const gioiTinhValue = $form.find('input[name="gioiTinh"]:checked').val();
            if (gioiTinhValue) {
                indexed_array['gioiTinh'] = gioiTinhValue === "Nam" ? true : false; // true: Nam, false: Nữ
            }

            return indexed_array;
        }

        function showToast(icon, title) {
            Swal.fire({
                toast: true,
                icon: icon,
                title: title,
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true,
                didOpen: (toast) => {
                    toast.addEventListener('mouseenter', Swal.stopTimer);
                    toast.addEventListener('mouseleave', Swal.resumeTimer);
                }
            });
        }

    }


    );
    document.addEventListener("DOMContentLoaded", function () {
            let citis = document.getElementById("city");
            let districts = document.getElementById("district");
            let wards = document.getElementById("ward");

            axios.get("https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json")
                .then(function (response) {
                    let
                        data = response.data;
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
        }
    );
}