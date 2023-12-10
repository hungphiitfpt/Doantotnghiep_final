async function registerUser() {
	validateName();
		if ($('input[class="form-control bad-input"]').length > 0) {
		sweatAlert(`Lỗi thêm người dùng`, "error")
		return false;
	}
	let method = 'post',
		url = `${host}signup`,
		params = {},
		data = {
			userName: $('#username_register_user').val(),
			encrytedPassword: $('#password_register_user').val(),
			firstName: $('#firstname_register_user').val(),
			lastName: $('#lastname_register_user').val(),
			birthday: Date.parse($('#birthday_register_user').val()),
			address: $('#address_register_user').val(),
			country: $('#country_register_user').val(),
			gender: $("input[class='is_gender']:checked").val(),
			email: $("#email_register_user").val(),
			city : $('#address_register_user').val(),
		};
		if($('#address_register_user').val().toUpperCase() == $('#country_register_user').val().toUpperCase()) {
			sweatAlert(`Bạn không được nhập địa chỉ trùng thành phố`, "error")
		} else {
			if ($('input[type="checkbox"]').prop("checked") == true) {
				let res = await axiosTemplate(method, url, params, data);
				if (res.status == 202) {
					sweatAlert(`${res.data}`, "warning");
				} else if(res.status == 200) {
					sweatAlert(`${res.data}`, "success");
				}
			} else {
				alert("bạn chưa đồng ý xác nhận tạo tài khoản")
			}
		}
}

function changeInforUser () {
	
}
function validateName() {
	$('.form-group input[type=text]').each(function(i, v) {
		if (v.hasAttribute("data-validate-required")) {
			if (v.value == '') {
				v.classList.toggle("bad-input");
				v.parentElement.getElementsByClassName("error")[0].innerHTML = `${errorRequired}`;
			} else {
				v.classList.remove("bad-input");
				v.parentElement.getElementsByClassName("error")[0].innerHTML = '';
			}
		}
	})

	$('.form-group input[type=password]').each(function(i, v) {
		if (v.hasAttribute("data-validate-required")) {
			if (v.value == '') {
				v.classList.toggle("bad-input");
				v.parentElement.getElementsByClassName("error")[0].innerHTML = `${errorRequired}`;
			} else {
				v.classList.remove("bad-input");
				v.parentElement.getElementsByClassName("error")[0].innerHTML = '';
			}
		}
	})
}