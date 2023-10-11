package com.personal.project.api.services;

import com.personal.project.api.configs.JWTUtil;
import com.personal.project.api.dto.user.ResponseUserLoginDTO;
import com.personal.project.api.dto.user.ResponseUserRegisterDTO;
import com.personal.project.api.mapper.UserMapper;
import com.personal.project.api.models.user.User;
import com.personal.project.api.dto.user.RequestUserLoginDTO;
import com.personal.project.api.dto.user.RequestUserRegisterDTO;
import com.personal.project.api.repositories.UserRepository;
import com.personal.project.api.services.exceptions.AuthorizationException;
import com.personal.project.api.services.exceptions.BusinessException;
import com.personal.project.api.services.exceptions.ObjectNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.Objects;
import java.util.Optional;

@Service
@Validated
public class UserService {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserMapper userMapper;


    private User userAuthenticated() {
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        if(userAuth == null)
           return null;
        return (User) userAuth.getPrincipal();
    }

    protected boolean hasRole(@NotBlank String roleName)
    {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
    }

    public User findAuthenticatedUser() {
        User userAuth = userAuthenticated();
        if(Objects.isNull(userAuth))
           throw new AuthorizationException("Access denied!");

        Optional<User> user = this.userRepository.findById(userAuth.getId());
        return user.orElseThrow(() -> new ObjectNotFoundException(
                "User not found! Id: " + userAuth.getId() + ", Type: " + User.class.getName()));
    }

    public boolean userExists(@NotBlank String login) {
        return userRepository.findByLogin(login) != null;
    }

    public ResponseUserRegisterDTO registerUser(@Valid RequestUserRegisterDTO requestUserRegisterDTO) {
        if(userExists(requestUserRegisterDTO.login()))
           throw new BusinessException("User already registered!");

        String encryptPass = new BCryptPasswordEncoder().encode(requestUserRegisterDTO.password());
        User newUser = new User(requestUserRegisterDTO.login(), encryptPass, requestUserRegisterDTO.role());
        return userMapper.mapToResponseUserRegisterDTO(userRepository.save(newUser));
    }

    public ResponseUserLoginDTO loginUser(@Valid RequestUserLoginDTO authDTO) {
        var userPass = new UsernamePasswordAuthenticationToken(authDTO.login(), authDTO.password());
        var auth = this.authManager.authenticate(userPass);

        return new ResponseUserLoginDTO(jwtUtil.generateToken((User)auth.getPrincipal()));
    }

}
