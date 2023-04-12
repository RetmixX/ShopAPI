package com.example.retmix.repository;

import com.example.retmix.models.Permission;
import com.example.retmix.models.enums.AvailablePermission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Integer> {
    List<Permission> findAll();

    Permission findByName(AvailablePermission name);
}
