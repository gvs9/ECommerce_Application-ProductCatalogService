package com.example.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SortParam {

    private String paramName;
    private SortType sortType;

}
