package com.example.productcatalogservice.repositories;

import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProductRepoTest {

    @Autowired
    private ProductRepo productRepo;

    @Test
    @Transactional
   public void testJpaQueries() {
        List<Product> productList = productRepo.findProductByPriceBetween(100000D,150000D);

        Optional<Product> optionalProduct = productRepo.findById(1L);
        optionalProduct.ifPresent(product -> System.out.println(product.getName()));

        String productName=productRepo.findProductNameFromId(2L);
        System.out.println( productRepo.findCategoryNameFromProductId(1L));
    }

//@Test
//    public void insertIntoAWSDb(){
//
//        Product product = new Product();
//        product.setId(1L);
//        product.setName("git");
//        product.setDescription("test2git");
//
//        Category category = new Category();
//        category.setId(4L);
//        category.setName("test2gitesh2");
//        product.setCategory(category);
//        productRepo.save(product);
//
//
//    }

}