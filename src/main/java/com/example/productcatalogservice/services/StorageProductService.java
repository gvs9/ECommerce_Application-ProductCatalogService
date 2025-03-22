package com.example.productcatalogservice.services;

import com.example.productcatalogservice.dtos.UserDto;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@Primary

public class StorageProductService implements IProductService{





    private ProductRepo productRepo;

    private RestTemplate restTemplate;

    public StorageProductService(ProductRepo productRepo,RestTemplate restTemplate) {
        this.productRepo = productRepo;
        this.restTemplate = restTemplate;

    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> productOptional = productRepo.findById(id);
        return productOptional.orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {


         List<Product>products=productRepo.findAll();

        return products;
    }


    @Override
    public Product createProduct(Product product) {
        Product result = productRepo.save(product);
        return result;
    }

    @Override
    public Product replaceProduct(Long id, Product product) {

        return null;
    }
    @Override
    public Product getProductBasedOnScope( Long pid,Long uid){
        Product product=productRepo.findById(pid).get();
       UserDto userDto= restTemplate.getForEntity("http://localhost:9000/UserService/users/{uid}",UserDto.class,uid).getBody();
        System.out.println("EMAIL " +userDto.getEmail());
       return product;
    }

}
