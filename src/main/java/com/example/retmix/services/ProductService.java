package com.example.retmix.services;

import com.example.retmix.dto.products.CreateProductDTO;
import com.example.retmix.dto.products.ProductDTO;
import com.example.retmix.exceptions.ObjectNotFoundError;
import com.example.retmix.models.Product;
import com.example.retmix.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDTO createProduct(CreateProductDTO createProductDTO){
        Product createProduct = new Product(createProductDTO);
        productRepository.save(createProduct);
        return new ProductDTO(
                createProduct.getId(),
                createProduct.getName(),
                createProduct.getDescription(),
                createProduct.getPrice()
        );
    }

    public List<ProductDTO> allProducts(){
        return productRepository.findAll().stream()
                .map(product->
                        new ProductDTO(
                                product.getId(),
                                product.getName(),
                                product.getDescription(),
                                product.getPrice()))
                .toList();
    }

    public void removeProduct(int id){
        Product removeProduct = productRepository.findById(id)
                .orElseThrow(()-> new ObjectNotFoundError(String.format("Продукт c id:%d не найден", id)));
        productRepository.delete(removeProduct);
        System.out.println();
    }
}
