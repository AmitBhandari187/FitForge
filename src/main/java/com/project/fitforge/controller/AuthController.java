package com.project.fitforge.controller;

import com.project.fitforge.dto.LoginRequest;
import com.project.fitforge.dto.LoginResponse;
import com.project.fitforge.dto.RegisterRequest;
import com.project.fitforge.dto.UserResponse;
import com.project.fitforge.model.User;
import com.project.fitforge.repository.UserRepository;
import com.project.fitforge.security.JwtUtils;
import com.project.fitforge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @PostMapping("register")
    private ResponseEntity<UserResponse> registerUser(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(userService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        Authentication authentication;
        try {
            //Get the user
            User user=userRepository.findByEmail(loginRequest.getEmail());
            if (user==null) return ResponseEntity.status(401).build();
            //Validate password
            if (!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())){
                return ResponseEntity.status(401).build();
            };  // Uses to match raw pass and encoded pass from db
            // Generate token
            String token=jwtUtils.generateToken(user.getUserId(),user.getRole().name());

            return ResponseEntity.ok(
                    new LoginResponse(
                            token,
                            userService.mapToResponse(user)
                    )
            );
        }catch (AuthenticationException e){
            e.printStackTrace();
            return ResponseEntity.status(401).build();
        }
    }
}
