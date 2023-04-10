package com.example.retmix.models;

import com.example.retmix.dto.products.CreateOrUpdateProductDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "products")
public class Product extends BaseModel{
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private int price;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<Cart> cartUser;

    public Product() {
    }

    public Product(CreateOrUpdateProductDTO data){
        this.name = data.name();
        this.description = data.description();
        this.price = data.price();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
