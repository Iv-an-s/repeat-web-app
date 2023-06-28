package com.geekbrains.repeatapp.repositories;

import com.geekbrains.repeatapp.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

// для того чтобы использовать спецификации наследуемся от JpaSpecificationExecutor
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Optional<Product> findByTitle(String title);

    @Query("select p from Product p where p.price >= :minPrice and p.price <= :maxPrice")
    public List<Product> findAllByPrice(int minPrice, int maxPrice);

    List<Product>findAllByPriceLessThanEqual(int maxPrice);
    List<Product>findAllByPriceLessThanEqualAndPriceIsGreaterThanEqual(int maxPrice, int minPrice);
    List<Product>findAllByPriceBetween(int maxPrice, int minPrice);
    List<Product>findAllByPriceBetweenAndIdLessThanEqual(int maxPrice, int minPrice, Long maxId);

    List<Product> findAllByPriceGreaterThanEqual(int minPrice);
}
