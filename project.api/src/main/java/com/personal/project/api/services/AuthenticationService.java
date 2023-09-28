package com.personal.project.api.services;

import com.personal.project.api.configs.JWTUtil;
import com.personal.project.api.models.user.User;
import com.personal.project.api.dto.user.AuthenticationDTO;
import com.personal.project.api.dto.user.RegisterDTO;
import com.personal.project.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    public boolean userExists(RegisterDTO registerDTO) {
        return userRepository.findByLogin(registerDTO.login()) != null;
    }

    public void registerUser(RegisterDTO registerDTO) {
        String encryptPass = new BCryptPasswordEncoder().encode(registerDTO.password());
        User newUser = new User(registerDTO.login(), encryptPass, registerDTO.role());
        userRepository.save(newUser);
    }

    public String loginUser(AuthenticationDTO authDTO) {
        var userPass = new UsernamePasswordAuthenticationToken(authDTO.login(), authDTO.password());
        var auth = this.authManager.authenticate(userPass);

        return jwtUtil.generateToken((User)auth.getPrincipal());
    }

}