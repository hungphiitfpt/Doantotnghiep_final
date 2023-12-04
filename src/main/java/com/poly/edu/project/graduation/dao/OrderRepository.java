package com.poly.edu.project.graduation.dao;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.poly.edu.project.graduation.model.ShopOrdersEntity;

public interface OrderRepository extends JpaRepository<ShopOrdersEntity, Long> {

    // Truy vấn native SQL để lấy danh sách đơn hàng theo từ khóa nhập vào
    @Query(value = "SELECT * FROM shop_orders  "     
            + "WHERE (( :keyword <> '' AND (shop_orders.user_id LIKE CONCAT('%',:keyword,'%') "
            + "OR shop_orders.ship_name LIKE CONCAT('%',:keyword,'%') "
            + "OR shop_orders.id LIKE CONCAT('%',:keyword,'%') "
            + "OR shop_orders.ship_city LIKE CONCAT('%',:keyword,'%') "
            + "OR shop_orders.ship_state LIKE CONCAT('%',:keyword,'%'))) "
            + "OR :keyword = '')", nativeQuery = true)
    Page<ShopOrdersEntity> findByKeyWord(String keyword, Pageable pageable);

    // Truy vấn native SQL để tìm chi tiết đơn hàng theo ID
    @Query(value = " SELECT * FROM shop_orders where id = ?1", nativeQuery =  true)
    ShopOrdersEntity findOrdersDetailById(Long id);

    // Truy vấn native SQL để thay đổi trạng thái đơn hàng
    @Modifying  
    @Query(value = "UPDATE shop_orders SET order_status = :status, updated_at = :update_at, "
            + "shipped_date = :shipped_date WHERE id  = :id ", nativeQuery = true)
    // Quay ngược hành động nếu không thành công
    @Transactional
    void changeStatusOrder(Long status, String update_at, String shipped_date,Long id);

    // Truy vấn native SQL để lấy danh sách đơn hàng theo ID người dùng
    @Query(value = "SELECT * FROM shop_orders where user_id = :id",nativeQuery = true)
    List<ShopOrdersEntity> findByUserId(String id);

    // Truy vấn native SQL để hủy đơn hàng theo ID
    @Modifying  
    @Query(value = "UPDATE ShopOrdersEntity SET orderStatus = 6 WHERE id = ?1")
    @Transactional
    void cancelOrderById(long id);

    // Truy vấn native SQL để đếm số lượng đơn hàng theo ngày
    @Query(value = "SELECT COUNT(DISTINCT(user_id)) FROM shop_orders where created_at LIKE CONCAT('%',:date,'%')", nativeQuery = true)
    String count_number_order_customers(Date date);

    // Truy vấn native SQL để tính tổng giá trị đơn hàng theo ngày
    @Query(value = "SELECT SUM(total_price) FROM shop_orders where created_at LIKE CONCAT('%',:date,'%')", nativeQuery = true)
    String TotalPriceOrdersDateNow(Date date);

    // Truy vấn native SQL để đếm số lượng đơn hàng theo ngày
    @Query(value = "SELECT COUNT(id) FROM shop_orders where created_at LIKE CONCAT('%',:date,'%')", nativeQuery = true)
    String CountOrdersDateNow(Date date);

    // Truy vấn native SQL để đếm số lượng đơn hàng theo từng trạng thái
    @Query(value = "SELECT count(id) FROM shop_orders where order_status = 0 "
            + "UNION ALL "
            + "SELECT count(id) FROM shop_orders where order_status = 1 "
            + "UNION ALL "
            + "SELECT count(id) FROM shop_orders where order_status = 2 "
            + "UNION ALL "
            + "SELECT count(id) FROM shop_orders where order_status = 3 "
            + "UNION ALL "
            + "SELECT count(id) FROM shop_orders where order_status = 4 "
            + "UNION ALL "
            + "SELECT count(id) FROM shop_orders where order_status = 5 "
            + "UNION ALL "
            + "SELECT count(id) FROM shop_orders where order_status = 6", nativeQuery = true)
    List<String> countOrderMarquee();
}
