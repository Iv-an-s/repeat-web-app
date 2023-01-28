package com.geekbrains.repeatapp.controllers;

import com.geekbrains.repeatapp.dtos.ProductDto;
import com.geekbrains.repeatapp.entities.Product;
import com.geekbrains.repeatapp.exceptions.MarketError;
import com.geekbrains.repeatapp.servises.CategoryService;
import com.geekbrains.repeatapp.servises.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    private CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public Page<ProductDto> findAll(@RequestParam(defaultValue = "1", name = "p") int pageIndex) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        return productService.findAll(pageIndex - 1, 10).map(ProductDto::new);
    }

//    @GetMapping("/products")
//    public List<ProductDto> findAll(@RequestParam (name = "min_price", defaultValue = "0") int minPrice,
//                                 @RequestParam (name = "max_price", defaultValue = "0") int maxPrice){
//        if(minPrice == 0 && maxPrice == 0) {
//            return productService.findAll().stream().map(s -> new ProductDto(s)).collect(Collectors.toList());
//        }
//        if(minPrice == 0 && maxPrice !=0){
//            return productService.findAllByPriceLessThanEqual(maxPrice).stream().map(s -> new ProductDto(s)).collect(Collectors.toList());
//        }
//        if(minPrice != 0 && maxPrice ==0){
//            return productService.findAllByPriceGreaterThanEqual(minPrice).stream().map(s -> new ProductDto(s)).collect(Collectors.toList());
//        }
//        return productService.findAllByPrice(minPrice, maxPrice).stream().map(s -> new ProductDto(s)).collect(Collectors.toList());
//    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        if (!product.isPresent()){
            return new ResponseEntity<>(new MarketError("product with id:"+ id + " is not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ProductDto(product.get()), HttpStatus.OK);
    }

    @PostMapping
    public ProductDto save(@RequestBody ProductDto productDto) {
        Product product = new Product();
        product.setPrice(productDto.getPrice());
        product.setTitle(productDto.getTitle());
        product.setCategory(categoryService.findByTitle(productDto.getCategoryTitle()).get());
        return new ProductDto(productService.save(product));
    }

    @GetMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
