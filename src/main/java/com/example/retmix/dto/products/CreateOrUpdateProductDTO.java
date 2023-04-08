package com.example.retmix.dto.products;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateOrUpdateProductDTO(
        @Size(min = 1, max = 100, message = "От 1 до 100")
        @NotNull(message = "Поле не заполнено")
        String name,
        @Size(min = 1, max = 200, message = "От 1 до 200")
        @NotNull(message = "Поле не заполнено")
        String description,
        @Min(value = 0, message = "От 0")
        @NotNull(message = "Поле не заполнено")
        Integer price
)
{
}
