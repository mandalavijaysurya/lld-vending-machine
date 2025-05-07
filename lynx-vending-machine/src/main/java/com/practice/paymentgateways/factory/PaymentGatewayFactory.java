package com.practice.paymentgateways.factory;

import com.practice.paymentgateways.PaymentGateway;
import com.practice.paymentgateways.api.RazorpayApi;
import com.practice.paymentgateways.api.StripeApi;
import com.practice.paymentgateways.implementations.RazorpayPaymentGateway;
import com.practice.paymentgateways.implementations.StripePaymentGateway;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
public class PaymentGatewayFactory {

    private final static StripeApi STRIPE_API = new StripeApi();
    private final static RazorpayApi RAZORPAY_API = new RazorpayApi();

    public static PaymentGateway getPaymentGateway(String name) {

        return switch (name) {
            case "stripe" -> new StripePaymentGateway(STRIPE_API);
            default -> new RazorpayPaymentGateway(RAZORPAY_API);
        };

    }

}
