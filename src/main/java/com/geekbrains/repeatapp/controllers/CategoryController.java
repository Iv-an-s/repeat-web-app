package com.geekbrains.repeatapp.controllers;

import com.geekbrains.repeatapp.dtos.CategoryDto;
import com.geekbrains.repeatapp.exceptions.ResourceNotFoundException;
import com.geekbrains.repeatapp.servises.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/categories/{id}")
    public CategoryDto findById(@PathVariable Long id) {
        return new CategoryDto(categoryService.findByIdWithProducts(id).orElseThrow(() -> new ResourceNotFoundException
                ("Category with id = " + id + " not found")));
    }
}
