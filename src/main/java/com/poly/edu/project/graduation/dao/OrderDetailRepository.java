package com.poly.edu.project.graduation.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import com.poly.edu.project.graduation.model.ShopOrderDetailEntity;

public interface OrderDetailRepository  extends JpaRepository<ShopOrderDetailEntity, Integer>{
//ádasdasdasd
    @Query(value = "SELECT product_id, quantity FROM shop_order_detail where order_id  = ?1", nativeQuery = true)
   List<Object[]> queryListOrderDetailId(String id);
   
   @Procedure(procedureName = "updateShopProductQuantity")
   void updateRevertQuantityProduct(BigInteger id, BigInteger quantity);
}
