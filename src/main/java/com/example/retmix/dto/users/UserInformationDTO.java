package com.example.retmix.dto.users;

import java.util.List;

public record UserInformationDTO(
        int id,
        String full_name,
        List<String> permissions
) {
}
