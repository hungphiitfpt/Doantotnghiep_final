package com.poly.edu.project.graduation.model;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "shop_orders", schema = "ecommer_db", catalog = "")
public class ShopOrdersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "user_id", nullable = true)
    private Long userId;
    @Basic
    @Column(name = "order_date", nullable = true)
    private Timestamp orderDate;
    @Basic
    @Column(name = "ship_name", nullable = true, length = 50)
    private String shipName;
    @Basic
    @Column(name = "ship_address", nullable = true, length = 500)
    private String shipAddress;
    @Basic
    @Column(name = "ship_city", nullable = true, length = 255)
    private String shipCity;
    @Basic
    @Column(name = "ship_state", nullable = true, length = 255)
    private String shipState;
    @Basic
    @Column(name = "note", nullable = true, length = 255)
    private String note;
    @Basic
    @Column(name = "phone", nullable = true, length = 100)
    private String phone;
	@Basic
    @Column(name = "shipping_fee", nullable = true, precision = 4)
    private Integer shippingFee;
    @Basic
    @Column(name = "payment_type_id", nullable = true)
    private Long paymentTypeId;
    @Basic
    @Column(name = "paid_date", nullable = true)
    private Timestamp paidDate;
    @Basic
    @Column(name = "order_status", nullable = true)
    private Integer orderStatus;
    @Basic
    @CreationTimestamp
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;
    @Basic
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = true)
    private Timestamp updatedAt;
    @Basic
    @Column(name = "total_price", nullable = true)
    private Integer totalPrice;
    @Basic
    @Column(name = "shipped_date", nullable = true)
    private Timestamp shippedDate;
    @OneToMany(mappedBy = "shopOrdersByOrderId")
    private List<ShopOrderDetailEntity> shopOrderDetailsById;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private AppUserEntity appUserByUserId;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "payment_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ShopPaymentTypesEntity shopPaymentTypesByPaymentTypeId;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public String getShipCity() {
        return shipCity;
    }

    public void setShipCity(String shipCity) {
        this.shipCity = shipCity;
    }

    public String getShipState() {
        return shipState;
    }

    public void setShipState(String shipState) {
        this.shipState = shipState;
    }

    public Integer getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Integer shippingFee) {
        this.shippingFee = shippingFee;
    }

    public Long getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(Long paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public Timestamp getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Timestamp paidDate) {
        this.paidDate = paidDate;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Timestamp getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Timestamp shippedDate) {
        this.shippedDate = shippedDate;
    }

    public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

    public ShopOrdersEntity(long id, Long userId, Timestamp orderDate, String shipName, String shipAddress,
			String shipCity, String shipState, String note, String phone, Integer shippingFee, Long paymentTypeId,
			Timestamp paidDate, Integer orderStatus, Timestamp createdAt, Timestamp updatedAt, Integer totalPrice,
			Timestamp shippedDate, List<ShopOrderDetailEntity> shopOrderDetailsById, AppUserEntity appUserByUserId,
			ShopPaymentTypesEntity shopPaymentTypesByPaymentTypeId) {
		super();
		this.id = id;
		this.userId = userId;
		this.orderDate = orderDate;
		this.shipName = shipName;
		this.shipAddress = shipAddress;
		this.shipCity = shipCity;
		this.shipState = shipState;
		this.note = note;
		this.phone = phone;
		this.shippingFee = shippingFee;
		this.paymentTypeId = paymentTypeId;
		this.paidDate = paidDate;
		this.orderStatus = orderStatus;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.totalPrice = totalPrice;
		this.shippedDate = shippedDate;
		this.shopOrderDetailsById = shopOrderDetailsById;
		this.appUserByUserId = appUserByUserId;
		this.shopPaymentTypesByPaymentTypeId = shopPaymentTypesByPaymentTypeId;
	}

	public ShopOrdersEntity() {
		super();
	}

	public List<ShopOrderDetailEntity> getShopOrderDetailsById() {
        return shopOrderDetailsById;
    }

    public void setShopOrderDetailsById(List<ShopOrderDetailEntity> shopOrderDetailsById) {
        this.shopOrderDetailsById = shopOrderDetailsById;
    }

    public AppUserEntity getAppUserByUserId() {
        return appUserByUserId;
    }

    public void setAppUserByUserId(AppUserEntity appUserByUserId) {
        this.appUserByUserId = appUserByUserId;
    }

    public ShopPaymentTypesEntity getShopPaymentTypesByPaymentTypeId() {
        return shopPaymentTypesByPaymentTypeId;
    }

    public void setShopPaymentTypesByPaymentTypeId(ShopPaymentTypesEntity shopPaymentTypesByPaymentTypeId) {
        this.shopPaymentTypesByPaymentTypeId = shopPaymentTypesByPaymentTypeId;
    }
}
