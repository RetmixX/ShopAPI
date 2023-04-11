package com.example.retmix.repository;

import com.example.retmix.models.Permission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PermissionRepository extends CrudRepository<Permission, Integer> {
    List<Permission> findAll();
}
