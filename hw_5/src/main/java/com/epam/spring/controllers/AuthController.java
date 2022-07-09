package com.epam.spring.controllers;

import com.epam.spring.model.dto.user.UserDto;
import com.epam.spring.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody UserDto user){
        return userService.registerClient(user);
    }
}
