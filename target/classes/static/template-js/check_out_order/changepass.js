async function changePass() {
	if($('#pass-1').val() == "" || $('#pass-2').val() == ""){
		alert('Bạn không được để trống mật khẩu')
	}else if($('#pass-2').val() != $('#pass-1').val()){
		alert('Xác nhận mật khẩu phải trùng với mật khẩu mới')
	}else{
	let method = 'post',
	url = `${api_graduation}changePass`,
	params = {passWord: $('#pass-1').val()}
	data = {}
	let res = await axiosTemplate(method, url, params, data);
	sweatAlert(`Bạn đổi pass thành công`, "success")
	}
}

async function updateUser() {
	 let selectedGender = $("input[name='inlineRadioOptions']:checked").val();
	 let username_form = $('#username_form').val();
	 let firstname_form = $('#firstname_form').val();
	 let lastname_form = $('#lastname_form').val();
	 let email_form = $('#email_form').val();
	 let date_form = Date.parse($('#date_form').val());
	 let city_form = $('#shipCity').val();
	 let district_form = $('#shipState').val();
	 let address_form = $('#address_form').val();	
	 console.log('address =>' , address_form); 
	 let imageSession = sessionStorage.getItem("image");
	 console.log(selectedGender);
	let method = 'post',
	url = `${api_admin}update/${username_form}`,
	params = {	
	}
	data = {
		firstName : firstname_form,	
		lastName : lastname_form,
		gender : selectedGender,
		email : email_form,
		birthday : date_form,
		address : address_form,
		city :city_form,
		avatar:imageSession,
		district: district_form
	}
	let res = await axiosTemplate(method, url, params, data);
	sweatAlert(`Bạn đã cập nhật người dùng thành công`, "success")
	
}
