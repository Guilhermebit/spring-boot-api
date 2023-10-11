package com.personal.project.api.mapper;

import com.personal.project.api.dto.user.ResponseUserRegisterDTO;
import com.personal.project.api.models.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public ResponseUserRegisterDTO mapToResponseUserRegisterDTO(User user) {
        if (user == null)
            return null;

        return new ResponseUserRegisterDTO(
                user.getId(),
                user.getUsername()
        );
    }
}