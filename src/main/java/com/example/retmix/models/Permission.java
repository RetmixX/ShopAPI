package com.example.retmix.models;

import com.example.retmix.models.enums.AvailablePermission;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "permissions")
public class Permission extends BaseModel{
    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private AvailablePermission name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "userPermissions")
    private List<User> permissionUsers;

    public Permission() {
    }

    public AvailablePermission getName() {
        return name;
    }
}
