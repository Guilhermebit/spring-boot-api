package com.personal.project.api.controllers;

import com.personal.project.api.dto.user.RequestUserLoginDTO;
import com.personal.project.api.dto.user.ResponseUserLoginDTO;
import com.personal.project.api.dto.user.RequestUserRegisterDTO;
import com.personal.project.api.responses.ResponseHandler;
import com.personal.project.api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class UserController {

    @Autowired
    private UserService authService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid RequestUserRegisterDTO requestUserRegisterDTO) {
        boolean userExists = authService.userExists(requestUserRegisterDTO.login());
        if(userExists)
            return ResponseHandler.responseBuilder(HttpStatus.BAD_REQUEST, "User already registered!", null);

        authService.registerUser(requestUserRegisterDTO);

        return ResponseHandler.responseBuilder(HttpStatus.OK, "Your registration was successful", null);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody @Valid RequestUserLoginDTO authDTO) {
            String token = authService.loginUser(authDTO);
            return ResponseHandler.responseBuilder(HttpStatus.OK, "", new ResponseUserLoginDTO(token));
    }

}
