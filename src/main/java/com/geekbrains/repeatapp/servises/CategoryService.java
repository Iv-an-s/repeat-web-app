package com.geekbrains.repeatapp.servises;

import com.geekbrains.repeatapp.dtos.ProductDto;
import com.geekbrains.repeatapp.entities.Category;
import com.geekbrains.repeatapp.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Optional<Category> findByTitle(String title){
        return categoryRepository.findByTitle(title);
    }

    public Optional<Category> findById(Long id){
        return categoryRepository.findById(id);
    }

    public Optional<Category> findByIdWithProducts(Long id){
        return categoryRepository.findByIdWithProducts(id);
    }
}
