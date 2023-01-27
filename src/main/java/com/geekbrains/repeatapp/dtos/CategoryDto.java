package com.geekbrains.repeatapp.dtos;

import com.geekbrains.repeatapp.entities.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String title;
    private List<ProductDto> products;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
        this.products = category.getProducts().stream().map(ProductDto::new).collect(Collectors.toList());
    }
}
