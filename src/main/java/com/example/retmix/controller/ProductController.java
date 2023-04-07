package com.example.retmix.controller;

import com.example.retmix.dto.products.CreateProductDTO;
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
    public ResponseEntity<?> index(){
        return ResponseEntity.status(200).body(Map.of("data", productService.allProducts()));
    }

    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody CreateProductDTO createProductDTO){
        return ResponseEntity.status(201).body(Map.of("data", productService.createProduct(createProductDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        productService.removeProduct(id);
        return ResponseEntity.ok(Map.of("data", Map.of("message", "Продукт удален")));
    }
}
