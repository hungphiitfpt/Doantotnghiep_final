
$(function() {
	loadAlllProductShop();
	loadAlllCategoryShop();
})

// mã javascript để hiển thị sản phẩm ra giao diện
async function loadAlllProductShop() {

	/*
	keyWord : giá trị người dùng gõ trên ô search
	*/
	let keyWord = $('#input-search-product-keyword').val();
	// truyền method get, vì api viết metheo get
	let method = 'get',
		// url là tên api 
		url = `${api_graduation}findListProductExist`,
		// params là tham số truyền vào
		params = { keyword: keyWord, size: 9, page: 0 },
		data = {};
	// biến hứng data khi gọi api, api sẽ trả về dữ liệu json nạp vào biến RES ở dưới
	let res = await axiosTemplate(method, url, params, data);
	// Funtion vẽ dữ liệu ra bên giao diện
	drawDataProductShop(res);
}

async function loadAlllCategoryShop() {
	let categoryHTML = ``;
	let method = 'get',
		url = `${api_graduation}category/getAllCategory`,
		params = {},
		data = {
		};
	let res = await axiosTemplate(method, url, params, data);
	console.log(res);
	for (let i = 0; i < res.data.length; i++) {
		categoryHTML += `<li class="filter-category-item row mb-3">
		<img class="image-sidebar-category" src="${res.data[i].image}" alt="" />
		<label class="title-filter-category filter-product-by-categoryId" data-id="${res.data[i].id}" value="${res.data[i].id}">${res.data[i].categoryName}</label>
	</li>`;
	}
	$('#list-category-shop').html(categoryHTML);

}

$(document).on('click', '.page-link', async function() {
	event.preventDefault();
	let page = $(this).text();
	let idCategory = $('.active-btn-filter-category').data('id');
	let priceStart = $('#minamount').val();
	let priceEnd = $('#maxamount').val();
	let method = 'get',
		url = `${api_graduation}getProducts`,
		params = {
			keyword: $('#input-search-list-product').val(),
			page: page,
			size: 9,
			idCategory: idCategory,
			priceStart: priceStart,
			priceEnd: priceEnd
		},
		data = {
		};
	let res = await axiosTemplate(method, url, params, data);
	drawDataProductShop(res);
	/*
		let currentPage = localStorage.getItem('currentPage');
	*/
	$(`.page-link[value='${page}']`).addClass('pagination__number pagination__number--active')
	sweatAlert(`Bạn đang ở trang thứ ${page}`, "success")
})

$(document).on('click', '.filter-product-by-categoryId', async function() {
	var idCategoryOld = localStorage.getItem("idCategoryOld");
	let idCategory = $(this).data('id');
	let page = localStorage.getItem("currentPage");
	localStorage.setItem('idCategoryOld', idCategory);
	let priceStart = $('#minamount').val();
	let priceEnd = $('#maxamount').val();
	$('.filter-product-by-categoryId').removeClass('active-btn-filter-category')
	$('.overlapTwo').removeClass('active-btn-filter-category')
	$(this).addClass('active-btn-filter-category')
	$('.overlapTwo').removeClass('bg-active')
	$(this).find('.overlapTwo').addClass('bg-active');
	localStorage.setItem('currentPage', page);
	let parameter = {};
	if (idCategoryOld == idCategory) {
		localStorage.removeItem('idCategoryOld');
		$('.filter-product-by-categoryId').removeClass('active-btn-filter-category')
		parameter = {
			page: 0,
			size: 9,
			keyword: $('#input-search-list-product').val(),
			priceStart: priceStart,
			priceEnd: priceEnd
		}
	}
	else {
		parameter = {
			page: 0,
			size: 9,
			keyword: $('#input-search-list-product').val(),
			idCategory: idCategory,
			priceStart: priceStart,
			priceEnd: priceEnd
		}
	}
	let method = 'get',
		url = `${api_graduation}findListProductExist`,
		params = parameter,
		data = {};
	let res = await axiosTemplate(method, url, params, data);
	drawDataProductShop(res);
	let currentPage = localStorage.getItem('currentPage');
	$(`.button-panigation-manager-product[value='${currentPage}']`).addClass('active')

})

$(document).on('click', '.filter-product-by-price', async function() {
	let page = localStorage.getItem("currentPage");
	let idCategory = $('.active-btn-filter-category').data('id');
	console.log(idCategory)
	let priceStart = $('#minamount').val();
	let priceEnd = $('#maxamount').val();
	localStorage.setItem('currentPage', page);
	let method = 'get',
		url = `${api_graduation}filterDataProduct`,
		params = {
			page: 0,
			size: 9,
			// idCategory: idCategory, 
			priceStart: priceStart,
			priceEnd: priceEnd
		},
		data = {};
	let res = await axiosTemplate(method, url, params, data);
	drawDataProductShop(res);
	let currentPage = localStorage.getItem('currentPage');
	$(`.button-panigation-manager-product[value='${currentPage}']`).addClass('active')
})



// function vẽ dữ liệu ra bên giao diện
async function drawDataProductShop(res) {
	let productHTML = ``, pagination = ``, image = ``, labelDiscount = ``, buttonAddCart = ``, moneyAfterDiscount = ``;
	// vòng lặp for để lặp tất cả sản phẩm khi api trả về
	for (let i = 0; i < res.data.content.length; i++) {
		sale_time_still = startCountdownAndReturn(res.data.content[i].saletime);
		console.log(sale_time_still);
		if(res.data.content[i].discountinued > 0) {
			html_sale_time = `<div class="sale_time">${sale_time_still}</div>`;
		}else {
			html_sale_time = '';
		}

		// biến số tiền
		formatmoney = (res.data.content[i].listPrice * ((100 - res.data.content[i].discountinued) / 100));
		// biến format định dạng tiền
		moneyAfterDiscount = formatVND(formatmoney);
		// biến hình ảnh sản phẩm
		image = res.data.content[i].image;
		// nếu sản phẩm bị tắt hiển thị hết hàng
		if (res.data.content[i].deleted == true) {
			labelDiscount = `<div class="label stockout">Hết hàng</div>`;
			buttonAddCart = ``;
			// nếu số lượng sản phẩm <= 0 cũng hiển thị hết hàng
		} else if (res.data.content[i].quantityPerUnit <= 0) {
			labelDiscount = `<div class="label stockout">Hết hàng</div>`;
			buttonAddCart = ``;
		}
		// số lượng sản phẩm thì mới hiển thị giá đang giảm, và nút cho mua hàng
		else {
			labelDiscount = `<div class="label sale">${res.data.content[i].discountinued}%</div>`;
			buttonAddCart = `<li onclick="addItemToCart(${res.data.content[i].id},'${res.data.content[i].productName}',1,${formatmoney},'${res.data.content[i].image}','${res.data.content[i].discountinued}')"><a href="#"><span class="icon_bag_alt"></span></a></li>`;
		}
		// biến để vẽ 1 cục sản phẩm, trả về mã HTML
		productHTML += `<div class="col-lg-4 col-md-6">
        <div class="product__item">
            <div class="product__item__pic set-bg" data-setbg="${image}" style="background-image: url(&quot;${image}&quot;);"> 
            	${html_sale_time}
				${labelDiscount}
                <ul class="product__hover">
                    <li><a href="${res.data.content[i].image}" class="image-popup"><span
                            class="arrow_expand"></span></a></li>
                    <li data-nameproduct="${res.data.content[i].productName}" data-idProduct="${res.data.content[i].id}" onclick="drop_heart(this)"><a href="#"><span class="icon_heart_alt"></span></a></li>
                    ${buttonAddCart}
                </ul>
            </div>
            <div class="product__item__text">
                <h6>
                    <a href="${host}getProductByid/${res.data.content[i].id}">${res.data.content[i].productName}</a>
                </h6>
                <div class="rating">
                    <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i
                        class="fa fa-star"></i> <i class="fa fa-star"></i> <i
                        class="fa fa-star"></i>
                </div>
                <div class="product__price" data-id="${res.data.content[i].id}" data-price="${res.data.content[i].listPrice}"> ${moneyAfterDiscount}</div>
            </div>
        </div>
    </div>`
	}
	// VÒNG LẶP FOR ĐỂ VẼ RA CÁC NÚT PHÂN TRANG
	for (let i = 0; i < res.data.totalPages; i++) {
		pagination += ` <li class="page-item"  >
					<a class="page-link" value="${i}" href="#" tabindex="-1">${i}</a>
				</li> `
	}
	// mã dùng đễ dán những xử lý ở trên qua file html
	$('#list-product-shop').html(productHTML);
	// mã dùng đễ dán những xử lý ở trên qua file html
	$('#pagination__option').html(pagination);

}

async function searchProductByKeyword() {
	let keyword = $('#input-search-list-product').val();
	let category = $('.active-btn-filter-category').data('id');
	let method = 'get',
		url = `${api_graduation}findListProductExist`,
		params = {
			page: 0,
			size: 9,
			keyword: keyword,
			idCategory: category
		},
		data = {};
	let res = await axiosTemplate(method, url, params, data);
	drawDataProductShop(res);
	let currentPage = localStorage.getItem('currentPage');
	$(`.button-panigation-manager-product[value='${currentPage}']`).addClass('active')
}

async function drop_heart(e) {
	let method = 'post',
		url = `${api_graduation}createHearthForUser`,
		params = { IdProduct: e.getAttribute("data-idproduct") }
	data = {}
	let res = await axiosTemplate(method, url, params, data);
	sweatAlert(`Bạn đã thêm sản phẩm vào danh sách yêu thích`, "success");
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


var timeRemainingValue = '';

// Hàm đếm ngược và trả về giá trị time_remaining
function startCountdownAndReturn(time) {
       var saleTime = moment(time);
    var currentTime = moment();

    // Tính toán thời gian còn lại trong giây
    var timeDifferenceInSeconds = saleTime.diff(currentTime, 'seconds');

    if (timeDifferenceInSeconds <= 0) {
        console.log("Sale time has passed!");
        // Nếu thời gian đã qua, trả về giá trị cuối cùng và kết thúc đệ quy
        return timeRemainingValue;
    } else {
        // Tính toán số ngày
        var days = Math.floor(timeDifferenceInSeconds / (24 * 3600));
        var remainingSeconds = timeDifferenceInSeconds % (24 * 3600);

        // Tính toán giờ, phút và giây
        var hours = Math.floor(remainingSeconds / 3600);
        remainingSeconds %= 3600;
        var minutes = Math.floor(remainingSeconds / 60);
        var seconds = remainingSeconds % 60;

        // Format chuỗi hiển thị
        if (days > 0) {
            timeRemainingValue = "Còn giảm trong " + days + " ngày " + hours + " giờ " + minutes + " phút " + seconds + " giây";
        } else {
            timeRemainingValue = "Còn giảm trong " + hours + " giờ " + minutes + " phút " + seconds + " giây";
        }

        // In giá trị mỗi lần tính toán
        console.log(timeRemainingValue);

        // Gọi lại hàm sau 1 giây bằng cách sử dụng setTimeout
        setTimeout(function() {
            startCountdownAndReturn(time);
        }, 1000);

        // Trả về giá trị hiện tại để sử dụng nếu cần
        return timeRemainingValue;
    }
}


