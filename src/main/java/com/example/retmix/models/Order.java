package com.example.retmix.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseModel implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "price")
    private int price;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "orders_products", joinColumns = {@JoinColumn(name = "order_id")},
    inverseJoinColumns = {@JoinColumn(name = "product_id")})
    private List<Product> productsOrder;


    public Order() {
    }

    public Order(User user, int price, List<Product> productsOrder) {
        this.user = user;
        this.price = price;
        this.productsOrder = productsOrder;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProductsOrder() {
        return productsOrder;
    }

    public int getPrice() {
        return price;
    }
}