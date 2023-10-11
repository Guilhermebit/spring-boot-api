package com.personal.project.api.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.personal.project.api.dto.user.RequestUserLoginDTO;
import com.personal.project.api.dto.user.RequestUserRegisterDTO;
import com.personal.project.api.dto.user.ResponseUserRegisterDTO;
import com.personal.project.api.enums.UserRole;
import com.personal.project.api.mapper.UserMapper;
import com.personal.project.api.models.user.User;
import com.personal.project.api.repositories.UserRepository;
import com.personal.project.api.services.exceptions.AuthorizationException;
import com.personal.project.api.services.exceptions.BusinessException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import com.personal.project.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @MockBean
    //@Autowired
    private UserRepository userRepository;


    /**
     * Method under test: {@link UserService#registerUser(RequestUserRegisterDTO)}
     */

    @Test
    @Order(1)
    @DisplayName("Should register a user")
    void testRegisterUser() {
        User userObj = UTestData.createValidUser();
        RequestUserRegisterDTO requestUserRegisterDTO = UTestData.createValidUserRequest();

        when(userRepository.save(any())).thenReturn(userObj);

        ResponseUserRegisterDTO responseUserDTO =  userService.registerUser(requestUserRegisterDTO);

        assertEquals(userMapper.mapToResponseUserRegisterDTO(userObj), responseUserDTO);
        verify(userRepository).save(any());
    }

    /**
     * Method under test: {@link UserService#registerUser(RequestUserRegisterDTO)}
     */

    @Test
    @Order(2)
    @DisplayName("Should throw an exception when creating an invalid user")
    void testRegisterInvalid() {
        List<RequestUserRegisterDTO> userDTOList = UTestData.createInvalidUserRequest();
        for (RequestUserRegisterDTO registerUser : userDTOList) {
             assertThrows(ConstraintViolationException.class, () -> userService.registerUser(registerUser));
        }
        then(userRepository).shouldHaveNoInteractions();
    }

    /**
     * Method under test: {@link UserService#registerUser(RequestUserRegisterDTO)}
     */

    @Test
    @Order(3)
    @DisplayName("Should throw an exception when creating a duplicate login")
    void testRegisterSameLogin() {
        RequestUserRegisterDTO requestUserRegisterDTO = UTestData.createValidUserRequest();

        when(userRepository.findByLogin(anyString())).thenReturn(UTestData.createValidUser());

        assertThrows(BusinessException.class, () -> userService.registerUser(requestUserRegisterDTO));

        verify(userRepository).findByLogin(anyString());
        verify(userRepository, times(0)).save(any());
    }

    /**
     * Method under test: {@link UserService#loginUser(RequestUserLoginDTO)}
     */

    @Test
    @Order(4)
    @DisplayName("Should login a user")
    void testLoginUser() {
        // Arrange
        User user = new User(UTestData.LOGIN_ADMIN, UTestData.PASSWORD, UserRole.ADMIN);
        when(userRepository.findByLogin(anyString())).thenReturn(user);

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername(UTestData.LOGIN_ADMIN);

        // Assert
        assertEquals(user, userDetails);
        verify(userRepository).findByLogin(anyString());
    }

    /**
     * Method under test: {@link UserService#findAuthenticatedUser()}
     */

    @Test
    @Order(5)
    @DisplayName("Should throw Authorization exception when access denied")
    void testAccessDenied() {
        Exception exception = assertThrows(AuthorizationException.class, () -> userService.findAuthenticatedUser());
        assertEquals("Access denied!", exception.getMessage());
    }

}
