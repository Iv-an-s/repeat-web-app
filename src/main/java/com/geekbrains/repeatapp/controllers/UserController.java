package com.geekbrains.repeatapp.controllers;

import com.geekbrains.repeatapp.dtos.UserRegDto;
import com.geekbrains.repeatapp.entities.User;
import com.geekbrains.repeatapp.exceptions.DataValidationException;
import com.geekbrains.repeatapp.exceptions.MarketError;
import com.geekbrains.repeatapp.servises.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/reg")
    public UserRegDto registerUser(@RequestBody @Validated UserRegDto userRegDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new DataValidationException(bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList()));
        }
        if (!userRegDto.getPassword().equals(userRegDto.getControlPassword())){
            throw new DataValidationException(Collections.singletonList("Введенные пароли не совпадают"));
        }
        User user = userService.saveUser(userRegDto);
        return new UserRegDto(user);
    }
}
