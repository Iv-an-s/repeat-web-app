package com.geekbrains.repeatapp.controllers;

import com.geekbrains.repeatapp.dtos.OrderDetailsDto;
import com.geekbrains.repeatapp.servises.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final CartService cartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody OrderDetailsDto order){
        cartService.clearCart();
    }
}
