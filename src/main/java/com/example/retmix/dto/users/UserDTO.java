package com.example.retmix.dto.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserDTO(
        int id,
        String fullName,
        String email
){
}
