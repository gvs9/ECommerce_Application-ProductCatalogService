package com.example.productcatalogservice.paymentgateway;

public interface PaymentGateway {

    String getPaymentLink(Long amount,String orderId,String phoneNumber,String name,String email);
}
