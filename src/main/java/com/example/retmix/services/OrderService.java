package com.example.retmix.services;

import com.example.retmix.dto.orders.OrderDTO;
import com.example.retmix.exceptions.CartEmptyError;
import com.example.retmix.models.BaseModel;
import com.example.retmix.models.Cart;
import com.example.retmix.models.Order;
import com.example.retmix.models.User;
import com.example.retmix.repository.CartRepository;
import com.example.retmix.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    public OrderService(OrderRepository orderRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }


    public List<OrderDTO> allOrders(User user){
        return user.getOrders().stream()
                .map(o->
                        new OrderDTO(o.getId(),
                                o.getProductsOrder().stream().map(BaseModel::getId).toList(),
                                o.getPrice()))
                .toList();
    }

    public Order ordering(User user){
        if (user.getProductCart().isEmpty()){
            throw new CartEmptyError("Корзина пользователя пуста");
        }
        Order newOrder = new Order(
                user,
                user.getProductCart().stream().mapToInt(p->p.getProduct().getPrice()).sum(),
                user.getProductCart().stream().map(Cart::getProduct).toList());
        orderRepository.save(newOrder);
        cartRepository.deleteAll(user.getProductCart());
        return newOrder;
    }


}
