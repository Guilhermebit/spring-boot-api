package com.personal.project.api.dto.user;

import com.personal.project.api.models.user.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    @NotBlank(message = "Login should not be empty or null.")
    private String login;
    @NotBlank(message = "Password should not be empty or null.")
    private String password;
    private UserRole role;

}
