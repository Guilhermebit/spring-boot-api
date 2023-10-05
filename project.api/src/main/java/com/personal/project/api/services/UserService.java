package com.personal.project.api.services;

import com.personal.project.api.configs.JWTUtil;
import com.personal.project.api.models.user.User;
import com.personal.project.api.dto.user.RequestUserLoginDTO;
import com.personal.project.api.dto.user.RequestUserRegisterDTO;
import com.personal.project.api.repositories.UserRepository;
import com.personal.project.api.services.exceptions.AuthorizationException;
import com.personal.project.api.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.Optional;


@Service
public class UserService implements UserInterface {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    private User userAuthenticated() {
        User userAuth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(Objects.isNull(userAuth))
            return null;

        return userAuth;
    }

    protected boolean hasRole(String roleName)
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

    public boolean userExists(String login) {
        return userRepository.findByLogin(login) != null;
    }

    public void registerUser(RequestUserRegisterDTO requestUserRegisterDTO) {
        String encryptPass = new BCryptPasswordEncoder().encode(requestUserRegisterDTO.password());
        User newUser = new User(requestUserRegisterDTO.login(), encryptPass, requestUserRegisterDTO.role());
        userRepository.save(newUser);
    }

    public String loginUser(RequestUserLoginDTO authDTO) {
        var userPass = new UsernamePasswordAuthenticationToken(authDTO.login(), authDTO.password());
        var auth = this.authManager.authenticate(userPass);

        return jwtUtil.generateToken((User)auth.getPrincipal());
    }

}
