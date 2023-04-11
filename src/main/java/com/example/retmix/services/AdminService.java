package com.example.retmix.services;

import com.example.retmix.dto.permissions.PermissionDTO;
import com.example.retmix.dto.users.UserInformationDTO;
import com.example.retmix.exceptions.ObjectNotFoundError;
import com.example.retmix.models.Permission;
import com.example.retmix.models.User;
import com.example.retmix.repository.PermissionRepository;
import com.example.retmix.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;

    public AdminService(PermissionRepository permissionRepository, UserRepository userRepository) {
        this.permissionRepository = permissionRepository;
        this.userRepository = userRepository;
    }

    public List<PermissionDTO> allPermission(){
        return permissionRepository.findAll().stream()
                .map(p->new PermissionDTO(p.getId(), p.getName().getPermission()))
                .toList();
    }

    public UserInformationDTO addPermissionUser(int idUser, int idPermission){
        User editUser = findUser(idUser);
        editUser.addPermission(findPermission(idPermission));
        userRepository.save(editUser);

        return new UserInformationDTO(
                editUser.getId(),
                editUser.getFullName(),
                editUser.getPermissions().stream().map(p->p.getName().getPermission()).toList()
                );
    }

    public UserInformationDTO removePermissionUser(int idUser, int idPermission){
        User editUser = findUser(idUser);
        editUser.removePermission(findPermission(idPermission));
        userRepository.save(editUser);
        return new UserInformationDTO(
                editUser.getId(),
                editUser.getFullName(),
                editUser.getPermissions().stream().map(p->p.getName().getPermission()).toList()
        );
    }

    private User findUser(int userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new ObjectNotFoundError(String.format("Пользователь с id:%d не найден", userId)));
    }

    private Permission findPermission(int permissionId){
        return permissionRepository
                .findById(permissionId)
                .orElseThrow(
                        ()->new ObjectNotFoundError(String.format("Прво доступа с id:%d не найдено", permissionId)));
    }
}
