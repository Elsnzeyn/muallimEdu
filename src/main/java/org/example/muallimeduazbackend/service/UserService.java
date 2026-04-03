package org.example.muallimeduazbackend.service;

import org.example.muallimeduazbackend.dto.request.CreateUserRequestDto;
import org.example.muallimeduazbackend.dto.request.UpdateUserRequestDto;
import org.example.muallimeduazbackend.dto.request.UserLoginRequestDto;
import org.example.muallimeduazbackend.dto.response.UserLoginResponseDto;
import org.example.muallimeduazbackend.entity.User;

import java.util.List;

public interface UserService {
    String createUser(CreateUserRequestDto body, User authUser);
    String updateUser(UpdateUserRequestDto body);
    List<User> getUsers();
    User findById(int id);
    User findByEmailOrElseThrow(String email);

}
