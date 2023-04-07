package com.example.retmix.controller;

import com.example.retmix.dto.users.RegistrationUserDTO;
import com.example.retmix.dto.users.UserDTO;
import com.example.retmix.exceptions.IdiNaxyiException;
import com.example.retmix.response.HelloResponse;
import com.example.retmix.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> index() {
        return ResponseEntity.ok(Map.of("data", userService.allUsers()));
    }

    @PostMapping
    public ResponseEntity<UserDTO> store(@Valid @RequestBody RegistrationUserDTO registrationUserDTO){
        return ResponseEntity.status(201).body(userService.createUser(registrationUserDTO));
    }
}
