package org.example.muallimeduazbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.muallimeduazbackend.dto.request.UserLoginRequestDto;
import org.example.muallimeduazbackend.dto.response.UserLoginResponseDto;
import org.example.muallimeduazbackend.service.AuthService;
import org.example.muallimeduazbackend.service.JwtService;
import org.example.muallimeduazbackend.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public UserLoginResponseDto loginUser(UserLoginRequestDto body){
        var existingUser = userService.findByEmailOrElseThrow(body.getEmail());
        if(existingUser == null){
            throw new RuntimeException ("there is not such an email");
        }

        if(!existingUser.getPassword().equals(body.getPassword())){
            throw new RuntimeException(String.format("there %s not a password for this: %s","is", body.getEmail()));
        }
        var token = jwtService.generateToken(existingUser);
        return UserLoginResponseDto.builder().token(token).build();
    }
}
