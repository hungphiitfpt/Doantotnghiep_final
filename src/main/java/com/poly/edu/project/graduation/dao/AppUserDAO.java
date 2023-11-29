package com.poly.edu.project.graduation.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.poly.edu.project.graduation.model.AppUserEntity;

@Repository
@Transactional
public class AppUserDAO extends HttpServlet {

    @Autowired
    private EntityManager entityManager;
    // EntityManager được tự động chèn bởi Spring để tương tác với ngữ cảnh lưu trữ.

    public AppUserEntity findUserAccount(String userName) {
        // Phương thức này tìm kiếm thông tin người dùng dựa trên tên người dùng.

        try {
            // Xây dựng truy vấn JPQL để chọn thông tin người dùng dựa trên tên người dùng.
            String sql = "Select e from " + AppUserEntity.class.getName() + " e " //
                    + " Where e.userName = :userName ";

            // Tạo đối tượng Truy vấn JPA bằng cách sử dụng EntityManager.
            Query query = entityManager.createQuery(sql, AppUserEntity.class);
            
            // Thiết lập tham số cho tên người dùng trong truy vấn.
            query.setParameter("userName", userName);
            
            // Thực hiện truy vấn và trả về kết quả duy nhất.
            return (AppUserEntity) query.getSingleResult();
        } catch (NoResultException e) {
            // Nếu không tìm thấy kết quả, trả về null.
            return null;
        }
    }
}
