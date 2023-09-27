package com.personal.project.api.controllers;

import com.personal.project.api.configs.JWTUtil;
import com.personal.project.api.models.user.User;
import com.personal.project.api.models.user.dto.AuthenticationDTO;
import com.personal.project.api.models.user.dto.LoginResponseDTO;
import com.personal.project.api.models.user.dto.RegisterDTO;
import com.personal.project.api.repositories.UserRepository;
import com.personal.project.api.responses.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody @Valid AuthenticationDTO authDTO) {
          var userPass = new UsernamePasswordAuthenticationToken(authDTO.login(), authDTO.password());
          var auth = this.authManager.authenticate(userPass);

          var token = jwtUtil.generateToken((User)auth.getPrincipal());

          return ResponseHandler.responseBuilder(HttpStatus.OK, "", new LoginResponseDTO(token));
    }

    @PostMapping("register")
    public ResponseEntity registerUser(@RequestBody @Valid RegisterDTO registerDTO) {
          // User already registered!
          if(this.userRepository.findByLogin(registerDTO.login()) != null)
              return ResponseEntity.badRequest().build();

          // New user
          String encryptPass = new BCryptPasswordEncoder().encode(registerDTO.password());
          User newUser = new User(registerDTO.login(), encryptPass, registerDTO.role());
          this.userRepository.save(newUser);

        return ResponseHandler.responseBuilder(HttpStatus.OK, "Your registration was successful", null);
    }

}
