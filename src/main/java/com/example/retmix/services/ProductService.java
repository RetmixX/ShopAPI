package com.example.retmix.services;

import com.example.retmix.dto.products.CreateOrUpdateProductDTO;
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

    public ProductDTO createProduct(CreateOrUpdateProductDTO createOrUpdateProductDTO) {
        Product createProduct = new Product(createOrUpdateProductDTO);
        productRepository.save(createProduct);
        return new ProductDTO(
                createProduct.getId(),
                createProduct.getName(),
                createProduct.getDescription(),
                createProduct.getPrice()
        );
    }

    public List<ProductDTO> allProducts() {
        return productRepository.findAll().stream()
                .map(product ->
                        new ProductDTO(
                                product.getId(),
                                product.getName(),
                                product.getDescription(),
                                product.getPrice()))
                .toList();
    }

    public ProductDTO updateProduct(int id, CreateOrUpdateProductDTO updateProductDTO) {
        Product updateProduct = productRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundError(String.format("Продукт с id:%d не найден", id)));
        updateProduct.setName(updateProductDTO.name());
        updateProduct.setPrice(updateProductDTO.price());
        updateProduct.setDescription(updateProductDTO.description());
        productRepository.save(updateProduct);
        return new ProductDTO(
                updateProduct.getId(),
                updateProduct.getName(),
                updateProduct.getDescription(),
                updateProduct.getPrice());
    }

    public void removeProduct(int id) {
        Product removeProduct = productRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundError(String.format("Продукт c id:%d не найден", id)));
        productRepository.delete(removeProduct);
    }
}
