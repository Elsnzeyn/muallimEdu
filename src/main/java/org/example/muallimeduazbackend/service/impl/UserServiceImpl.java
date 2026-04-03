package org.example.muallimeduazbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.muallimeduazbackend.dto.request.CreateUserRequestDto;
import org.example.muallimeduazbackend.dto.request.UpdateUserRequestDto;
import org.example.muallimeduazbackend.dto.request.UserLoginRequestDto;
import org.example.muallimeduazbackend.dto.response.UserLoginResponseDto;
import org.example.muallimeduazbackend.entity.User;
import org.example.muallimeduazbackend.enums.UserRoleEnum;
import org.example.muallimeduazbackend.exception.ResourceNotFoundException;
import org.example.muallimeduazbackend.repository.UserRepository;
import org.example.muallimeduazbackend.service.JwtService;
import org.example.muallimeduazbackend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.muallimeduazbackend.enums.UserRoleEnum.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;


    private void userValidation(CreateUserRequestDto body){

        var password = body.getPassword();

        if(userRepo.existsByEmail(body.getEmail())){
            throw new RuntimeException("User already exist with this email: " + body.getEmail());
        }

        else if(password.matches("[a-zA-Z0-9]+")){
            throw new RuntimeException("Password doesnt match requirements, add special character");
        }

        else if(password.length()<8){
            throw new RuntimeException("Password length doesnt match requirements (min 8 elements)");
        }

        else if(!password.matches("[A-Z]") || !password.matches("[a-z]") || !password.matches("[0-9]")){
            throw new RuntimeException("Password should contain lowercase and uppercase letters, and numbers");
        }

        else if(!body.getEmail().contains("@")){
            throw new RuntimeException("Not valid email");
        }
    }

    public String createUser(CreateUserRequestDto body, User authUser){

        if(!isAvailable(authUser.getRole(), body.getRole())){
            return "access denied";
        }
        userValidation(body);

        User user = User.builder()
                .email(body.getEmail())
                .role(body.getRole().name())
                .name(body.getName())
                .password(body.getPassword())
                .build();
        userRepo.save(user);
        return "User added successfully with this email: " + body.getEmail();
    }



    @Override
    public String updateUser(UpdateUserRequestDto body){
        var existingUser = userRepo.findByEmail(body.getEmail());
        if(existingUser == null){
            return "There is not a user with this email: " + body.getEmail();
        }


        if(!(existingUser.getRole().equals(SUPER_ADMIN.name()) || existingUser.getRole().equals(ADMIN.name()))){
            return "You cannot change password unless being admin";
        }
        existingUser.setPassword(body.getPassword());
        userRepo.save(existingUser);
        return "User`s password changed` with this email: " + body.getEmail();

    }


    @Override
    public List<User> getUsers(){
        return userRepo.findAll();
    }

    public User findById(int id){
        return userRepo.findById(id);
    }

    private boolean isAvailable(String mainRole, UserRoleEnum secondRole){
        if(mainRole.equals(SUPER_ADMIN.name())){
            return true;
        }
        if(mainRole.equals(ADMIN.name()) && secondRole.equals(SUPER_ADMIN)){
                return false;
        }
        if(mainRole.equals(REDACTOR.name()) && (secondRole.equals(SUPER_ADMIN) || secondRole.equals(ADMIN)))
        {
            return false;
        }
        if(mainRole.equals(READER.name()) && !secondRole.equals(READER)){
            return false;
        }
        return true;
    }

    @Override
    public User findByEmailOrElseThrow(String email){
        var user = userRepo.findByEmail(email);
        if(user == null){
            throw new ResourceNotFoundException("Email not found");
        }

        return user;
    }
}
