package com.practice.paymentgateways.implementations;

import com.practice.enums.PaymentStatus;
import com.practice.models.PaymentTransaction;
import com.practice.paymentgateways.PaymentGateway;
import com.practice.paymentgateways.api.StripeApi;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
public class StripePaymentGateway implements PaymentGateway {

    private final StripeApi api;

    public StripePaymentGateway(
            StripeApi api
    ) {
        this.api = api;
    }

    @Override
    public PaymentTransaction performPayment(PaymentTransaction paymentTransaction) {

        PaymentStatus status = api.performPayment();
        paymentTransaction.setPaymentStatus(status);

        return paymentTransaction;
    }
}
