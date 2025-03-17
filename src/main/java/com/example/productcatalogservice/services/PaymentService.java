package com.example.productcatalogservice.services;

import com.example.productcatalogservice.paymentgateway.PaymentGateway;
import com.example.productcatalogservice.paymentgateway.PaymentGatewayChooserStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {


@Autowired
    private PaymentGatewayChooserStrategy paymentGatewayChooserStrategy;

    public String getPaymentLink(Long amount, String orderId, String phoneNumber, String name, String email) {

       PaymentGateway paymentGateway=paymentGatewayChooserStrategy.getOptimalPaymentGateway();
        return paymentGateway.getPaymentLink(amount,orderId,phoneNumber,name,email);
    }


}
