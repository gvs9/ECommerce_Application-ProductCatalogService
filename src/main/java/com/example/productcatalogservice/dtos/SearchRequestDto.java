package com.example.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SearchRequestDto {

    private  String query;

    private  Integer pageLimit;

    private Integer pageNumber;

    private List<SortParam>sortParamList=new ArrayList<SortParam>();
}
