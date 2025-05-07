package com.practice.paymentgateways.implementations;

import com.practice.enums.PaymentStatus;
import com.practice.models.PaymentTransaction;
import com.practice.paymentgateways.PaymentGateway;
import com.practice.paymentgateways.api.RazorpayApi;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

public class RazorpayPaymentGateway implements PaymentGateway {

    private final RazorpayApi api;

    public RazorpayPaymentGateway(
            RazorpayApi razorpayApi
    ) {
        this.api = razorpayApi;
    }
    @Override
    public PaymentTransaction performPayment(PaymentTransaction paymentTransaction) {

        PaymentStatus status = api.performPayment();
        paymentTransaction.setPaymentStatus(status);

        return paymentTransaction;
    }
}
