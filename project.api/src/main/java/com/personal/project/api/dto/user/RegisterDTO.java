package com.personal.project.api.dto.user;

import com.personal.project.api.models.user.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
