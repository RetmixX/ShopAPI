package com.example.retmix.controller;

import com.example.retmix.models.enums.AvailablePermission;
import com.example.retmix.services.AdminService;
import com.example.retmix.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;

    public AdminController(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }

    @GetMapping("/permissions")
    public ResponseEntity<?> indexPermission(@RequestHeader("authorization") String token){
        userService.checkPermission(userService.getUserByToken(token), AvailablePermission.SHOW_PERMISSION);
        return ResponseEntity.ok(Map.of("data", adminService.allPermission()));
    }

    @PostMapping("/users/{userId}/permission/{permissionId}")
    public ResponseEntity<?> addPermission(@RequestHeader("authorization") String token,
                                           @PathVariable int userId,
                                           @PathVariable int permissionId){
        userService.checkPermission(userService.getUserByToken(token), AvailablePermission.ADD_PERMISSION);

        return ResponseEntity.status(201).body(Map.of(
                "message", "Право добавлено",
                "object", adminService.addPermissionUser(userId, permissionId)
        ));
    }

    @DeleteMapping("/users/{userId}/permission/{permissionId}")
    public ResponseEntity<?> removePermission(@RequestHeader("authorization") String token,
                                              @PathVariable int userId,
                                              @PathVariable int permissionId){
        userService.checkPermission(userService.getUserByToken(token), AvailablePermission.REMOVE_PERMISSION);

        return ResponseEntity.ok(Map.of(
                "message", "Право удалено",
                "object", adminService.removePermissionUser(userId, permissionId)));
    }
}
