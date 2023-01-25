package com.geekbrains.repeatapp.repositories;

import com.geekbrains.repeatapp.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.price >= :minPrice and p.price <= :maxPrice")
    public List<Product> findAllByPrice(int minPrice, int maxPrice);
}
