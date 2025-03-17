package com.example.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitiatePaymentDto {
    private Long amount;

    private String orderId;

    private String phoneNumber;

    private String name;

    private String email;

}
