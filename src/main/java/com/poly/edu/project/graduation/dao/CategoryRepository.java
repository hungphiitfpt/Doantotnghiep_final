package com.poly.edu.project.graduation.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.edu.project.graduation.model.ShopCategoriesEntity;
import com.poly.edu.project.graduation.model.ShopProductsEntity;

public interface CategoryRepository extends JpaRepository<ShopCategoriesEntity, Long> {
    // Truy vấn native SQL để lấy danh sách tất cả các danh mục không bị xóa
    @Query(value = "SELECT * FROM shop_categories  where shop_categories.is_deleted = false", nativeQuery = true)
    List<ShopCategoriesEntity> findAllCategory();

    // Truy vấn JPA để tìm danh sách danh mục theo tên danh mục
    @Query("SELECT u FROM ShopCategoriesEntity u WHERE u.categoryName = ?1")
    List<ShopCategoriesEntity> findByProductName(String categoryName);
    
    // Truy vấn native SQL để tìm kiếm danh mục theo nhiều điều kiện
    @Query(value ="SELECT * FROM shop_categories  where shop_categories.is_deleted = false "
            + "AND shop_categories.id                  LIKE CONCAT('%',:keyword,'%') "
            + "OR shop_categories.category_code         LIKE CONCAT('%',:keyword,'%') "
            + "OR shop_categories.category_name         LIKE CONCAT('%',:keyword,'%') "
            + "OR shop_categories.description          LIKE CONCAT('%',:keyword,'%') " ,nativeQuery = true)
    Page<ShopCategoriesEntity> findByKeyWord(@Param("keyword") String keyword, Pageable pageable);

    // Truy vấn JPA để cập nhật trạng thái isDeleted thành TRUE
    @Modifying  
    @Transactional
    @Query(value = "UPDATE ShopCategoriesEntity SET isDeleted = TRUE WHERE id = ?1")
    void changeStatusIsdeleted(long id);
    
    // Truy vấn JPA để cập nhật trạng thái isDeleted thành FALSE
    @Modifying  
    @Transactional
    @Query(value = "UPDATE ShopCategoriesEntity SET isDeleted = FALSE WHERE id = ?1")
    void changeIstock(long id);
}
