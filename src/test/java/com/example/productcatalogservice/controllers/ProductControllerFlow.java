package com.example.productcatalogservice.controllers;


import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.services.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest

public class ProductControllerFlow {

    @Autowired
    private ProductController productController;

@Autowired
    private IProductService productService;


    @Test
    public void Test_Create_Replace_GetProduct_RunsSuccessfully() {
        //Arrange
        ProductDto productDto = new ProductDto();
        productDto.setName("Iphone15");
        productDto.setId(10L);
//Act
        ProductDto response=productController.createProduct(productDto);

        ResponseEntity<ProductDto> responseEntity=productController.getProduct(response.getId());
        productDto.setName("Iphone20");

       ProductDto response1= productController.replaceProduct(response.getId(),productDto);
        ResponseEntity<ProductDto> responseEntity1=productController.getProduct(response1.getId());

        //Assert

        assertEquals("Iphone15",responseEntity.getBody().getName());
        assertEquals("Iphone20",responseEntity1.getBody().getName());


    }
}
