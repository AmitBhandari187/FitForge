package com.project.fitforge.service;

import com.project.fitforge.dto.LoginRequest;
import com.project.fitforge.dto.RegisterRequest;
import com.project.fitforge.dto.UserResponse;
import com.project.fitforge.model.User;
import com.project.fitforge.model.UserRole;
import com.project.fitforge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(RegisterRequest registerRequest) {
        UserRole role = registerRequest.getRole() !=null ? registerRequest.getRole():UserRole.USER;
        User user=User.builder()
                .email(registerRequest.getEmail())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(role)
                .build();

        User savedUser= userRepository.save(user);
        return mapToResponse(savedUser);

    }

    public UserResponse mapToResponse(User savedUser) {
        UserResponse userResponse=new UserResponse();
        userResponse.setUserId(savedUser.getUserId());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setPassword(savedUser.getPassword());
        userResponse.setFirstName(savedUser.getFirstName());
        userResponse.setLastName(savedUser.getLastName());
        userResponse.setCreatedAt(savedUser.getCreatedAt());
        userResponse.setUpdatedAt(savedUser.getUpdatedAt());

        return userResponse;
    }

    public User authenticate(LoginRequest loginRequest) {
        //Get the user
        User user=userRepository.findByEmail(loginRequest.getEmail());
        if (user==null)
            throw new RuntimeException("Invalid Credentials");
        //Validate password
        if (!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())){
            throw new RuntimeException("Invalid Credentials");
        };  // Uses to match raw pass and encoded pass from db
        return user;
    }
}
