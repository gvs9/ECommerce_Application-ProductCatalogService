package com.example.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FakeStoreProductDto implements Serializable {

    private Long id;

    private String title;

    private String description;

    private Double price;

    private String category;

    private String Image;

    private FakeStoreRatingDto fakeStoreRating;
}
