package com.example.productcatalogservice.dtos;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {
    private Long id;

    private String imageUrl;
    private String name;
    private String description;
    private double price;

    private CategoryDto category;
}
