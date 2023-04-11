package com.example.retmix.controller;

import com.example.retmix.models.User;
import com.example.retmix.models.enums.AvailablePermission;
import com.example.retmix.services.CartService;
import com.example.retmix.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final UserService userService;
    private final CartService cartService;

    public CartController(UserService userService, CartService cartService) {
        this.userService = userService;
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<?> index(@RequestHeader("authorization") String token){
        return ResponseEntity.ok(Map.of("data", cartService.cartUser(userService.getUserByToken(token))));
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> store(@RequestHeader("authorization") String token, @PathVariable int id){
        User user = userService.getUserByToken(token);
        userService.checkPermission(user, AvailablePermission.ADD_PRODUCT_TO_CART);
        cartService.addProductToCart(user, id);

        return ResponseEntity.status(201)
                .body(Map.of("data", Map.of("message", "Продукт добавлен в корзину")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestHeader("authorization") String token, @PathVariable int id){
        User user = userService.getUserByToken(token);
        userService.checkPermission(user, AvailablePermission.REMOVE_PRODUCT_FROM_CART);
        cartService.removeProductFromCart(user, id);
        return ResponseEntity.ok(Map.of("data", Map.of("message", "Товар удален из корзины")));
    }

}
