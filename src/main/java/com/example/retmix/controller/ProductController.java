package com.example.retmix.controller;

import com.example.retmix.dto.products.CreateOrUpdateProductDTO;
import com.example.retmix.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> index() {
        return ResponseEntity.status(200).body(Map.of("data", productService.allProducts()));
    }

    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody CreateOrUpdateProductDTO createOrUpdateProductDTO) {
        return ResponseEntity.status(201).body(Map.of("data", productService.createProduct(createOrUpdateProductDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        productService.removeProduct(id);
        return ResponseEntity.ok(Map.of("data", Map.of("message", "Продукт удален")));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @Valid @RequestBody CreateOrUpdateProductDTO updateProductDTO) {
        return ResponseEntity.ok(Map.of("data", productService.updateProduct(id, updateProductDTO)));
    }
}
