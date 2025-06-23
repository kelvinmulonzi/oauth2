package com.example.Oath2.controller;

import com.example.Oath2.dto.UserRegistrationDto;
import com.example.Oath2.dto.UserResponseDto;
import com.example.Oath2.entities.User;
import com.example.Oath2.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import com.example.Oath2.dto.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        try {
            User user = userService.createUser(
                    registrationDto.getUsername(),
                    registrationDto.getPassword(),
                    registrationDto.getEmail()
            );
            return ResponseEntity.ok(new UserResponseDto(user.getId(), user.getUsername(), user.getEmail()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponseDto> getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(new UserResponseDto(user.getId(), user.getUsername(), user.getEmail()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        List<UserResponseDto> userDtos = users.stream()
                .map(user -> new UserResponseDto(user.getId(), user.getUsername(), user.getEmail()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }
}

