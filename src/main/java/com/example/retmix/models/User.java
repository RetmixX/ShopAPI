package com.example.retmix.models;

import com.example.retmix.dto.users.RegistrationUserDTO;
import com.example.retmix.exceptions.PermissionError;
import com.example.retmix.models.enums.AvailablePermission;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseModel{
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "patronymic")
    private String patronymic;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_permissions", joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    private List<Permission> userPermissions;

    @Column(name = "token")
    private String token;

    @OneToMany(mappedBy = "user")
    private List<Cart> productCart;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Order> orders;

    public User(RegistrationUserDTO data){
        this.name = data.name();
        this.surname = data.surname();
        this.patronymic = data.patronymic();
        this.email = data.email();
        this.password = data.password();
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String username) {
        this.surname = username;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Permission> getPermissions() {
        return userPermissions;
    }

    public void addPermission(Permission newPermission){
        this.userPermissions.stream().filter(p->p.getId() == newPermission.getId()).findFirst().ifPresent(per->{
            throw new PermissionError(String.format("Право '%s' уже есть у данного пользователя",
                    per.getName().getPermission()));
        });

        this.userPermissions.add(newPermission);
    }

    public void removePermission(Permission permission){
        this.userPermissions.stream().filter(p->p.getId() == p.getId())
                .findFirst().orElseThrow(()->new PermissionError("Данных прав у пользователя нету"));

        this.userPermissions.remove(permission);
    }

    public void setPermissions(List<Permission> permissions) {
        this.userPermissions = permissions;
    }

    public List<Cart> getProductCart(){
        return this.productCart;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public String getFullName(){
        return String.format("%s %s %s", name, surname, patronymic);
    }
}
