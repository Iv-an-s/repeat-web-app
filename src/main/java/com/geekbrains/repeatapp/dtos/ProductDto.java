package com.geekbrains.repeatapp.dtos;

import com.geekbrains.repeatapp.entities.Product;
import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String title;
    private int price;
    private String categoryTitle;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.categoryTitle = product.getCategory().getTitle();
    }

    public ProductDto(String title, int price, String categoryTitle) {
        this.title = title;
        this.price = price;
        this.categoryTitle = categoryTitle;
    }
}
