package org.example.muallimeduazbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.muallimeduazbackend.dto.request.CreateUserRequestDto;
import org.example.muallimeduazbackend.dto.request.UpdateUserRequestDto;
import org.example.muallimeduazbackend.dto.request.UserLoginRequestDto;
import org.example.muallimeduazbackend.dto.response.CreateUserResponseDto;
import org.example.muallimeduazbackend.dto.response.GeneralResponseDto;
import org.example.muallimeduazbackend.entity.User;
import org.example.muallimeduazbackend.service.AuthService;
import org.example.muallimeduazbackend.service.JwtService;
import org.example.muallimeduazbackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthService authService;

    @PostMapping()
    public ResponseEntity<GeneralResponseDto<?>> createUser(@RequestBody CreateUserRequestDto body, @RequestHeader("Authorization") String token){
        var authUser = jwtService.decodeToken(token);
        var response = new GeneralResponseDto<>(userService.createUser(body, authUser), null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<GeneralResponseDto<?>> updateUser(@RequestBody UpdateUserRequestDto body){
        var response = new GeneralResponseDto<>(userService.updateUser(body), null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<GeneralResponseDto<?>> login(@RequestBody UserLoginRequestDto body){
        var response = new GeneralResponseDto<>("successful", authService.loginUser(body).getToken());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<GeneralResponseDto<List<User>>> getUsers(){
        var response = new GeneralResponseDto<>("Users loaded", userService.getUsers());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
