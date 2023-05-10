package com.geekbrains.repeatapp.dtos;

import com.geekbrains.repeatapp.entities.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemDto {
    private Long productId;
    private String productTitle;
    private int quantity;
    private int pricePerProduct;
    private int price;

    public OrderItemDto(Product product) {
        this.productId = product.getId();
        this.productTitle = product.getTitle();
        this.quantity = 1;
        this.pricePerProduct = product.getPrice();
        this.price = pricePerProduct;
    }

    public void changeQuantity(int delta){
        quantity += delta;
        if (quantity < 0){
            quantity = 0;
        }
        price = pricePerProduct * quantity;
    }
}
