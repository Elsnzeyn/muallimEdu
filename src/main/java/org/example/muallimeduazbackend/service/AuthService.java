package org.example.muallimeduazbackend.service;

import org.example.muallimeduazbackend.dto.request.UserLoginRequestDto;
import org.example.muallimeduazbackend.dto.response.UserLoginResponseDto;

public interface AuthService {
    UserLoginResponseDto loginUser(UserLoginRequestDto body);
}
