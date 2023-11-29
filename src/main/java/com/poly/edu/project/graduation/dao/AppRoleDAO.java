package com.poly.edu.project.graduation.dao;
// Lớp này là một Đối tượng Truy cập Dữ liệu (DAO) chịu trách nhiệm xử lý các vai trò liên quan đến người dùng trong cơ sở dữ liệu.

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.poly.edu.project.graduation.model.UserRoleEntity;

@Repository
@Transactional
public class AppRoleDAO {

    @Autowired
    private EntityManager entityManager;
    // EntityManager được tự động chèn bởi Spring để tương tác với ngữ cảnh lưu trữ.

    @SuppressWarnings("unchecked")
    public List<String> getRoleNames(Long userId) {
        // Phương thức để lấy tên vai trò liên quan đến một ID người dùng.

        // Xây dựng truy vấn JPQL (Ngôn ngữ Truy vấn Lưu trữ Java) để chọn tên vai trò.
        String sql = "Select ur.appRoleByRoleId.roleName from " + UserRoleEntity.class.getName() + " ur " //
                + " where ur.appUserByUserId.userId = :userId ";

        // Tạo đối tượng Truy vấn JPA bằng cách sử dụng EntityManager.
        Query query = this.entityManager.createQuery(sql, String.class);
        
        // Thiết lập tham số cho ID người dùng trong truy vấn.
        query.setParameter("userId", userId);
        
        // Thực hiện truy vấn và trả về danh sách tên vai trò.
        return query.getResultList();
    }
}
