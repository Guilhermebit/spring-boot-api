package com.personal.project.api.controllers;

import com.personal.project.api.dto.user.AuthenticationDTO;
import com.personal.project.api.dto.user.LoginResponseDTO;
import com.personal.project.api.dto.user.RegisterUserDTO;
import com.personal.project.api.responses.ResponseHandler;
import com.personal.project.api.services.AuthenticationService;
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
public class AuthenticationController {

    @Autowired
    private AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody @Valid AuthenticationDTO authDTO) {
            var token = authService.loginUser(authDTO);
            return ResponseHandler.responseBuilder(HttpStatus.OK, "", new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid RegisterUserDTO registerUserDTO) {
            boolean userExists = authService.userExists(registerUserDTO.getLogin());
            if(userExists)
               return ResponseHandler.responseBuilder(HttpStatus.BAD_REQUEST, "User already registered!", null);

            authService.registerUser(registerUserDTO);

            return ResponseHandler.responseBuilder(HttpStatus.OK, "Your registration was successful", null);
    }

}
