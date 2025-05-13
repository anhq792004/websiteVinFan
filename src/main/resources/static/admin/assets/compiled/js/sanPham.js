$(document).ready(function() {
    // Load kiểu quạt for dropdowns
    function loadFanTypes() {
        $.ajax({
            url: "/admin/san-pham/api/kieu-quat",
            type: "GET",
            success: function(data) {
                let options = '<option value="">-- Chọn kiểu quạt --</option>';
                data.forEach(function(item) {
                    options += `<option value="${item.id}">${item.ten}</option>`;
                });
                $("#fanType, #editFanType, #kieuQuat").html(options);

                // Nếu đang chỉnh sửa, chọn kiểu quạt hiện tại
                if ($("#editFanType").data("selected")) {
                    $("#editFanType").val($("#editFanType").data("selected"));
                }
            },
            error: function(error) {
                console.error("Error loading fan types:", error);
                showToast('error', 'Lỗi khi tải danh sách kiểu quạt');
            }
        });
    }

    loadFanTypes();

    
    // Save new product
    $("#saveProductBtn").click(function() {
        const formData = getFormData($("#addProductForm"));

        // Add current date
        formData.ngayTao = new Date().toISOString();

        $.ajax({
            url: "/admin/san-pham/them",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(formData),
            success: function(response) {
                $("#addProductModal").modal("hide");
                showToast('success', response);
                setTimeout(function() {
                    location.reload();
                }, 1000);
            },
            error: function(error) {
                console.error("Error saving product:", error);
                showToast('error', 'Lỗi khi thêm sản phẩm');
            }
        });
    });

    // Edit product - load data
    $(".edit-product-btn").click(function() {
        const productId = $(this).data("id");

        $.ajax({
            url: `/admin/san-pham/get/${productId}`,
            type: "GET",
            success: function(data) {
                $("#editProductId").val(data.id);
                $("#editProductCode").val(data.ma);
                $("#editProductName").val(data.ten);
                $("#editProductDescription").val(data.moTa);

                // Lưu kiểu quạt ID để chọn sau khi load danh sách
                if (data.kieuQuat) {
                    $("#editFanType").data("selected", data.kieuQuat.id);
                    $("#editFanType").val(data.kieuQuat.id);
                } else {
                    $("#editFanType").val('');
                }

                $("#editProductStatus").val(data.trangThai.toString());

                $("#editProductModal").modal("show");
            },
            error: function(error) {
                console.error("Error loading product details:", error);
                showToast('error', 'Lỗi khi tải thông tin sản phẩm');
            }
        });
    });

    // Update product
    $("#updateProductBtn").click(function() {
        const formData = getFormData($("#editProductForm"));

        // Validate form
        if (!validateProductForm(formData)) {
            return;
        }

        $.ajax({
            url: "/admin/san-pham/sua",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(formData),
            success: function(response) {
                $("#editProductModal").modal("hide");
                showToast('success', response);
                setTimeout(function() {
                    location.reload();
                }, 1000);
            },
            error: function(error) {
                console.error("Error updating product:", error);
                showToast('error', 'Lỗi khi cập nhật sản phẩm: ' + (error.responseText || 'Vui lòng thử lại'));
            }
        });
    });

    // Validate product form
    function validateProductForm(formData) {
        if (!formData.ma || formData.ma.trim() === '') {
            showToast('error', 'Vui lòng nhập mã sản phẩm');
            return false;
        }

        if (!formData.ten || formData.ten.trim() === '') {
            showToast('error', 'Vui lòng nhập tên sản phẩm');
            return false;
        }

        if (!formData.kieuQuat || !formData.kieuQuat.id) {
            showToast('error', 'Vui lòng chọn kiểu quạt');
            return false;
        }

        return true;
    }

    // Toggle product status (active/inactive)
    $(".toggle-status-btn").click(function() {
        const productId = $(this).data("id");
        const currentStatus = $(this).data("status");
        const newStatus = !currentStatus;
        const statusText = newStatus ? "Đang kinh doanh" : "ngừng kinh doanh";

        Swal.fire({
            title: `${newStatus ? 'Đang kinh Doanh' : 'Ngừng kinh doanh'} sản phẩm?`,
            text: `Bạn có chắc chắn muốn chuyển thành ${statusText} sản phẩm này?`,
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Xác nhận",
            cancelButtonText: "Hủy"
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    url: `/admin/san-pham/thay-doi-trang-thai/${productId}`,
                    type: "POST",
                    contentType: "application/json",
                    data: JSON.stringify({
                        id: productId,
                        trangThai: newStatus
                    }),
                    success: function(response) {
                        showToast('success', `Sản phẩm đã được cập nhật thành ${statusText} thành công`);
                        setTimeout(function() {
                            location.reload();
                        }, 1000);
                    },
                    error: function(error) {
                        console.error("Error toggling product status:", error);
                        showToast('error', `Lỗi khi ${statusText} sản phẩm`);
                    }
                });
            }
        });
    });

    // Helper function to serialize form data to JSON
    function getFormData($form) {
        let unindexed_array = $form.serializeArray();
        let indexed_array = {};

        $.map(unindexed_array, function(n, i) {
            // Handle nested objects like kieuQuat.id
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

        // Convert boolean values
        if (indexed_array.trangThai) {
            indexed_array.trangThai = indexed_array.trangThai === "true";
        }

        return indexed_array;
    }

    // Toast notification function
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

    // Initialize flatpickr for date inputs if needed
    if ($(".flatpickr").length > 0) {
        $(".flatpickr").flatpickr({
            dateFormat: "d-m-Y",
            locale: "vn"
        });
    }
});

