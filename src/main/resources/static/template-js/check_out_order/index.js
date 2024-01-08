

$(function () {
	reloadedHearchFavourite();
	reloadedAddress();
})

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
$('.city-setting-account').val(res.data.city); 
$('#shipState').val(res.data.district);

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






























