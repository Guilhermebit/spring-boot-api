package com.personal.project.api.dto.user;

import com.personal.project.api.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestUserRegisterDTO (
        @NotBlank(message = "Login should not be empty or null.") String login,
        @NotBlank(message = "Password should not be empty or null.") String password,
        @NotNull(message = "Role should not be null.") UserRole role) {

}
