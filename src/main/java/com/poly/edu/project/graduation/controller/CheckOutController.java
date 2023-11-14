// Import các gói và lớp cần thiết
package com.poly.edu.project.graduation.controller;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poly.edu.project.graduation.model.CartEntity;
import com.poly.edu.project.graduation.model.ShopOrdersEntity;
import com.poly.edu.project.graduation.services.UserService;

// Đánh dấu lớp này là một Controller trong Spring MVC
@Controller
public class CheckOutController {
	
    // Tự động nạp (autowire) bean UserService
	@Autowired
	UserService service;

    /*
     * Xử lý yêu cầu GET đến trang thanh toán (checkout)
     */
	@RequestMapping(value = {"/checkout", "/addInfoUser"}, method = RequestMethod.GET)
	public String index(Model model, HttpSession session, Principal principal) throws Exception {
		try {
			// Kiểm tra xem người dùng có đăng nhập hay không
			if (principal.getName() == null) {
				return "shop-template/shop";
			} else {
				// Lấy danh sách sản phẩm từ giỏ hàng được lưu trữ trong session
				Map<Long, CartEntity> cartItems = (Map<Long, CartEntity>) session.getAttribute("cart");
				
				// Lấy ID của người dùng từ Principal (đối tượng chứa thông tin đăng nhập)
				String id = service.findIdUserByPrincipal(principal.getName());
				session.setAttribute("idUsser", id);
				
				// Kiểm tra nếu giỏ hàng không rỗng, thì thêm danh sách sản phẩm vào model
				if (cartItems != null) {
					model.addAttribute("cart", cartItems.values());
				} else {
					model.addAttribute("cart", null);
				}
				
				// Thêm thông tin về giá trị bắt đầu của giỏ hàng (cartStarts) vào model
				model.addAttribute("cartStarts", Utils.cartStarts(cartItems, session));
				
				// Chuyển hướng đến trang thanh toán (checkout)
				return "shop-template/checkout";
			}
		} catch (NullPointerException ex) {
			// Xử lý ngoại lệ khi không có người dùng đăng nhập
			System.out.println(ex);
			model.addAttribute("messageBuyCart", "Bạn phải đăng nhập mới có thể đặt hàng");
			System.out.println(model.getAttribute("messageBuyCart"));
			return "redirect:/login";
		}
	}

    /*
     * Xử lý yêu cầu POST khi người dùng nhập thông tin và xác nhận đặt hàng
     */
	@RequestMapping(value = "/addInfoUser", method = RequestMethod.POST)
	public String doAddEmployee(@ModelAttribute("employee") ShopOrdersEntity employee, ModelMap modelMap,
			HttpSession session) {
	    // Lưu thông tin người dùng và đơn hàng vào session
		session.setAttribute("userInf", employee);
		System.out.println(employee.getShippingFee());
		
		// Chuyển hướng đến trang xem giỏ hàng
		return "redirect:/order-page";
	}

}
