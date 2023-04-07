package com.example.retmix.services;

import com.example.retmix.dto.users.RegistrationUserDTO;
import com.example.retmix.dto.users.UserDTO;
import com.example.retmix.exceptions.RegistrationError;
import com.example.retmix.models.User;
import com.example.retmix.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserDTO createUser(RegistrationUserDTO registrationUserDTO){
        repository.findByEmail(registrationUserDTO.email()).ifPresent(action-> {
            throw new RegistrationError("Невалидная почта");
        });
        User user = new User(registrationUserDTO);
        repository.save(user);
        return new UserDTO(user.getId(),user.getFullName(), user.getEmail());
    }

    public List<UserDTO> allUsers(){
        return repository.findAll().stream()
                .map(user->new UserDTO(user.getId(), user.getFullName(), user.getEmail())).toList();
    }


}
