package com.example.productcatalogservice.repositories;

import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import jakarta.transaction.Transactional;
import org.apache.catalina.core.StandardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CategoryRepoTest {

    @Autowired
    private CategoryRepo categoryRepo;



    @Test
    @Transactional
    void testFetchTypes() {

        Optional<Category> optionalCategory = categoryRepo.findById(2L);
        System.out.println(optionalCategory.get().getName());
//        for (Product p: optionalCategory.get().getProduct())
//        {
//
//            System.out.println(p.getName());
//        }
    }

    @Test
    @Transactional
    void testFetchTypesAndModes() {
        Optional<Category> optionalCategory = categoryRepo.findById(4L);
        System.out.println(optionalCategory.get().getName());
        for (Product p: optionalCategory.get().getProduct())
        {

            System.out.println(p.getName());
        }
    }
}