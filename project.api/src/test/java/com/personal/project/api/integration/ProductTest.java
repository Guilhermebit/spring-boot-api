package com.personal.project.api.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.project.api.configs.JWTUtil;
import com.personal.project.api.dto.product.RequestProductDTO;
import com.personal.project.api.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import com.personal.project.api.models.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc//(addFilters = false)
class ProductTest {

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	private String token;

	@BeforeEach
	void setUp() {
		token = jwtUtil.generateToken(new User(ITestData.LOGIN_ADMIN, ITestData.PASSWORD, UserRole.ADMIN));
	}

	@Test
	@Order(1)
	@DisplayName("Find products by an range")
	void testFindProductBetweenPrice() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
						.get(ITestData.ROUTE_PRODUCT_VALUES, 10000,20000)
						.header("Authorization","Bearer " + token))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andDo(MockMvcResultHandlers.print());
	}

	@Test
	@Order(2)
	@DisplayName("Find a product by id")
	void findUniqueProduct() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
						.get(ITestData.ROUTE_PRODUCT_ID, ITestData.ID)
						.header("Authorization","Bearer " + token))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andDo(MockMvcResultHandlers.print());
	}

	@Test
	@Order(3)
	@DisplayName("List all products")
	void testAllProducts() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
						.get(ITestData.ROUTE_PRODUCT)
						.header("Authorization","Bearer " + token))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andDo(MockMvcResultHandlers.print());
	}

	@Test
	@Order(4)
	@DisplayName("Create product")
	void testCreateProduct() throws Exception {
		RequestProductDTO requestProduct = new RequestProductDTO(ITestData.PRODUCT_NAME, 20000);
		mockMvc.perform(MockMvcRequestBuilders
						.post(ITestData.ROUTE_PRODUCT)
						.header("Authorization","Bearer " + token)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(ITestData.ENCODING)
						.content(objectMapper.writeValueAsString(requestProduct)))
			   .andExpect(MockMvcResultMatchers.status().isCreated())
			   .andDo(MockMvcResultHandlers.print());
	}

	@Test
	@Order(5)
	@DisplayName("Update product")
	void testUpdateProduct() throws Exception {
		RequestProductDTO requestProduct = new RequestProductDTO(ITestData.PRODUCT_NAME, 15000);
		mockMvc.perform(MockMvcRequestBuilders
						.put(ITestData.ROUTE_PRODUCT_ID, ITestData.ID)
						.header("Authorization","Bearer " + token)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding(ITestData.ENCODING)
						.content(objectMapper.writeValueAsString(requestProduct)))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andDo(MockMvcResultHandlers.print());
	}

	@Test
	@Order(6)
	@DisplayName("Delete product")
	void testDeleteProduct() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
						.delete(ITestData.ROUTE_PRODUCT_ID, ITestData.ID)
						.header("Authorization","Bearer " + token))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andDo(MockMvcResultHandlers.print());
	}

}
