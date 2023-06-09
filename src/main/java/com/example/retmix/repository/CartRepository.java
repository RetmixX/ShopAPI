package com.example.retmix.repository;

import com.example.retmix.models.Cart;
import com.example.retmix.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {
    List<Cart> getCartsByUser(User user);
    Optional<Cart> findCartById(int id);
}
