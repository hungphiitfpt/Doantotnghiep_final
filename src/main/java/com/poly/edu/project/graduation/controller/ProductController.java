package com.poly.edu.project.graduation.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.edu.project.graduation.enums.Title;
import com.poly.edu.project.graduation.model.CartEntity;
import com.poly.edu.project.graduation.model.ShopCategoriesEntity;
import com.poly.edu.project.graduation.model.ShopProductsEntity;
import com.poly.edu.project.graduation.services.CategoryServices;
import com.poly.edu.project.graduation.services.ProductServices;
import com.poly.edu.project.graduation.services.ReviewProductService;

@Controller
public class ProductController {

	@Autowired
	ProductServices productServices;

	@Autowired
	CategoryServices categoryServices;

	@Autowired
	ReviewProductService reviewProductService;

	/*
	 * View trang chủ index
	 */
	@RequestMapping("/index")
	public String index() {
		return "shop-template/index";
	}

	/*
	 * Trả về view của trang sản phẩm
	 */
	@RequestMapping("/shop")
	public String shop(Model model, HttpServletRequest request, HttpSession session,
			@ModelAttribute(name = "idCategory") String idCategory,
			@RequestParam(name = "size", required = false, defaultValue = "12") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
		try {
			// Lấy danh sách sản phẩm từ service dựa trên các tham số như idCategory, size, page, sort
			Page<ShopProductsEntity> listProduct = productServices.findAllProducts(idCategory, PageRequest.of(page, size));
			
			// Lấy danh sách các danh mục sản phẩm từ service
			List<ShopCategoriesEntity> listCategory = categoryServices.findAll();
			
			// Thêm các thuộc tính vào model để truyền đến view
			model.addAttribute("category", listCategory);
			model.addAttribute("product", listProduct);
			
			// Tính toán và thêm thông tin giỏ hàng vào model
			Map<Long, CartEntity> cartItems = (Map<Long, CartEntity>) session.getAttribute("cart");
			model.addAttribute("cartStarts", Utils.cartStarts(cartItems, session));
		} catch (Exception e) {
			// Xử lý nếu có lỗi
			e.printStackTrace();
		}
		return "shop-template/shop";
	}

	/*
	 * Trả về view chi tiết sản phẩm dựa trên id sản phẩm
	 */
	@RequestMapping("/getProductByid/{id}")
	public String getProductById(Model model, @PathVariable Long id) {
		// Lấy thông tin sản phẩm từ service dựa trên id
		ShopProductsEntity product = productServices.findProductById(id);

		// Thêm thông tin sản phẩm và các thuộc tính khác vào model để truyền đến view
		model.addAttribute("product", product);
		model.addAttribute("images", product.getShopProductImagesById());
		System.out.println(product.getQuantityPerUnit());
		System.out.println(product.isDeleted());
		model.addAttribute("review", product.getShopProductReviewsById());
		

		// Trả về view chi tiết sản phẩm
		return "shop-template/product-details";
	}
}
