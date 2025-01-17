package com.example.productcatalogservice.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Product  extends BaseModel{

    private String name;

    private String description;


    private String imageUrl;


    private double price;

    private Category category;
}
