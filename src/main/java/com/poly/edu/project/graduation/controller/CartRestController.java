// Import các gói và lớp cần thiết
package com.poly.edu.project.graduation.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.poly.edu.project.graduation.dao.ProductsRepository;
import com.poly.edu.project.graduation.model.CartEntity;
import com.poly.edu.project.graduation.model.ShopProductsEntity;
import com.poly.edu.project.graduation.services.OrderService;
import com.poly.edu.project.graduation.services.ProductServices;

// Đánh dấu lớp này là một RestController trong Spring MVC
@RestController
@SuppressWarnings("unchecked")
public class CartRestController<T> {

    // Tự động nạp (autowire) bean ProductServices
	@Autowired
	ProductServices productServices;
	
    // Tự động nạp (autowire) bean ProductsRepository
	@Autowired
	ProductsRepository productsRepository;

    // Tự động nạp (autowire) bean OrderService
	@Autowired
	OrderService orderService;

    /*
     * API thêm sản phẩm vào giỏ hàng
     */
	@RequestMapping(value = "/api/addCart", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String, String>> viewAdd(@RequestBody CartEntity params, HttpSession session) {
		// Lấy danh sách sản phẩm từ giỏ hàng được lưu trữ trong session
		Map<Long, CartEntity> cartItems = (Map<Long, CartEntity>) session.getAttribute("cart");
		
		// Kiểm tra nếu giỏ hàng không rỗng, thì tạo một giỏ hàng mới
		if (cartItems == null) {
			cartItems = new HashMap<>();
		}
		
		// Lấy ID của sản phẩm
		Long productId = params.getProductId();
		
		// Kiểm tra số lượng tồn kho của sản phẩm
		Long check = productsRepository.checkTonKho(productId);
		session.setAttribute("tonkho", check);
		
		// Kiểm tra sản phẩm đã có trong giỏ hàng hay chưa
		if (cartItems.containsKey(productId) == true) {
			CartEntity shopsdetail = cartItems.get(productId);
			
			// Kiểm tra số lượng sản phẩm có thể thêm vào giỏ hàng
			if(shopsdetail.getQuantity() + 1 > check) {
				shopsdetail.setQuantity(shopsdetail.getQuantity());
			} else {
				shopsdetail.setQuantity(shopsdetail.getQuantity() + 1);
			}
		} else { // Sản phẩm chưa có trong gỏ hàng
			cartItems.put(productId, params);
		}

		// Lưu giỏ hàng vào session
		session.setAttribute("cart", cartItems);
		
		// Biến này là tổng số sản phẩm đang được chọn trong giỏ hàng
		session.getAttribute("countCartItems");
		
	    return new ResponseEntity<>(Utils.cartStarts(cartItems,session), HttpStatus.OK);
	}

    /*
     * API cập nhật giỏ hàng
     */
	@RequestMapping(value = "/api/updateCart", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String, String>> updateCartInput(@RequestBody CartEntity params, HttpSession session) {
		try {
			// Lấy danh sách sản phẩm từ giỏ hàng được lưu trữ trong session
			Map<Long, CartEntity> cartItems = (Map<Long, CartEntity>) session.getAttribute("cart");
			
			// Kiểm tra nếu giỏ hàng không rỗng, thì tạo một giỏ hàng mới
			if (cartItems == null) {
				cartItems = new HashMap<>();
			}
			
			// Lấy ID của sản phẩm
			Long productId = params.getProductId();
			
			// Lấy thông tin tồn kho của sản phẩm
			ShopProductsEntity stock = productsRepository.findById_c(productId);

			// Kiểm tra xem sản phẩm có trong giỏ hàng hay không
			if (cartItems.containsKey(productId) == true) {
				CartEntity shopsdetail = cartItems.get(productId);
				
				// Cập nhật số lượng sản phẩm trong giỏ hàng
				shopsdetail.setQuantity(params.getQuantity());
				
				// Xử lý trường hợp số lượng sản phẩm là 0
				switch (params.getQuantity()) {
				case 0:
					cartItems.remove(productId, shopsdetail);
					session.setAttribute("cart", cartItems);
					break;
				default:
						cartItems.put(productId, params);
					break;
				}
			} else { // Sản phẩm chưa có trong gỏ hàng
				session.setAttribute("cart", cartItems);
			}
			
			// Biến này là tổng số sản phẩm đang được chọn trong giỏ hàng
			session.getAttribute("countCartItems");
			
			return new ResponseEntity<>(Utils.cartStarts(cartItems, session), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}

    /*
     * API xóa sản phẩm khỏi giỏ hàng
     */
	@RequestMapping(value = "/api/deleteCart/{id}", method = RequestMethod.DELETE, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String, String>> deleteCartItem(@PathVariable(value = "id") long id,
			HttpSession session) {
		try {
			// Lấy danh sách sản phẩm từ giỏ hàng được lưu trữ trong session
			Map<Long, CartEntity> cartItems = (Map<Long, CartEntity>) session.getAttribute("cart");
			
			// Kiểm tra xem giỏ hàng có tồn tại và có chứa sản phẩm cần xóa hay không
			if (cartItems != null && cartItems.containsKey(id)) {
				cartItems.remove(id);
				session.setAttribute("cart", cartItems);
			}
			
			return new ResponseEntity<>(Utils.cartStarts(cartItems,session), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/api/pay", method = RequestMethod.POST, produces = {
	        MediaType.APPLICATION_JSON_VALUE })
	public HttpStatus PayCart(HttpSession session) {
	    // Gọi phương thức để tạo đơn hàng từ giỏ hàng và lưu vào cơ sở dữ liệu
	    orderService.CreateOrder((Map<Long, CartEntity>) session.getAttribute("cart"), session);
	    
	    // Xóa thông tin giỏ hàng, số lượng sản phẩm và thông tin người dùng khỏi session sau khi thanh toán
	    session.removeAttribute("cart");
	    session.removeAttribute("countCartItems");
	    session.removeAttribute("userInf");
	    
	    // Trả về mã HTTP OK khi thanh toán thành công
	    return HttpStatus.OK;
	}
}
