package com.example.retmix.dto.orders;

import java.util.List;

public record OrderDTO(
        int id,
        List<Integer> products,
        int order_price
) {
}
