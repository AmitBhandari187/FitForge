package com.project.fitforge.service;

import com.project.fitforge.dto.RegisterRequest;
import com.project.fitforge.dto.UserResponse;
import com.project.fitforge.model.User;
import com.project.fitforge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse register(RegisterRequest registerRequest) {
        User user=User.builder()
                .email(registerRequest.getEmail())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .password(registerRequest.getPassword())
                .build();

        User savedUser= userRepository.save(user);
        return mapToResponse(savedUser);

    }

    private UserResponse mapToResponse(User savedUser) {
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
}
