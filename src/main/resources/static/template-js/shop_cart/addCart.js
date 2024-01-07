$(function () {
	// showcart();
	reloadedHearchFavourite();
		  // Call the calculateTotal function initially
		  calculateTotal();
})
var cart = [];
cart = JSON.parse(localStorage.getItem("cart"));
if (cart == null) {
	cart = [];
}

/**
 * api thêm sản phẩm vào giỏ hàng
 * @param {*} productId id của sản phẩm
 * @param {*} productName tên của sản phẩm
 * @param {*} quantity  số lượng sản phẩm mua
 * @param {*} price số tiền của sản phẩm
 */
async function addItemToCart(productId, productName, quantity, price,image,discountPercentage){
	event.preventDefault();
	let method = 'post',
	url = `${host}api/addCart`,
	params = { },
	data = {
		productId : productId,
		productName: productName,
		quantity : quantity, 
		price: price,
		discountPercentage : discountPercentage,
		discountAmount: 0,
		image: image
	};
let res = await axiosTemplate(method, url, params, data);
$('.count-quantity-cart').text(res.data.counter);
$('.total-price-cart').text(formatVND(res.data.amount));
$('.total-quantity-cart').text(formatVND(res.data.counter));
}


function reloadPrice() {
	// Sử dụng jQuery để lấy giá trị từ các thẻ td có class là cart__price
var cartPrices = [];
$('.cart__price').each(function() {
    var priceText = $(this).text().trim(); // Lấy văn bản từ thẻ td và loại bỏ khoảng trắng
    var priceValue = parseFloat(priceText.replace(/[^0-9.-]+/g, '')); // Chuyển đổi văn bản thành giá trị số
    cartPrices.push(priceValue); // Thêm giá trị vào mảng
});
console.log('cartPrices => '+ cartPrices);
}

async function deleteItemCart(productId, r){
	let x = r;
	event.preventDefault();
	let method = 'delete',
	url = `${host}api/deleteCart/${productId}`,
	params = {},
	data = {
	};
let res = await axiosTemplate(method, url, params, data);
$('.count-quantity-cart').text(res.data.counter);
$('.total-price-cart').text(formatVND(res.data.amount));
$('.total-quantity-cart').text(formatVND(res.data.counter));
x.parents('.product').remove();
}
$(document).on('click','.inc.qtybtn',async function () {
	let quantity = $(this).parents('.product').find('.input-quantity-buy-cart').val();
	let productId = $(this).parents('.product').find('.infoAddCart').data('id');
	let productName = $(this).parents('.product').find('.infoAddCart').data('name');
	let price = $(this).parents('.product').find('.infoAddCart').data('price');
	let image = $(this).parents('.product').find('.infoAddCart').data('image');
	if(quantity == 0) {
		$(this).parents('.product').remove();
	}
	let method = 'post',
	url = `${host}api/updateCart`,
	params = { },
	data = {
		productId : productId,
		productName: productName,
		quantity : quantity, 
		price: price,
		discountPercentage : 0,
		discountAmount: 0,
		image: image
	};
let res = await axiosTemplate(method, url, params, data);
$('.count-quantity-cart').text(res.data.counter);
$('.total-price-cart').text(formatMoney(res.data.amount) + " VND");
$('.total-quantity-cart').text(formatVND(res.data.counter));
let money = (parseInt(quantity) * parseInt(price));
$(this).parents('.product').find('.cart__total').text(formatVND(money));
calculateTotal() ;
})

$(document).on('click','.dec.qtybtn',async function () {
    
	let quantity = $(this).parents('.product').find('.input-quantity-buy-cart').val();
	let productId = $(this).parents('.product').find('.infoAddCart').data('id');
	let productName = $(this).parents('.product').find('.infoAddCart').data('name');
	let price = $(this).parents('.product').find('.infoAddCart').data('price');
	let image = $(this).parents('.product').find('.infoAddCart').data('image');
	if(quantity == 0) {
		$(this).parents('.product').remove();
	}
	let method = 'post',
	url = `${host}api/updateCart`,
	params = { },
	data = {
		productId : productId,
		productName: productName,
		quantity : quantity, 
		price: price,
		discountPercentage : 0,
		discountAmount: 0,
		image: image
	};
let res = await axiosTemplate(method, url, params, data);
$('.count-quantity-cart').text(res.data.counter);
$('.total-price-cart').text(formatMoney(res.data.amount) + " VND");
$('.total-quantity-cart').text(formatVND(res.data.counter));
let money = (parseInt(quantity) * parseInt(price));
$(this).parents('.product').find('.cart__total').text(formatVND(money));
calculateTotal() ;
})

$(document).on('input change','.input-quantity-buy-cart',async function () {

	let quantity = $(this).parents('.product').find('.input-quantity-buy-cart').val();
	let productId = $(this).parents('.product').find('.infoAddCart').data('id');
	let productName = $(this).parents('.product').find('.infoAddCart').data('name');
	let price = $(this).parents('.product').find('.infoAddCart').data('price');
	let image = $(this).parents('.product').find('.infoAddCart').data('image');
	if(quantity == 0) {
		$(this).parents('.product').remove();
	}
	let method = 'post',
	url = `${host}api/updateCart`,
	params = { },
	data = {
		productId : productId,
		productName: productName,
		quantity : quantity, 
		price: price,
		discountPercentage : 0,
		discountAmount: 0,
		image: image,
	};
let res = await axiosTemplate(method, url, params, data);
$('.count-quantity-cart').text(res.data.counter);
$('.total-price-cart').text(formatMoney(res.data.amount) + " VND");
$('.total-quantity-cart').text(formatVND(res.data.counter));
let money = (parseInt(quantity) * parseInt(price));
$(this).parents('.product').find('.cart__total').text(formatVND(money));
calculateTotal() ;
})


// function addItemToCart(x) {
// 	let id = x.parents('.product__item').find('.product__item__text .product__price').data('id');
// 	let image = x.parents('.product__item').find('.product__item__pic').data('setbg');
// 	let price = x.parents('.product__item').find('.product__item__text .product__price').data('price');
// 	let name = x.parents('.product__item').find('.product__item__text h6 a').text();
// 	let product = {
// 		id: id, image: image, price: price, name: name, quantity: 1
// 	}
// 	var check = 0;
// 	for (let i = 0; i < cart.length; i++) {
// 		if (product.id == cart[i].id) {
// 			check = 1;
// 			alert("trùng");
// 			cart[i].quantity += 1;
// 			break;
// 		}
// 	}
// 	console.log(cart)
// 	if (check == 0) {
// 		cart.push(product);
// 	}
// 	localStorage.setItem("cart", JSON.stringify(cart));

// }

function showcart() {
	let cartHTML = '';
	for (let i = 0; i < cart.length; i++) {
		let money = formatMoney(`${cart[i].price}`);
		cartHTML  += `<tr>
		<td class="cart__product__item">
			<img src="${cart[i].image}" alt="">
			<div class="cart__product__item__title">
				<h6>${cart[i].name}</h6>
				<div class="rating">
					<i class="fa fa-star"></i>
					<i class="fa fa-star"></i>
					<i class="fa fa-star"></i>
					<i class="fa fa-star"></i>
					<i class="fa fa-star"></i>
				</div>
			</div>
		</td>
		<td class="cart__price">${money}</td>
		<td class="cart__quantity">
			<div class="pro-qty">
				<input type="text" value="${cart[i].quantity}">
			</div>
		</td>
		<td class="cart__total">${money}</td>
		<td class="cart__close"><span class="icon_close"></span></td>
	</tr>`;
		
	}
	$('#table-product-orderPage').html(cartHTML);
	calculateTotal() 
}



async function pay() {
	if($('#table-product-orderPage tr').length == 0) {
		sweatAlert(`Bạn Chưa Có Sản Phẩm`, "warning");
		return false;
	}
	event.preventDefault();
	let method = 'post',
	url = `${host}api/pay`,
	params = { },
	data = {};
let res = await axiosTemplate(method, url, params, data);
console.log(res);
if(res.status == 200) {
	$('#table-product-orderPage tr').remove();
	$('.count-quantity-cart span').text('0');
	$('.total-quantity-cart').text('0');
	$('.total-ship-cart').text('0');
	$('.total-price-cart').text('0 VND');
	sweatAlert(`Đặt Hàng Thành Công`, "success");
}
calculateTotal() ;
}
async function deleteHearth(e) {
   let method = 'post',
	url = `${api_graduation}deleteHearth`,
	params = {IdProduct: e.getAttribute("data-idproduct")}
	data = {}
	let res = await axiosTemplate(method, url, params, data);
	sweatAlert(`Bạn đã xoá sản phẩm trong danh sách yêu thích`, "success")
	var trProduct = e.parentNode.closest('.product');
	trProduct.remove();
	reloadedHearchFavourite();
}

async function reloadedHearchFavourite() {
	let method = 'get',
	 url = `${api_graduation}countQuantity`,
	 params = {}
	 data = {}
	 let res = await axiosTemplate(method, url, params, data);
	 $('.tip_quantity_favourite').text(res.data);
 }

 
     // Function to calculate total amount
	 function calculateTotal() {
		var totalAmount = 0;
  
		// Iterate through each 'cart__total' element
		$('.cart__total').each(function() {
		  // Extract numeric value from the text content and add to totalAmount
		  totalAmount += parseFloat($(this).text().replace(/\D/g, ''));
		});
  
		// Display the total amount
		console.log('Total Amount: ' + totalAmount.toLocaleString('en-US') + ' VND');
		$('.total-price-cart').text(totalAmount.toLocaleString('en-US') + ' VND');
	  }
  
/**
 * api thêm sản phẩm vào giỏ hàng
 * @param {*} productId id của sản phẩm
 * @param {*} productName tên của sản phẩm
 * @param {*} quantity  số lượng sản phẩm mua
 * @param {*} price số tiền của sản phẩm
 */
 async function addItemToCart2(element){
	// Truy cập dữ liệu từ thuvar productId = element.getAttribute('data-productid');
    var productId = element.getAttribute('data-productid');
    var productName = element.getAttribute('data-productname');
    var price = element.getAttribute('data-price');
    var image = element.getAttribute('data-image');
    var discountPercentage = element.getAttribute('data-discountpercentage');

	console.log(productId, productName, price, image, discountPercentage);
   let method = 'post',
   url = `${host}api/addCart`,
   params = { },
   data = {
	   productId : productId,
	   productName: productName,
	   quantity : 1, 
	   price: parseInt(price),
	   discountPercentage : discountPercentage,
	   discountAmount: 0,
	   image: image
   };
let res = await axiosTemplate(method, url, params, data);
$('.count-quantity-cart').text(res.data.counter);
$('.total-price-cart').text(formatVND(res.data.amount));
$('.total-quantity-cart').text(formatVND(res.data.counter));
}

function validateInput(inputElement) {
    var inputValue = parseInt(inputElement.value, 10);

    if (isNaN(inputValue) || inputValue < 0) {
        // Nếu giá trị không phải là số hoặc là số âm
        alert("Vui lòng nhập một số không âm.");
        // Đặt giá trị về giá trị cũ
        inputElement.value = Math.max(inputValue, 0);
    }
}
