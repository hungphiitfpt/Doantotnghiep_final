package com.poly.edu.project.graduation.model;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "app_user", //
uniqueConstraints = { //
        @UniqueConstraint(name = "APP_USER_UK", columnNames = "user_name") })
public class AppUserEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id", nullable = false)
    private long userId;
	
	@Basic
    @Column(name = "is_Admin", nullable = true)
    private int isAdmin;
    @Basic
    @Column(name = "user_name", nullable = false, length = 36)
    private String userName;
    @Basic
    @Column(name = "encryted_password", nullable = false, length = 128)
    private String encrytedPassword;
    @Basic
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;
    @Basic
    @Column(name = "last_name", nullable = true, length = 45)
    private String lastName;
    @Basic
    @Column(name = "first_name", nullable = true, length = 45)
    private String firstName;
    @Basic
    @Column(name = "gender", nullable = true)
    private Boolean gender;
    @Basic
    @Column(name = "email", nullable = true, length = 200)
    private String email;
    @Basic
    @JsonFormat(pattern="yyyy/MM/dd")
    @Column(name = "birthday", nullable = true)
    private Date birthday;
    @Basic
    @Column(name = "avatar", nullable = true, length = 200)
    private String avatar;
    @Basic
    @Column(name = "address", nullable = true, length = 400)
    private String address;
    @Basic
    @Column(name = "country", nullable = true, length = 45)
    private String country;
    @Basic
    @Column(name = "city", nullable = true, length = 45)
    private String city;
    @Basic
    @CreationTimestamp
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;
    @Basic
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = true)
    private Timestamp updatedAt;
    @OneToMany(mappedBy = "appUserByUserId")
    private List<UserRoleEntity> userRolesByUserId;
    @OneToMany(mappedBy = "appUserByUserId")
    private List<ShopOrdersEntity> shopOrdersByUserId;
    
    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    
	public long getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

	public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncrytedPassword() {
        return encrytedPassword;
    }

    public void setEncrytedPassword(String encrytedPassword) {
        this.encrytedPassword = encrytedPassword;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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


    public List<UserRoleEntity> getUserRolesByUserId() {
        return userRolesByUserId;
    }

    public void setUserRolesByUserId(List<UserRoleEntity> userRolesByUserId) {
        this.userRolesByUserId = userRolesByUserId;
    }

    public List<ShopOrdersEntity> getShopOrdersByUserId() {
        return shopOrdersByUserId;
    }

    public void setShopOrdersByUserId(List<ShopOrdersEntity> shopOrdersByUserId) {
        this.shopOrdersByUserId = shopOrdersByUserId;
    }
}
