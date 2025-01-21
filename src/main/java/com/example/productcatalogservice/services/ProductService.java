package com.example.productcatalogservice.services;


import com.example.productcatalogservice.dtos.FakeStoreProductDto;
import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;



//get product by id
    @Override
    public Product getProductById(Long id){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity= restTemplate.getForEntity("https://fakestoreapi.com/products/{product id}", FakeStoreProductDto.class,id);
   if(fakeStoreProductDtoResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200))){
       return from(fakeStoreProductDtoResponseEntity.getBody());
   }
    return null;
    }


    //get all products

    @Override
    public List<Product> getAllProducts(){
        List<Product> products=new ArrayList<>();
        RestTemplate restTemplate = restTemplateBuilder.build();
       FakeStoreProductDto[]fakeStoreProductDtos= restTemplate.getForEntity("https://fakestoreapi.com/products", FakeStoreProductDto[].class).getBody();
       for(FakeStoreProductDto fakeStoreProductDto:fakeStoreProductDtos){
           products.add(from(fakeStoreProductDto));
       }
        return products;
    }


    //create product
    @Override
    public Product createProduct(Product product){
        return null;
    }


    //replace product
@Override
public Product replaceproduct(Long id, Product product){
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.put("https://fakestoreapi.com/products/{product id}",product,id);

        return null;
}




    private Product from(FakeStoreProductDto fakeStoreProductDto){

        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImage());
        Category category=new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }
}
