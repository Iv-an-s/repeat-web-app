package com.geekbrains.repeatapp.servises;

import com.geekbrains.repeatapp.dtos.OrderDetailsDto;
import com.geekbrains.repeatapp.dtos.OrderItemDto;
import com.geekbrains.repeatapp.dtos.ProductDto;
import com.geekbrains.repeatapp.entities.Order;
import com.geekbrains.repeatapp.entities.OrderItem;
import com.geekbrains.repeatapp.entities.User;
import com.geekbrains.repeatapp.exceptions.ResourceNotFoundException;
import com.geekbrains.repeatapp.repositories.OrderRepository;
import com.geekbrains.repeatapp.utils.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartService cartService;
    private final ProductService productService;

    @Transactional
    public void createOrder(String username, OrderDetailsDto orderDetailsDto){
        User user = userService.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("Не удалось найти пользователя при оформлении заказа c именем: " + username));
        Cart cart = cartService.getCartForCurrentUser();
        Order order = new Order();
        order.setUser(user);
        order.setPrice(cart.getTotalPrice());
        List<OrderItem> items = new ArrayList<>();
        for (OrderItemDto i : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setPrice(i.getPrice());
            orderItem.setPricePerProduct(i.getPricePerProduct());
            orderItem.setQuantity(i.getQuantity());
            orderItem.setProduct(productService.findById(i.getProductId()).orElseThrow(()-> new ResourceNotFoundException("Невозможно найти продукт при оформлении заказа с id = " + i.getProductId())));
            items.add(orderItem);
        }
        order.setItems(items);
        order.setPhone(orderDetailsDto.getPhone());
        order.setAddress(orderDetailsDto.getAddress());
        save(order);
        cartService.clearCart();
    }

    public void save(Order order){
        orderRepository.save(order);
    }

    public List<Order> getOrdersForCurrentUser(String username){
        return orderRepository.findAllByUsername(username);
    }
}
