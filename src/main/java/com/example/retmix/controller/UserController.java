package com.example.retmix.controller;

import com.example.retmix.dto.users.AuthorizationUserDTO;
import com.example.retmix.dto.users.RegistrationUserDTO;
import com.example.retmix.dto.users.UserDTO;
import com.example.retmix.models.enums.AvailablePermission;
import com.example.retmix.services.UserService;
import com.example.retmix.utils.TokenUtil;
import jakarta.validation.Valid;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

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
    public ResponseEntity<?> store(@Valid @RequestBody RegistrationUserDTO registrationUserDTO) throws NoSuchAlgorithmException {
        return ResponseEntity.status(201).body(Map.of(
                "token", userService.createUser(registrationUserDTO)
        ));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("authorization") String tokenRequest){
        userService.removeToken(tokenRequest);
        return ResponseEntity.ok(Map.of("data", Map.of("message", "logout")));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthorizationUserDTO authData) throws NoSuchAlgorithmException {
        return ResponseEntity.ok(Map.of("token", userService.authorization(authData)));
    }
}
