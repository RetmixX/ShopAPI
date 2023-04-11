package com.example.retmix.repository;

import com.example.retmix.models.Order;
import com.example.retmix.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
    List<Order> getAllByUser(User user);
}
