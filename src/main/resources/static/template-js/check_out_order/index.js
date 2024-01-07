

$(function () {
	getUser();
	reloadedAddress();
	reloadedHearchFavourite();

})

async function getUser() {
	let cityHtml = ``;
	let stateHtml = ``;
  try {
    const res =  await axios.get('https://provinces.open-api.vn/api/?depth=2');
    for (let i = 0; i < res.data.length; i++) {
	cityHtml += `<option data-code="${res.data[i].code}" value="${res.data[i].name}">${res.data[i].name}</option>`;
	}		
	const res2 =  await axios.get(`https://provinces.open-api.vn/api/p/1?depth=2`);
	for (let i = 0; i < res2.data.districts.length; i++) {
		stateHtml += `<option value = "${res2.data.districts[i].name}">${res2.data.districts[i].name}</option>`;
	}
    $('#shipCity').html(cityHtml);
	$('#shipState').html(stateHtml);
  } catch (error) {
    console.error(error);
  }
}

async function reloadedHearchFavourite() {
	let method = 'get',
		url = `${api_graduation}countQuantity`,
		params = {}
	data = {}
	let res =  await axiosTemplate(method, url, params, data);
	console.log(res);
	$('.tip_quantity_favourite').text(res.data);
}

async function reloadedAddress() {
	let method = 'get',
		url = `${api_graduation}populateInfo`,
		params = {}
	data = {}
	let res =  await axiosTemplate(method, url, params, data);
	console.log(res);
$('#address-user-checkout').text(res.data.address); 
}

$(document).on('change','#shipCity',async function() {
	var stateHtml = ``;
	let idCity = $(this).find('option:selected').data('code');
	const res = await axios.get(`https://provinces.open-api.vn/api/p/${idCity}?depth=2`);
	console.log(res)
	for (let i = 0; i < res.data.districts.length; i++) {
		stateHtml += `<option value = "${res.data.districts[i].name}">${res.data.districts[i].name}</option>`;
	}
	console.log(stateHtml);
	$('#shipState').html(stateHtml);
})
































