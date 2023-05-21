package com.geekbrains.repeatapp.controllers;

import com.geekbrains.repeatapp.servises.CartService;
import com.geekbrains.repeatapp.utils.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;


    @GetMapping
    public Cart getCartForCurrentUser(){
        return cartService.getCartForCurrentUser();
    }

    @GetMapping("/add/{productId}")
    public void addToCart(@PathVariable Long productId){
        cartService.addItem(productId);
    }

    @GetMapping("/decrement/{productId}")
    public void decrementItem(@PathVariable Long productId){
        cartService.decrementItem(productId);
    }

    @GetMapping("/remove/{productId}")
    public void removeItem(@PathVariable Long productId){
        cartService.removeItem(productId);
    }
}
