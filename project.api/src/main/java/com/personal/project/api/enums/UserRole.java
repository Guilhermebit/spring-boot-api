package com.personal.project.api.enums;

import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN("ADMIN"),
    USER("USER");

    private final String role;

    UserRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return role;
    }

}

