package com.example.retmix.services;

import com.example.retmix.dto.carts.CartDTO;
import com.example.retmix.exceptions.ObjectNotFoundError;
import com.example.retmix.exceptions.PermissionDenied;
import com.example.retmix.models.Cart;
import com.example.retmix.models.User;
import com.example.retmix.repository.CartRepository;
import com.example.retmix.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CartService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public CartService(ProductRepository productRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    public List<CartDTO> cartUser(User user){
        return user.getProductCart().stream()
                .map(cart->new CartDTO(
                        cart.getId(),
                        cart.getProduct().getId(),
                        cart.getProduct().getName(),
                        cart.getProduct().getDescription(),
                        cart.getProduct().getPrice())).sorted(Comparator.comparingInt(CartDTO::id))
                .toList();
    }

    public void addProductToCart(User user, int productId){
        cartRepository.save(new Cart(user, productRepository.findById(productId)
                .orElseThrow(()->new ObjectNotFoundError(String.format("Продукт с id:%d не найден", productId)))));


    }

    public void removeProductFromCart(User user, int cartId){
        Cart cart = cartRepository.findCartById(cartId)
                .orElseThrow(()
                        ->new ObjectNotFoundError(String.format("Товар с id:%d не найден", cartId)));
        if (!user.getProductCart().remove(cart)){
            throw new PermissionDenied("Доступ запрещен");
        }
        cartRepository.delete(cart);
    }
}
