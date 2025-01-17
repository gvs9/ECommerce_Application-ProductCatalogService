package com.example.productcatalogservice.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public abstract class BaseModel {

    private int id;

    private Date createdAt;

    private Date lastUpdatedAt;

    private State state;

}
