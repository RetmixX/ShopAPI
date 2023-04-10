package com.example.retmix.models;

import com.example.retmix.dto.users.RegistrationUserDTO;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Cart> cart;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    List<Order> orders;

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

    public void setPermissions(List<Permission> permissions) {
        this.userPermissions = permissions;
    }

    public void addPermission(Permission permission){
        this.userPermissions.add(permission);
    }

    public boolean removePermission(Permission permission){
        return this.userPermissions.remove(permission);
    }

    public String getFullName(){
        return String.format("%s %s %s", name, surname, patronymic);
    }
}
