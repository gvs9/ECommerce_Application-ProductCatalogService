package com.example.productcatalogservice.repositories;

import com.example.productcatalogservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {


    Page<Product> findProductByName(String query, Pageable pageable);


    Optional<Product> findById(Long id);


    List<Product>findProductByPriceBetween(Double lower, Double higher);

    List<Product> findAllByIsPrimeSpecific(Boolean value);

    List<Product> findAllByIsPrimeSpecificTrue();


List<Product> findAllByOrderByIdDesc();

@Query ("SELECT p.name FROM Product p WHERE p.id=?1")
String findProductNameFromId(Long id);

@Query("select c.name from Product p join Category c on p.category.id=c.id where p.id=:productId")
String findCategoryNameFromProductId(Long productId);

    Long id(Long id);
}
