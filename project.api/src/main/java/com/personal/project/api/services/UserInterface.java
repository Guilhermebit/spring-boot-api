package com.personal.project.api.services;

import com.personal.project.api.dto.user.RequestUserLoginDTO;
import com.personal.project.api.dto.user.RequestUserRegisterDTO;

public interface UserInterface {
    void registerUser(RequestUserRegisterDTO requestUserRegisterDTO);
    String loginUser(RequestUserLoginDTO authDTO);
}
