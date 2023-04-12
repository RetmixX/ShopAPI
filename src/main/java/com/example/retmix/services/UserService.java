package com.example.retmix.services;

import com.example.retmix.dto.users.AuthorizationUserDTO;
import com.example.retmix.dto.users.RegistrationUserDTO;
import com.example.retmix.dto.users.UserDTO;
import com.example.retmix.exceptions.AuthorizationError;
import com.example.retmix.exceptions.PermissionDenied;
import com.example.retmix.exceptions.RegistrationError;
import com.example.retmix.exceptions.UserByTokenNotFountError;
import com.example.retmix.models.Permission;
import com.example.retmix.models.User;
import com.example.retmix.models.enums.AvailablePermission;
import com.example.retmix.repository.PermissionRepository;
import com.example.retmix.repository.UserRepository;
import com.example.retmix.utils.PasswordHash;
import com.example.retmix.utils.TokenUtil;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final PermissionRepository permissionRepository;
    private final TokenUtil tokenUtil;
    private final PasswordHash passwordHash;


    public UserService(UserRepository repository, PermissionRepository permissionRepository, TokenUtil tokenUtil, PasswordHash passwordHash) {
        this.repository = repository;
        this.permissionRepository = permissionRepository;
        this.tokenUtil = tokenUtil;
        this.passwordHash = passwordHash;
    }

    public String createUser(RegistrationUserDTO registrationUserDTO) throws NoSuchAlgorithmException {
        repository.findByEmail(registrationUserDTO.email()).ifPresent(action-> {
            throw new RegistrationError("Невалидная почта");
        });

        User user = new User(registrationUserDTO, passwordHash.hashPassword(registrationUserDTO.password()));
        UserDTO dto = new UserDTO(user.getId(),user.getFullName(), user.getEmail());

        user.setToken(tokenUtil.generateToken(dto));
        user.addPermission(permissionRepository.findByName(AvailablePermission.REMOVE_PRODUCT_FROM_CART));
        user.addPermission(permissionRepository.findByName(AvailablePermission.PLACE_ON_ORDER));
        user.addPermission(permissionRepository.findByName(AvailablePermission.ADD_PRODUCT_TO_CART));

        repository.save(user);

        return user.getToken();
    }

    public String authorization(AuthorizationUserDTO data) throws NoSuchAlgorithmException {
        User user = repository.findByEmail(data.email())
                .orElseThrow(()->new AuthorizationError("Неправильная почта или пароль"));

        if (!passwordHash.checkPassword(data.password(), user.getPassword())){
            throw new AuthorizationError("Неправильная почта или пароль");
        }
        String token = tokenUtil.generateToken(new UserDTO(user.getId(), user.getFullName(), user.getEmail()));
        user.setToken(token);
        repository.save(user);
        return token;
    }

    public User getUserByToken(String token){
        var tokenT = prepareToken(token);
        var test = repository.findByToken(prepareToken(token));
        return repository.findByToken(prepareToken(token)).orElseThrow(() -> new UserByTokenNotFountError("Токен не валиден"));
    }

    public void checkPermission(User user, AvailablePermission permission){
        user.getPermissions().stream().filter(p->p.getName()
                .equals(permission)).findFirst().orElseThrow(()-> new PermissionDenied("Доступ запрещен"));
    }

    public void removeToken(@NotNull String token){
        User user = repository.findByToken(prepareToken(token)).orElseThrow(() -> new UserByTokenNotFountError("Токен не валиден"));
        user.setToken(null);
        repository.save(user);
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