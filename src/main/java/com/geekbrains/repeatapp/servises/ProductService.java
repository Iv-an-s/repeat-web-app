package com.geekbrains.repeatapp.servises;

import com.geekbrains.repeatapp.entities.Product;
import com.geekbrains.repeatapp.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id){
        return productRepository.findById(id);
    }

    public List<Product> findAllByPrice(int minPrice, int maxPrice){
        return productRepository.findAllByPrice(minPrice, maxPrice);
    }

    public List<Product> findAllByPriceLessThanEqual(int maxPrice) {
        return productRepository.findAllByPriceLessThanEqual(maxPrice);
    }

    public Product save(Product product){
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.delete(productRepository.findById(id).get());
    }

    public List<Product> findAllByPriceGreaterThanEqual(int minPrice) {
        return productRepository.findAllByPriceGreaterThanEqual(minPrice);
    }
}
