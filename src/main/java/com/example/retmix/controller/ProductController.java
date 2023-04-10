package com.example.retmix.controller;

import com.example.retmix.dto.products.CreateOrUpdateProductDTO;
import com.example.retmix.models.enums.AvailablePermission;
import com.example.retmix.services.ProductService;
import com.example.retmix.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> index() {
        return ResponseEntity.status(200).body(Map.of("data", productService.allProducts()));
    }

    @PostMapping
    public ResponseEntity<?> store(@RequestHeader("authorization") String token, @Valid @RequestBody CreateOrUpdateProductDTO createOrUpdateProductDTO) {
        userService.checkPermission(userService.getUserByToken(token), AvailablePermission.ADD_PRODUCT);
        return ResponseEntity.status(201).body(Map.of("data", productService.createProduct(createOrUpdateProductDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestHeader("authorization") String token, @PathVariable int id) {
        userService.checkPermission(userService.getUserByToken(token), AvailablePermission.REMOVE_PRODUCT);
        productService.removeProduct(id);
        return ResponseEntity.ok(Map.of("data", Map.of("message", "Продукт удален")));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@RequestHeader("authorization") String token, @PathVariable int id, @Valid @RequestBody CreateOrUpdateProductDTO updateProductDTO) {
        userService.checkPermission(userService.getUserByToken(token), AvailablePermission.EDIT_PRODUCT);
        return ResponseEntity.ok(Map.of("data", productService.updateProduct(id, updateProductDTO)));
    }
}
