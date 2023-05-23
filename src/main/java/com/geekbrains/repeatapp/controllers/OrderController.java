package com.geekbrains.repeatapp.controllers;

import com.geekbrains.repeatapp.dtos.OrderDetailsDto;
import com.geekbrains.repeatapp.dtos.OrderDto;
import com.geekbrains.repeatapp.servises.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody OrderDetailsDto orderDetailsDto, Principal principal) {
        orderService.createOrder(principal.getName(), orderDetailsDto);
    }

    @GetMapping
    public List<OrderDto> getOrdersForCurrentUser(Principal principal) {
        return orderService.getOrdersForCurrentUser(principal.getName()).stream().map(OrderDto::new).collect(Collectors.toList());
    }
}
