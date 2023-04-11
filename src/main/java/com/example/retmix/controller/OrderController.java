package com.example.retmix.controller;

import com.example.retmix.services.OrderService;
import com.example.retmix.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;

    public OrderController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<?> index(@RequestHeader("authorization") String token){
        return ResponseEntity.ok(Map.of("data", orderService.allOrders(userService.getUserByToken(token))));
    }
    @PostMapping
    public ResponseEntity<?> store(@RequestHeader("authorization") String token){
        return ResponseEntity.status(201).body(Map.of("data", Map.of(
                "order_id", orderService.ordering(userService.getUserByToken(token)).getId(),
                "message", "Заказ в процессе")));
    }
}
