package com.poly.edu.project.graduation.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.poly.edu.project.graduation.exception.CustomerNotFoundException;
import com.poly.edu.project.graduation.model.AppUserEntity;
import com.poly.edu.project.graduation.model.Utility;
import com.poly.edu.project.graduation.services.UserService;

import net.bytebuddy.utility.RandomString;

@Controller
public class ForgotPasswordController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService service;

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "shop-template/forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        // Tạo một chuỗi ngẫu nhiên để sử dụng làm token
        String token = RandomString.make(30);
        try {
            // Cập nhật token vào cơ sở dữ liệu
            service.updateResetPasswordToken(token, email);
            // Tạo liên kết đặt lại mật khẩu
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            // Gửi email chứa liên kết đặt lại mật khẩu
            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "Chúng tôi đã gửi một liên kết đặt lại mật khẩu đến email của bạn. Vui lòng kiểm tra hộp thư của bạn.");
        } catch (CustomerNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Có lỗi khi gửi email");
        }

        return "shop-template/forgot_password_form";
    }

    // Gửi email chứa liên kết đặt lại mật khẩu
    public void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("contact@support.com", "Support Graduation");
        helper.setTo(recipientEmail);

        String subject = "Thay đổi mật khẩu của bạn";

        String content = "<p>Xin chào,</p>"
                + "<p>Bạn đã yêu cầu đặt lại mật khẩu của mình.</p>"
                + "<p>Nhấp vào liên kết bên dưới để thay đổi mật khẩu của bạn:</p>"
                + "<p><a href=\"" + link + "\">Thay đổi mật khẩu của tôi</a></p>"
                + "<br>"
                + "<p>Bỏ qua email này nếu bạn nhớ mật khẩu của mình, "
                + "hoặc bạn chưa thực hiện yêu cầu.</p>";

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        // Lấy thông tin người dùng dựa trên token
        AppUserEntity customer = service.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (customer == null) {
            model.addAttribute("message", "Token không hợp lệ");
            return "message";
        }

        return "shop-template/reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        // Lấy thông tin người dùng dựa trên token
        AppUserEntity customer = service.getByResetPasswordToken(token);
        model.addAttribute("title", "Thay Đổi Mật khẩu");

        if (customer == null) {
            model.addAttribute("message", "Token không hợp lệ");
            return "message";
        } else {
            // Cập nhật mật khẩu mới
            service.updatePassword(customer, password);

            model.addAttribute("message", "Bạn đã thay đổi mật khẩu thành công.");
        }

        return "shop-template/forgot_password_form";
    }

}
