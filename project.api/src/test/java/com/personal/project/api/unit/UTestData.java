package com.personal.project.api.unit;

import com.personal.project.api.dto.product.RequestProductDTO;
import com.personal.project.api.dto.user.RequestUserRegisterDTO;
import com.personal.project.api.enums.UserRole;
import com.personal.project.api.models.product.Product;
import com.personal.project.api.models.user.User;
import java.util.List;

public class UTestData {

    public static final String LOGIN_ADMIN = "User1"; // ADMIN

    public static final String LOGIN_USER = "User2"; // USER

    public static final String PASSWORD = "1234";

    public static final String PRODUCT_ID = "49c74b02-3f7a-4af6-9b72-04622769adc1";

    public static final String USER_ID = "9d93b715-c6a5-4e48-9ad0-e0d536ab9b3e";

    public static final String PRODUCT_NAME = "Headset Sem Fio Gamer Multilaser";

    private static final Integer PRODUCT_PRICE = 17000;

    private UTestData(){

    }

    public static User createValidUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setLogin(LOGIN_USER);
        user.setPassword(PASSWORD);
        user.setRole(UserRole.ADMIN);
        return user;
    }

    public static RequestUserRegisterDTO createValidUserRequest() {
        return new RequestUserRegisterDTO(LOGIN_USER, PASSWORD, UserRole.ADMIN);
    }

    public static List<RequestUserRegisterDTO> createInvalidUserRequest() {
        return List.of(new RequestUserRegisterDTO(null, null, null),
                       new RequestUserRegisterDTO("", "", null));
    }

    public static Product createValidProduct() {
        Product product = new Product();
        product.setId(PRODUCT_ID);
        product.setName(PRODUCT_NAME);
        product.setPrice_in_cents(PRODUCT_PRICE);
        product.setActive(true);
        product.setUser(createValidUser());
        return product;
    }

    public static RequestProductDTO createValidProductRequest() {
        return new RequestProductDTO(PRODUCT_NAME, PRODUCT_PRICE);
    }

    public static List<RequestProductDTO> createInvalidProductRequest() {
        return List.of(new RequestProductDTO(null, null),
                       new RequestProductDTO("", null));
    }

}
