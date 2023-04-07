package com.example.retmix.dto.products;

public record ProductDTO(
        int id,
        String name,
        String description,
        int price
) {
}
