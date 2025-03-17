package com.example.productcatalogservice.paymentgateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PaymentGatewayChooserStrategy {

@Autowired
    private  RazorpayPaymentGateway razorpayPaymentGateway;

@Autowired
private StripePaymentGateway stripePaymentGateway;

public  PaymentGateway getOptimalPaymentGateway() {
   // return razorpayPaymentGateway;
//return stripePaymentGateway;

    Random random = new Random();

    int randomNumber = random.nextInt(1000);
    System.out.println("Random Number Generated: " + randomNumber);

    // If the number is even, use Stripe; otherwise, use Razorpay
    if (randomNumber % 2 == 0) {
        return stripePaymentGateway;
    } else {
        return razorpayPaymentGateway;
    }
}
}
