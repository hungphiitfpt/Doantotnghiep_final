// Import các gói và lớp cần thiết
package com.poly.edu.project.graduation.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.edu.project.graduation.dao.ProductsRepository;
import com.poly.edu.project.graduation.enums.Title;
import com.poly.edu.project.graduation.services.ProductServices;

// Đánh dấu lớp này là một Controller trong Spring MVC
@Controller
// Ánh xạ URL cơ bản cho tất cả các phương thức trong controller này
@RequestMapping("/admin")
public class AdminHomeController {

    // Tự động nạp (autowire) bean ProductServices
    @Autowired
    ProductServices productServices;

    // Tự động nạp (autowire) bean ProductsRepository
    @Autowired
    ProductsRepository productsRepository;

    // Xử lý yêu cầu đến "/admin/index2"
    @RequestMapping("/index2")
    public String index(Model model) {
        // Trả về tên view là "admin-template/index"
        return "admin-template/index";
    }

    // Xử lý yêu cầu đến "/admin/manager_product"
    @RequestMapping("/manager_product")
    public String managerProduct(Model model) {
        // Trả về tên view là "admin-template/pages/forms/form_manager_product"
        return "admin-template/pages/forms/form_manager_product";
    }

    // Xử lý yêu cầu đến "/admin/manager_employee"
    @RequestMapping("/manager_employee")
    public String managerEmployee(Model model) {
        // Trả về tên view là "admin-template/pages/forms/form_manager_employee"
        return "admin-template/pages/forms/form_manager_employee";
    }

    // Xử lý yêu cầu đến "/admin/manager_category"
    @RequestMapping("/manager_category")
    public String managerCategory(Model model) {
        // Trả về tên view là "admin-template/pages/forms/form_manager_category"
        return "admin-template/pages/forms/form_manager_category";
    }

    // Xử lý yêu cầu đến "/admin/manager_order_product"
    @RequestMapping("/manager_order_product")
    public String managerOrderProduct(Model model) {
        // Trả về tên view là "admin-template/pages/forms/form_order_products"
        return "admin-template/pages/forms/form_order_products";
    }

    // Xử lý yêu cầu đến "/admin/manager_review_product"
    @RequestMapping("/manager_review_product")
    public String managerReviewProduct(Model model) {
        // Trả về tên view là "admin-template/pages/forms/formManagerReview"
        return "admin-template/pages/forms/formManagerReview";
    }

    // Xử lý yêu cầu đến "/admin/Statistical"
    @RequestMapping("/Statistical")
    public String reportChart(Model model) {
        // Trả về tên view là "admin-template/pages/charts/chartjs"
        return "admin-template/pages/charts/chartjs";
    }
}
