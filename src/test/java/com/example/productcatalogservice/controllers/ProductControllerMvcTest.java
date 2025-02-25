package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest( ProductController.class)
public class ProductControllerMvcTest {


@Autowired
    private MockMvc mockMvc;

@MockitoBean
    private IProductService productService;

@Autowired
private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private ObjectMapper objectMapper;

    @Test
public void Test_GetAllProducts_RunsSuccessfully()throws Exception {
//ARRANGE
    List<Product> productList=new ArrayList<>();
    Product product1=new Product();
    product1.setId(1L);
    product1.setName("Iphone");
    product1.setPrice(1000000D);

    Product product2=new Product();
    product2.setId(2L);

    product2.setName("Iphone 6");
    product2.setPrice(100000D);

    productList.add(product1);
    productList.add(product2);
when(productService.getAllProducts()).thenReturn(productList);

//ACT AND ASSERT
    mockMvc.perform(get("/products"))
            .andExpect(status().isOk())
            .andExpect(content().string(mapper.writeValueAsString(productList)))
            .andExpect(jsonPath("$[0].name").value("Iphone"))
            .andExpect(jsonPath("$[0].length()").value(3))
            .andExpect(jsonPath("$.length()").value(2));

}


    @Test

    public void Test_CreateProduct_RunsSuccessfully() throws Exception {
    ProductDto productDto=new ProductDto();
    productDto.setId(2L);
    productDto.setName("Ipad");
        productDto.setDescription("writing ipad");

        Product product=new Product();
        product.setId(2L);
        product.setName("Ipad");
        product.setDescription("writing ipad");

        when(productService.createProduct(any(Product.class))).thenReturn(product);


mockMvc.perform(post("/products").content(objectMapper.writeValueAsString(productDto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(objectMapper.writeValueAsString(productDto)))
        .andExpect(jsonPath("$.name").value("Ipad"))
        .andExpect(jsonPath("$.length()").value(3));


    }

    @Test
    public void Test_ReplaceProduct_RunsSuccessfully() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setId(2L);
        productDto.setName("Iphone");
        productDto.setDescription("writing ipad");


        Product updatedProduct = new Product();
        updatedProduct.setId(2L);
        updatedProduct.setName("Iphone 6");
        updatedProduct.setDescription("writing ipad");

        when(productService.replaceProduct(any(Long.class), any(Product.class))).thenReturn(updatedProduct);

        // ACT & ASSERT
        mockMvc.perform(put("/products/1").content(objectMapper.writeValueAsString(productDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Iphone 6"))
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    public void Test_GetProductById_RunsSuccessfully() throws Exception {
        // ARRANGE
        Product product = new Product();
        product.setId(2L);
        product.setName("Iphone");
        product.setDescription("writing ipad");


        when(productService.getProductById(1L)).thenReturn(product);

        // ACT & ASSERT
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Iphone"))
                .andExpect(jsonPath("$.length()").value(3));
    }



}

