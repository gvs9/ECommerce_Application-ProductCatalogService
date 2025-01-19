package com.example.productcatalogservice.controllers;


import com.example.productcatalogservice.dtos.CategoryDto;
import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@RestController
@RequestMapping("/products")
public class ProductController {


    @Autowired
    private IProductService productService;


    @GetMapping
    public List<ProductDto> getAllProducts() {
        return new ArrayList<ProductDto>();
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long productId) {

        try {
            if (productId < 1) {
                throw new IllegalArgumentException("Product not Available");
            }


        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("called by", "Product Catalog Service");
        Product product = productService.getProductById(productId);

        if (product == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        ProductDto productDto = from(product);
        return new ResponseEntity<>(productDto, headers, HttpStatus.OK);
    }
    catch(IllegalArgumentException e)

    {
return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}

@PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto){

        return productDto;
    }

    @PutMapping("{id}")
    public ProductDto replaceProduct(@PathVariable int id,@RequestBody ProductDto productDto){
        return productDto;
    }

    private ProductDto from( Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        if(product.getCategory() !=null){
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(product.getCategory().getId());
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setDescription(product.getCategory().getDescription());
            productDto.setCategory(categoryDto);
        }

        return productDto;
    }


}
