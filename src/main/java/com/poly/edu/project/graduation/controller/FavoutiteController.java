package com.poly.edu.project.graduation.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.edu.project.graduation.dao.FavouriteRepository;
import com.poly.edu.project.graduation.model.CartEntity;
import com.poly.edu.project.graduation.model.ShopFavoutiteEntity;
import com.poly.edu.project.graduation.services.UserService;

@Controller
//@RestController
public class FavoutiteController {

	@Autowired
	FavouriteRepository favouriteRepository;
	
	@Autowired
	UserService userService;

	
	@RequestMapping("favorite-product")
	public String index(Principal principal,Model model,HttpSession session) {
		if(principal != null) {
			String userName = principal.getName();
			String id = userService.findIdUserByPrincipal(userName);
			List<ShopFavoutiteEntity> sfv = favouriteRepository.getListFavourite(id);
			model.addAttribute("cart", sfv);
			// Thêm thông tin về giá trị bắt đầu của giỏ hàng (cartStarts) vào mode
			// Lấy danh sách sản phẩm từ giỏ hàng được lưu trữ trong session
			Map<Long, CartEntity> cartItems = (Map<Long, CartEntity>) session.getAttribute("cart");
			System.out.println(cartItems);
			model.addAttribute("cartStarts", Utils.cartStarts(cartItems, session));
			// Biến này là tổng số sản phẩm đang được chọn trong giỏ hàng

			return "shop-template/shop-favourite";
		} else {
			return "shop-template/shop";
		}

		
	}
	
}
