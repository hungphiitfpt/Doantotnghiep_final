// Import các gói và lớp cần thiết
package com.poly.edu.project.graduation.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.edu.project.graduation.model.CartEntity;
import com.poly.edu.project.graduation.model.ShopOrderDetailEntity;
import com.poly.edu.project.graduation.services.ProductServices;

// Đánh dấu lớp này là một Controller trong Spring MVC
@Controller
public class CartController {
	
    // Tự động nạp (autowire) bean ProductServices
	@Autowired
	ProductServices productServices;

	/*
	 * Hiển thị danh sách giỏ hàng từ session
	 */
	@RequestMapping("order-page")
	public String index(Model model, HttpSession session) {
		// Lấy danh sách sản phẩm trong giỏ hàng từ session
		Map<Long, CartEntity> cartItems = (Map<Long, CartEntity>) session.getAttribute("cart");
		
		// Kiểm tra nếu giỏ hàng không rỗng, thì thêm danh sách sản phẩm vào model
		if(cartItems != null) {
			model.addAttribute("cart", cartItems.values());
		} else {
			model.addAttribute("cart", null);
		}
		
		// Thêm thông tin người dùng (userInf) vào model
		model.addAttribute("inf", session.getAttribute("userInf"));
		
		// Thêm thông tin về giá trị bắt đầu của giỏ hàng (cartStarts) vào model
		model.addAttribute("cartStarts", Utils.cartStarts(cartItems, session));
		
		// Trả về trang shop cart
		return "shop-template/shop-cart";
	}
}
