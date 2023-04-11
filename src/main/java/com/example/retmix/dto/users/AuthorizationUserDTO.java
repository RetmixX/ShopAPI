package com.example.retmix.dto.users;

import jakarta.validation.constraints.NotNull;

public record AuthorizationUserDTO(
        @NotNull
        String email,
        @NotNull
        String password
) {
}
