package com.example.productcatalogservice.clients;


import com.example.productcatalogservice.dtos.FakeStoreProductDto;
import com.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
 public class FakeStoreApiClient {
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;




    public FakeStoreProductDto getProductById(Long id) {

        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = requestForEntity(HttpMethod.GET, "https://fakestoreapi.com/products/{product id}", null, FakeStoreProductDto.class, id);
        if (fakeStoreProductDtoResponseEntity.getBody() != null && fakeStoreProductDtoResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
            return (fakeStoreProductDtoResponseEntity.getBody());
        }
        return null;
    }

    public List<FakeStoreProductDto> getAllProducts() {

        ResponseEntity<FakeStoreProductDto[]> responseEntity = requestForEntity(
                HttpMethod.GET,
                "https://fakestoreapi.com/products",
                null,
                FakeStoreProductDto[].class
        );


        if (responseEntity.getBody() != null && responseEntity.getStatusCode().is2xxSuccessful()) {
            // Converting  array to a list and return it
            return Arrays.asList(responseEntity.getBody());
        }

        // Return an empty list if the retrieval failed
        return Collections.emptyList();
    }


    public  FakeStoreProductDto replaceProduct(Long id, FakeStoreProductDto fakeStoreProductDtoReq) {
    RestTemplate restTemplate = restTemplateBuilder.build();
    FakeStoreProductDto fakeStoreProductDto = requestForEntity(HttpMethod.PUT, "https://fakestoreapi.com/products/{product id}", fakeStoreProductDtoReq, FakeStoreProductDto.class, id).getBody();
    return fakeStoreProductDto;
}


public FakeStoreProductDto createProduct(FakeStoreProductDto fakeStoreProductDtoReq) {

        ResponseEntity<FakeStoreProductDto> responseEntity=requestForEntity(HttpMethod.POST,"https://fakestoreapi.com/products/{product id}", fakeStoreProductDtoReq, FakeStoreProductDto.class);
    if (responseEntity.getBody() != null && responseEntity.getStatusCode().is2xxSuccessful()) {

        return responseEntity.getBody();
    }


    return null;




}

    public  <T > ResponseEntity < T > requestForEntity(HttpMethod httpMethod, String url, @Nullable Object request, Class<T> responseType, Object...
            uriVariables) throws RestClientException {
            RestTemplate restTemplate = restTemplateBuilder.build();
            RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
            ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
            return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
        }
    }
