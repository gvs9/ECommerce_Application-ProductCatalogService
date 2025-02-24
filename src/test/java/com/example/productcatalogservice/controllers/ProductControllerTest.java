package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private IProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void Test_GetProductById_withValidId_ReturnsProductSuccessfully() {
        //Arrange
        Long id = 1L;
        Product product = new Product();
        product.setId(id);
        product.setName("Iphone20");

//        when(productService.getProductById(any(Long.class))).thenReturn(new Product());

        when(productService.getProductById(id)).thenReturn(product);
        ResponseEntity<ProductDto> response = productController.getProduct(id);

        //Assert
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
        assertEquals("Iphone20", response.getBody().getName());


    }

    @Test
    @DisplayName("parameter 0 resulted in product not available exception")
    public void Test_GetProductById_withInvalidId_ThrowsException() {

        Exception ex = assertThrows(IllegalArgumentException.class, () -> productController.getProduct(0L));
        assertEquals("Product not Available", ex.getMessage());

        verify(productService,times(0)).getProductById(0L);

    }

    @Test
    public void Test_GetProductById_ProductServiceThrowsException() {
        when(productService.getProductById(any(Long.class))).thenThrow(new RuntimeException("something went wrong"));

         assertThrows(RuntimeException.class, () -> productController.getProduct(1L));
    }
}
