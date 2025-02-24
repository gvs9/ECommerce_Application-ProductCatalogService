package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest( ProductController.class)
public class ProductControllerMvcTest {


@Autowired
    private MockMvc mockMvc;

@MockitoBean
    private IProductService productService;

@Autowired
private ObjectMapper mapper = new ObjectMapper();

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
            .andExpect(content().string(mapper.writeValueAsString(productList)));

}

}
