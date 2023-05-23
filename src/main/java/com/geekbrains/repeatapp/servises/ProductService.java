package com.geekbrains.repeatapp.servises;

import com.geekbrains.repeatapp.dtos.ProductDto;
import com.geekbrains.repeatapp.entities.Category;
import com.geekbrains.repeatapp.entities.Product;
import com.geekbrains.repeatapp.exceptions.ResourceNotFoundException;
import com.geekbrains.repeatapp.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private CategoryService categoryService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public Page<Product> findAll(int pageIndex, int pageSize){
        return productRepository.findAll(PageRequest.of(pageIndex, pageSize));
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

    @Transactional
    public Product deleteById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("product with id = " + id + " not found. Removal denied."));
        productRepository.delete(product);
        return product;
    }

    @Transactional
    public void updateProduct(ProductDto productDto){
        Product product = findById(productDto.getId()).orElseThrow(()->new ResourceNotFoundException("there is no product with such id: " + productDto.getId()));
        product.setPrice(productDto.getPrice());
        product.setTitle(productDto.getTitle());
        if(!productDto.getCategoryTitle().equals(product.getCategory().getTitle())) {
            Category category = categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(() -> new ResourceNotFoundException("Category with title = " + productDto.getCategoryTitle() + " not found"));
            product.setCategory(category);
        }
    }

    public List<Product> findAllByPriceGreaterThanEqual(int minPrice) {
        return productRepository.findAllByPriceGreaterThanEqual(minPrice);
    }

    public Optional<Product> findByTitle(String title){
        return productRepository.findByTitle(title);
    }
}
