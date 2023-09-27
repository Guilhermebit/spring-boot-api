package com.personal.project.api.models.user.dto;

import com.personal.project.api.models.user.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
