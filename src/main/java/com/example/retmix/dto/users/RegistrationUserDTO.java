package com.example.retmix.dto.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegistrationUserDTO (
        @Size(min = 1, max = 100, message = "От 1 до 100")
        @NotNull(message = "Поле не заполнено")
        String name,
        @Size(min = 1, max = 100, message = "От 1 до 100")
        @NotNull(message = "Поле не заполнено")
        String surname,
        @Size(min = 1, max = 100, message = "От 1 до 100")
        @NotNull(message = "Поле не заполнено")
        String patronymic,
        @Size(min = 1, max = 100, message = "От 1 до 100")
        @NotNull(message = "Поле не заполнено")
        @Email
        String email,
        @Size(min = 1, max = 100, message = "От 1 до 100")
        @NotNull(message = "Поле не заполнено")
        String password
) {
}
