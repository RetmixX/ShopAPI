package com.example.retmix.services;

import com.example.retmix.dto.users.RegistrationUserDTO;
import com.example.retmix.dto.users.UserDTO;
import com.example.retmix.exceptions.PermissionDenied;
import com.example.retmix.exceptions.RegistrationError;
import com.example.retmix.exceptions.UserByTokenNotFountError;
import com.example.retmix.models.User;
import com.example.retmix.models.enums.AvailablePermission;
import com.example.retmix.repository.UserRepository;
import com.example.retmix.utils.TokenUtil;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final TokenUtil tokenUtil;


    public UserService(UserRepository repository, TokenUtil tokenUtil) {
        this.repository = repository;
        this.tokenUtil = tokenUtil;
    }

    public String createUser(RegistrationUserDTO registrationUserDTO) throws NoSuchAlgorithmException {
        repository.findByEmail(registrationUserDTO.email()).ifPresent(action-> {
            throw new RegistrationError("Невалидная почта");
        });

        User user = new User(registrationUserDTO);
        UserDTO dto = new UserDTO(user.getId(),user.getFullName(), user.getEmail());

        user.setToken(tokenUtil.generateToken(dto));
        repository.save(user);

        return user.getToken();
    }

    public User getUserByToken(String token){
        return repository.findByToken(prepareToken(token)).orElseThrow(() -> new UserByTokenNotFountError("Токен не валиден"));
    }

    public void checkPermission(User user, AvailablePermission permission){
        user.getPermissions().stream().filter(p->p.getName()
                .equals(permission)).findFirst().orElseThrow(()-> new PermissionDenied("Доступ запрещен"));
    }

    public void removeToken(@NotNull String token){
        User user = repository.findByToken(prepareToken(token)).orElseThrow(() -> new UserByTokenNotFountError("Токен не валиден"));
        user.setToken(null);
    }

    public List<UserDTO> allUsers(){
        var test = repository.findAll();
        return repository.findAll().stream()
                .map(user->new UserDTO(user.getId(), user.getFullName(), user.getEmail())).toList();
    }

    private String prepareToken(String token){
        if (token == null){
            throw new UserByTokenNotFountError("Отсутствует токен");
        }
        return token.substring(token.indexOf(" ")).trim();
    }
}