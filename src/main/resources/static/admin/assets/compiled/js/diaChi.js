// Lấy danh sách địa chỉ từ Thymeleaf (chuyển từ dạng Thymeleaf sang JSON)

var listDiaChi = /*[[${diaChiList}]]*/ [];

listDiaChi.forEach(function (diaChi, index) {
    // Lấy giá trị từ các trường input dựa theo chỉ số
    var idTinh = document.getElementById('tinhThanhPho' + index).value;
    var idHuyen = document.getElementById('quanHuyen' + index).value;
    var idXa = document.getElementById('xaPhuong' + index).value;

    // Phần tử hiển thị kết quả
    var citis = document.getElementById('city' + index);
    var districts = document.getElementById('district' + index);
    var wards = document.getElementById('ward' + index);

    // Cấu hình Axios để lấy dữ liệu từ GitHub
    var Parameter = {
        url: "https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json",
        method: "GET",
        responseType: "application/json",
    };

    // Gọi API để lấy dữ liệu địa giới hành chính
    axios(Parameter).then(function (result) {
        renderCity(result.data, idTinh, idHuyen, idXa, citis, districts, wards);
    });

    // Hàm xử lý dữ liệu địa giới hành chính
    function renderCity(data, idTinh, idHuyen, idXa, citis, districts, wards) {
        var selectedCity = data.find(city => city.Id == idTinh);
        if (selectedCity) {
            citis.textContent = "Tỉnh: " + selectedCity.Name;

            var selectedDistrict = selectedCity.Districts.find(district => district.Id == idHuyen);
            if (selectedDistrict) {
                districts.textContent = "Quận / Huyện: " + selectedDistrict.Name;

                var selectedWard = selectedDistrict.Wards.find(ward => ward.Id == idXa);
                if (selectedWard) {
                    wards.textContent = "Xã / Phường: " + selectedWard.Name;
                } else {
                    wards.textContent = "Xã / Phường: Không tìm thấy";
                }
            } else {
                districts.textContent = "Quận / Huyện: Không tìm thấy";
                wards.textContent = "Xã / Phường: Không tìm thấy";
            }
        } else {
            citis.textContent = "Tỉnh: Không tìm thấy";
            districts.textContent = "Quận / Huyện: Không tìm thấy";
            wards.textContent = "Xã / Phường: Không tìm thấy";
        }
    }
});
var cities2 = document.getElementById("city2");
var districts2 = document.getElementById("district2");
var wards2 = document.getElementById("ward2");

var parameter = {
    url: "https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json",
    method: "GET",
    responseType: "json",
};

axios(parameter).then(function (result) {
    renderCity2(result.data);
});

function renderCity2(data) {
    for (const city of data) {
        cities2.options[cities2.options.length] = new Option(city.Name, city.Id);
    }
    cities2.onchange = function () {
        districts2.length = 1;
        wards2.length = 1;
        if (this.value !== "") {
            const result = data.find(n => n.Id === this.value);
            if (result) {
                for (const district of result.Districts) {
                    districts2.options[districts2.options.length] = new Option(district.Name, district.Id);
                }
            }
        }
    };

    districts2.onchange = function () {
        wards2.length = 1;
        const selectedCity = data.find(n => n.Id === cities2.value);
        if (this.value !== "") {
            const result = selectedCity.Districts.find(n => n.Id === this.value);
            if (result) {
                for (const ward of result.Wards) {
                    wards2.options[wards2.options.length] = new Option(ward.Name, ward.Id);
                }
            }
        }
    };
}


function disPlayBlook() {
    var p = document.getElementById("magic");
    if (p.style.display == "block") {
        p.style.display = "none"
    } else {
        p.style.display = "block"
    }
}

function diachiMacDinh() {
    var nameButton = document.getElementsByName("checkDiaChiMacDinh")
    // var buttonMacDinh = document.getElementsByName("checkDiaChiMacDinh")
    for (var i = 0; i < nameButton.length; i++) {
        if (nameButton.values() == null) {
            console.log("khong co dc mac dinh")
        } else if (nameButton.values() == true) {
            buttonMacDinh[i].parentElement.style.color = "red"
        } else {
            buttons[i].parentElement.style.color = "blue";
        }
    }
}
