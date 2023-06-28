package com.geekbrains.repeatapp.servises;

import com.geekbrains.repeatapp.dtos.ProductDto;
import com.geekbrains.repeatapp.entities.Category;
import com.geekbrains.repeatapp.entities.Product;
import com.geekbrains.repeatapp.exceptions.ResourceNotFoundException;
import com.geekbrains.repeatapp.repositories.ProductRepository;
import com.geekbrains.repeatapp.repositories.specifications.ProductSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private CategoryService categoryService;

    private static final String FILTER_MIN_PRICE = "min_price";
    private static final String FILTER_MAX_PRICE = "max_price";
    private static final String FILTER_TITLE = "title";

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public Page<Product> findAll(int pageIndex, int pageSize, MultiValueMap<String, String> rqParams){
        return productRepository.findAll(constructSpecification(rqParams), PageRequest.of(pageIndex, pageSize));
    }

    private Specification<Product> constructSpecification(MultiValueMap<String, String> params){
        Specification<Product> spec = Specification.where(null);
        if(params.containsKey(FILTER_MIN_PRICE) && !params.getFirst(FILTER_MIN_PRICE).isBlank()){
//            BigDecimal minPrice = BigDecimal.valueOf(Double.parseDouble(params.getFirst(FILTER_MIN_PRICE)));
            int minPrice = Integer.parseInt(params.getFirst(FILTER_MIN_PRICE));
            spec = spec.and(ProductSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if(params.containsKey(FILTER_MAX_PRICE) && !params.getFirst(FILTER_MAX_PRICE).isBlank()){
            int maxPrice = Integer.parseInt(params.getFirst(FILTER_MAX_PRICE));
            spec = spec.and(ProductSpecifications.priceLessOrEqualsThan(maxPrice));
        }
        if(params.containsKey(FILTER_TITLE) && !params.getFirst(FILTER_TITLE).isBlank()){
            String title = params.getFirst(FILTER_TITLE);
            spec = spec.and(ProductSpecifications.titleLike(title));
        }
        return spec;
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
