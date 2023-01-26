package com.geekbrains.repeatapp.controllers;

import com.geekbrains.repeatapp.dtos.ProductDto;
import com.geekbrains.repeatapp.entities.Product;
import com.geekbrains.repeatapp.servises.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> findAll(@RequestParam (name = "min_price", defaultValue = "0") int minPrice,
                                 @RequestParam (name = "max_price", defaultValue = "0") int maxPrice){
        if(minPrice == 0 && maxPrice == 0) {
            return productService.findAll();
        }
        if(minPrice == 0 && maxPrice !=0){
            return productService.findAllByPriceLessThanEqual(maxPrice);
        }
        return productService.findAllByPrice(minPrice, maxPrice);
    }

    @GetMapping("/products/{id}")
    public ProductDto findById(@PathVariable Long id){
        return new ProductDto(productService.findById(id).get());
    }

}
