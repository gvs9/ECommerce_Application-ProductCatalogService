package com.example.productcatalogservice.services;

import com.example.productcatalogservice.models.Product;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IProductService {


    Product getProductById(Long id);

    List<Product> getAllProducts();

    Product createProduct(Product product);

    public Product replaceProduct(Long id, Product product);

    Product getProductBasedOnScope( Long pid,Long uid);
}
