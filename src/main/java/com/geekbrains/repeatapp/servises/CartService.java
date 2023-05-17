package com.geekbrains.repeatapp.servises;

import com.geekbrains.repeatapp.entities.Product;
import com.geekbrains.repeatapp.exceptions.ResourceNotFoundException;
import com.geekbrains.repeatapp.utils.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private Cart cart;
    @PostConstruct
    public void init(){
        this.cart = new Cart();
    }

    public Cart getCartForCurrentUser(){
        return cart;
    }

    public void addToCart(Long productId) {
        if (cart.add(productId)){
            return;
        }
        Product product = productService.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Невозможно добавить продукт в корзину. В БД нет такого productId"));
        cart.add(product);
    }
}
