package com.example.productcatalogservice.services;


import com.example.productcatalogservice.clients.FakeStoreApiClient;
import com.example.productcatalogservice.dtos.FakeStoreProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements IProductService {


    private RestTemplateBuilder restTemplateBuilder;


private FakeStoreApiClient fakeStoreApiClient;

private RedisTemplate<String,Object> redisTemplate;

public FakeStoreProductService(RestTemplateBuilder restTemplateBuilder, FakeStoreApiClient fakeStoreApiClient, RedisTemplate<String,Object> redisTemplate) {
    this.restTemplateBuilder = restTemplateBuilder;
    this.fakeStoreApiClient = fakeStoreApiClient;
    this.redisTemplate = redisTemplate;

}
@Override
    public Product getProductBasedOnScope( Long pid,Long uid){
    return null;
    }

//get product by id
    @Override
    public Product getProductById(Long id) {

    FakeStoreProductDto fakeStoreProductDto= null;
    fakeStoreProductDto=(FakeStoreProductDto)redisTemplate.opsForHash().get("Products",id);
          if(fakeStoreProductDto!=null){
    System.out.println("Found in Cache");
    return from(fakeStoreProductDto);
}

        fakeStoreProductDto=fakeStoreApiClient.getProductById(id);
        System.out.println("Found By Calling Fakestore");
          redisTemplate.opsForHash().put("Products",id,fakeStoreProductDto);

          return from(fakeStoreProductDto);
    }


    //get all products

    @Override
    public List<Product> getAllProducts(){
//        List<Product> products=new ArrayList<>();
//        RestTemplate restTemplate = restTemplateBuilder.build();
//       FakeStoreProductDto[]fakeStoreProductDtos= restTemplate.getForEntity("https://fakestoreapi.com/products", FakeStoreProductDto[].class).getBody();
//        assert fakeStoreProductDtos != null;
//        for(FakeStoreProductDto fakeStoreProductDto:fakeStoreProductDtos){
//           products.add(from(fakeStoreProductDto));
//       }
//        return products;

        List<FakeStoreProductDto> fakeStoreProductDtos = fakeStoreApiClient.getAllProducts();
        List<Product> products = new ArrayList<>();
        for (FakeStoreProductDto dto : fakeStoreProductDtos) {
            products.add(from(dto));
        }
        return products;

    }


    //create product
    @Override
    public Product createProduct(Product product){
        FakeStoreProductDto fakeStoreProductDtoReq = from(product);

        //  FakeStoreApiClient to create the product
        FakeStoreProductDto fakeStoreProductDtoResponse = fakeStoreApiClient.createProduct(fakeStoreProductDtoReq);


        return from(fakeStoreProductDtoResponse);
    }


    //replace product
@Override
public Product replaceProduct(Long id, Product product){
FakeStoreProductDto fakeStoreProductDtoReq=from(product);

FakeStoreProductDto fakeStoreProductDtoResponse = fakeStoreApiClient.replaceProduct(id, fakeStoreProductDtoReq);
return from(fakeStoreProductDtoResponse);
}

    public <T> ResponseEntity<T> requestForEntity(HttpMethod httpMethod,String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
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

    private FakeStoreProductDto from(Product product){
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getName());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setImage(product.getImageUrl());
        if(product.getCategory()!=null) {
            fakeStoreProductDto.setCategory(product.getCategory().getName());
        }
        return fakeStoreProductDto;
    }



}
