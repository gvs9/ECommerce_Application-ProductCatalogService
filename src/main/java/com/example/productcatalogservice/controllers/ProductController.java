package com.example.productcatalogservice.controllers;


import com.example.productcatalogservice.dtos.CategoryDto;
import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
//    @Autowired
//    private List<IProductService> productService;






    @GetMapping
    public List<ProductDto> getAllProducts() {
        List<ProductDto>productDtos=new ArrayList<>();

        List<Product>products=productService.getAllProducts();

        for(Product product:products){
            productDtos.add(from(product));

        }
        return productDtos;
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long productId) {

        try {
            if (productId == 0) {
                throw new IllegalArgumentException("Product not Available");
            }
else if(productId<0){
    throw new IllegalArgumentException("are oyu stupid");
            }

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("called by", "Product Catalog Service");
        Product product = productService.getProductById(productId);

        if (product == null) {
            //return null;
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        ProductDto productDto = from(product);
       // return productDto;
        return new ResponseEntity<>(productDto, headers, HttpStatus.OK);
    }
    catch(IllegalArgumentException e)

    {
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
       // throw e;
    }
}

@PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto){

        Product product=from(productDto);
        Product response=productService.createProduct(product);
        return from(response);
    }

    @PutMapping("{id}")
    public ProductDto replaceProduct(@PathVariable Long id,@RequestBody ProductDto productDto){
Product product=from(productDto);
        Product result= productService.replaceProduct(id,product);
        return from(result);
    }



private Product from(ProductDto productDto){
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
       product.setId(productDto.getId());

        if(productDto.getCategory()!=null){
        Category category = new Category();
        category.setId(productDto.getCategory().getId());
        category.setName(productDto.getCategory().getName());
        product.setCategory(category);
            }
         return product;
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
