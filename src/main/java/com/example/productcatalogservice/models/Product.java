package com.example.productcatalogservice.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity

public class Product  extends BaseModel{

    private String name;

    private String description;

    private String imageUrl;

    private Double price;
@JsonManagedReference
@ManyToOne(cascade = CascadeType.ALL)
    private Category category;

private Boolean isPrimeSpecific;

}
