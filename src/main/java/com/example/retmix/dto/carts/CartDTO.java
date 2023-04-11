package com.example.retmix.dto.carts;

public record CartDTO (
        int id,
        int product_id,
        String name,
        String description,
        int price
){
}
