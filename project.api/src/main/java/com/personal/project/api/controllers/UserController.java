package com.personal.project.api.controllers;

import com.personal.project.api.dto.user.RequestUserLoginDTO;
import com.personal.project.api.dto.user.ResponseUserLoginDTO;
import com.personal.project.api.dto.user.RequestUserRegisterDTO;
import com.personal.project.api.dto.user.ResponseUserRegisterDTO;
import com.personal.project.api.responses.ResponseHandler;
import com.personal.project.api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;;

@Validated
@RestController
@RequestMapping("auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid RequestUserRegisterDTO requestUserRegisterDTO) {
            ResponseUserRegisterDTO user = userService.registerUser(requestUserRegisterDTO);
            if(user == null)
                return ResponseHandler.responseBuilder(HttpStatus.OK, "Unable to register user!", null);
            return ResponseHandler.responseBuilder(HttpStatus.OK, "Your registration was successful", null);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody @Valid RequestUserLoginDTO authDTO) {
            ResponseUserLoginDTO token = userService.loginUser(authDTO);
            return ResponseHandler.responseBuilder(HttpStatus.OK, "", token);
    }

}
