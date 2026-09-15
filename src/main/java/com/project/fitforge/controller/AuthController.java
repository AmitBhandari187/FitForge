package com.project.fitforge.controller;

import com.project.fitforge.dto.RegisterRequest;
import com.project.fitforge.dto.UserResponse;
import com.project.fitforge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("register")
    private UserResponse registerUser(@RequestBody RegisterRequest registerRequest){
        return userService.register(registerRequest);
    }
}
