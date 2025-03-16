package com.example.productcatalogservice.services;


import com.example.productcatalogservice.dtos.SortParam;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.repositories.ProductRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    private ProductRepo productRepo;

    public Page<Product> searchProducts(String query, Integer pageNumber, Integer pageSize, List<SortParam>sortParams) {
        //Sort sort = Sort.by( "price").and(Sort.by("id").descending()) ;
        Sort sort=null;
        if(!sortParams.isEmpty()){
if(sortParams.get(0).getSortType().equals("asc"))
    sort=Sort.by(sortParams.get(0).getParamName());
else
    sort=Sort.by(sortParams.get(0).getParamName()).descending();

        }
        for(int i=0;i<sortParams.size();i++){
            if(sortParams.get(i).getSortType().equals("asc"))
                sort.and(Sort.by(sortParams.get(i).getParamName()));
            else
                sort.and(Sort.by(sortParams.get(i).getParamName()).descending());
        }


        return productRepo.findProductByName(query, PageRequest.of(pageNumber,pageSize,sort));

    }


}
