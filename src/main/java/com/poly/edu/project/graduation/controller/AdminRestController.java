// Import các gói và lớp cần thiết
package com.poly.edu.project.graduation.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.poly.edu.project.graduation.dao.ProductsRepository;
import com.poly.edu.project.graduation.dao.UserRepository;
import com.poly.edu.project.graduation.model.AppUserEntity;
import com.poly.edu.project.graduation.model.ResponseObject;
import com.poly.edu.project.graduation.model.ShopProductsEntity;
import com.poly.edu.project.graduation.services.ProductServices;

// Đánh dấu lớp này là một RestController trong Spring MVC
@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    // Tự động nạp (autowire) bean ProductServices
    @Autowired
    ProductServices productServices;

    // Tự động nạp (autowire) bean ProductsRepository
    @Autowired
    ProductsRepository productsRepository;
    
    @Autowired
    UserRepository userRepository;

    /*
     * API cập nhật sản phẩm
     */
    @RequestMapping(value = "/update_product", method = RequestMethod.POST, consumes = {
            MediaType.APPLICATION_JSON_UTF8_VALUE })
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    ResponseEntity<ResponseObject> insertProduct(@RequestBody ShopProductsEntity newProduct, @Param("id") Long id)
            throws Exception {
        // Cập nhật thông tin sản phẩm nếu tồn tại ID, ngược lại thêm sản phẩm mới
        ShopProductsEntity updatedProduct = productsRepository.findById(id).map(product -> {
            // Cập nhật thông tin sản phẩm từ newProduct
            product.setCategoryId(newProduct.getCategoryId());
            product.setDiscountinued(newProduct.getDiscountinued());
            product.setDecription(newProduct.getDecription());
            product.setImage(newProduct.getImage());
            product.setListPrice(newProduct.getListPrice());
            product.setProductCode(newProduct.getProductCode());
            product.setProductName(newProduct.getProductName());
            product.setQuantityPerUnit(newProduct.getQuantityPerUnit());
            product.setShortDecription(newProduct.getShortDecription());
            product.setSaletime(newProduct.getSaletime());
            product.setStandCost(newProduct.getStandCost());
            return productsRepository.save(product);
        }).orElseGet(() -> {
            newProduct.setId(0);
            return productsRepository.save(newProduct);
        });

        // Trả về thông báo thành công và thông tin sản phẩm đã cập nhật
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Cập nhật sản phẩm thành công", updatedProduct));
    }

    /*
     * API thêm sản phẩm, kiểm tra nếu đã có sản phẩm trùng trên không được thêm
     */
    @RequestMapping(value = "/insert_product", method = RequestMethod.POST, consumes = {
            MediaType.APPLICATION_JSON_UTF8_VALUE })
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    ResponseEntity<ResponseObject> insertProduct(@RequestBody ShopProductsEntity shopProductsEntity) {
        // Kiểm tra nếu đã có sản phẩm trùng tên, trả về thông báo lỗi
        List<ShopProductsEntity> foundProducts = productServices
                .findByProductName(shopProductsEntity.getProductName().trim());
        if (foundProducts.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(new ResponseObject("failed", "Đã có sản phẩm trùng tên", ""));
        }

        // Nếu không có sản phẩm trùng tên, thêm sản phẩm mới và trả về thông báo thành công
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("200", "Thêm Thành Công", productsRepository.save(shopProductsEntity)));
    }

    /*
     * API chỉnh sửa trạng thái của sản phẩm hết hàng
     */
    @RequestMapping(value = "/update/isdeleted", method = RequestMethod.POST, consumes = {
            MediaType.APPLICATION_JSON_UTF8_VALUE })
    void updateIsDeleted(@Param("id") long id) {
        // Gọi phương thức trong service để cập nhật trạng thái hết hàng
        productServices.changeStatusIsdeleted(id);
    }

    /*
     * API chỉnh sửa trạng thái của sản phẩm còn hàng
     */
    @RequestMapping(value = "/update/in_stock", method = RequestMethod.POST, consumes = {
            MediaType.APPLICATION_JSON_UTF8_VALUE })
    void updateInstock(@Param("id") long id) {
        // Gọi phương thức trong service để cập nhật trạng thái còn hàng
        productServices.changeStatusInstock(id);
    }
    
    /*
     * API chỉnh sửa trạng thái của sản phẩm còn hàng
     */
    @RequestMapping(value = "/getCity", method = RequestMethod.GET)
    public long getCode(Principal principal) {
    	Integer code = null;
    	AppUserEntity user = null;
    	if(principal != null) {
    		user = userRepository.findAddressUserByPricipal(principal.getName());
    		System.out.println(user.getCity());
    	}
    	HashMap<Integer, String> cityMap = new HashMap<Integer, String>();
    	cityMap.put(1, "Thành phố Hà Nội");
        cityMap.put(2, "Tỉnh Hà Giang");
        cityMap.put(4, "Tỉnh Cao Bằng");
        cityMap.put(6, "Tỉnh Bắc Kạn");
        cityMap.put(8, "Tỉnh Tuyên Quang");
        cityMap.put(10, "Tỉnh Lào Cai");
        cityMap.put(11, "Tỉnh Điện Biên");
        cityMap.put(12, "Tỉnh Lai Châu");
        cityMap.put(14, "Tỉnh Sơn La");
        cityMap.put(15, "Tỉnh Yên Bái");
        cityMap.put(17, "Tỉnh Hoà Bình");
        cityMap.put(19, "Tỉnh Thái Nguyên");
        cityMap.put(20, "Tỉnh Lạng Sơn");
        cityMap.put(22, "Tỉnh Quảng Ninh");
        cityMap.put(24, "Tỉnh Bắc Giang");
        cityMap.put(25, "Tỉnh Phú Thọ");
        cityMap.put(26, "Tỉnh Vĩnh Phúc");
        cityMap.put(27, "Tỉnh Bắc Ninh");
        cityMap.put(30, "Tỉnh Hải Dương");
        cityMap.put(31, "Thành phố Hải Phòng");
        cityMap.put(33, "Tỉnh Hưng Yên");
        cityMap.put(34, "Tỉnh Thái Bình");
        cityMap.put(35, "Tỉnh Hà Nam");
        cityMap.put(36, "Tỉnh Nam Định");
        cityMap.put(37, "Tỉnh Ninh Bình");
        cityMap.put(38, "Tỉnh Thanh Hóa");
        cityMap.put(40, "Tỉnh Nghệ An");
        cityMap.put(42, "Tỉnh Hà Tĩnh");
        cityMap.put(44, "Tỉnh Quảng Bình");
        cityMap.put(45, "Tỉnh Quảng Trị");
        cityMap.put(46, "Tỉnh Thừa Thiên Huế");
        cityMap.put(48, "Thành phố Đà Nẵng");
        cityMap.put(49, "Tỉnh Quảng Nam");
        cityMap.put(51, "Tỉnh Quảng Ngãi");
        cityMap.put(52, "Tỉnh Bình Định");
        cityMap.put(54, "Tỉnh Phú Yên");
        cityMap.put(56, "Tỉnh Khánh Hòa");
        cityMap.put(58, "Tỉnh Ninh Thuận");
        cityMap.put(60, "Tỉnh Bình Thuận");
        cityMap.put(62, "Tỉnh Kon Tum");
        cityMap.put(64, "Tỉnh Gia Lai");
        cityMap.put(66, "Tỉnh Đắk Lắk");
        cityMap.put(67, "Tỉnh Đắk Nông");
        cityMap.put(68, "Tỉnh Lâm Đồng");
        cityMap.put(70, "Tỉnh Bình Phước");
        cityMap.put(72, "Tỉnh Tây Ninh");
        cityMap.put(74, "Tỉnh Bình Dương");
        cityMap.put(75, "Tỉnh Đồng Nai");
        cityMap.put(77, "Tỉnh Bà Rịa - Vũng Tàu");
        cityMap.put(79, "Thành phố Hồ Chí Minh");
        cityMap.put(80, "Tỉnh Long An");
        cityMap.put(82, "Tỉnh Tiền Giang");
        cityMap.put(83, "Tỉnh Bến Tre");
        cityMap.put(84, "Tỉnh Trà Vinh");
        cityMap.put(86, "Tỉnh Vĩnh Long");
        cityMap.put(87, "Tỉnh Đồng Tháp");
        cityMap.put(89, "Tỉnh An Giang");
        cityMap.put(91, "Tỉnh Kiên Giang");
        cityMap.put(92, "Thành phố Cần Thơ");
        cityMap.put(93, "Tỉnh Hậu Giang");
        cityMap.put(94, "Tỉnh Sóc Trăng");
        cityMap.put(95, "Tỉnh Bạc Liêu");
        cityMap.put(96, "Tỉnh Cà Mau");
        // In ra các cặp key-value trong Map
        for (Map.Entry<Integer, String> entry : cityMap.entrySet()) {
            if(entry.getValue().contains(user.getCity())) {
            	System.out.println(entry.getKey());
            	code = entry.getKey();
            };
        }
		return code;
        
    }
    public class CityWrapper {
        public String name; 
        public String code;
        public String division_type;
        public String codename;
        public String phone_code;
        public List<districtsWrapper> districts;
        
        public CityWrapper() {
			super();
		}

		public CityWrapper(String name, String code, String division_type, String codename, String phone_code,
				List<districtsWrapper> districts) {
			super();
			this.name = name;
			this.code = code;
			this.division_type = division_type;
			this.codename = codename;
			this.phone_code = phone_code;
			this.districts = districts;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getDivision_type() {
			return division_type;
		}

		public void setDivision_type(String division_type) {
			this.division_type = division_type;
		}

		public String getCodename() {
			return codename;
		}

		public void setCodename(String codename) {
			this.codename = codename;
		}

		public String getPhone_code() {
			return phone_code;
		}

		public void setPhone_code(String phone_code) {
			this.phone_code = phone_code;
		}

		public List<districtsWrapper> getDistricts() {
			return districts;
		}

		public void setDistricts(List<districtsWrapper> districts) {
			this.districts = districts;
		}

		class districtsWrapper {
            public String name; 
            public districtsWrapper(String name, String code, String division_type, String codename,
					String phone_code) {
				super();
				this.name = name;
				this.code = code;
				this.division_type = division_type;
				this.codename = codename;
				this.phone_code = phone_code;
			}
			public districtsWrapper() {
				super();
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getCode() {
				return code;
			}
			public void setCode(String code) {
				this.code = code;
			}
			public String getDivision_type() {
				return division_type;
			}
			public void setDivision_type(String division_type) {
				this.division_type = division_type;
			}
			public String getCodename() {
				return codename;
			}
			public void setCodename(String codename) {
				this.codename = codename;
			}
			public String getPhone_code() {
				return phone_code;
			}
			public void setPhone_code(String phone_code) {
				this.phone_code = phone_code;
			}
			public String code;
            public String division_type;
            public String codename;
            public String phone_code;
        }
    }

}

