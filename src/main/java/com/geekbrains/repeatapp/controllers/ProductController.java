package com.geekbrains.repeatapp.controllers;

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
    public List<Product> findAll(@RequestParam (defaultValue = "0") int minPrice, @RequestParam (defaultValue = "0") int maxPrice){
        if(minPrice == 0 && maxPrice == 0) {
            return productService.findAll();
        }
        return productService.findAllByPrice(minPrice, maxPrice);
    }

    @GetMapping("/products/{id}")
    public Product findById(@PathVariable Long id){
        return productService.findById(id).get();
    }

}
