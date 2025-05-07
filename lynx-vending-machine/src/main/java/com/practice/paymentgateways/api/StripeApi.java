package com.practice.paymentgateways.api;

import com.practice.enums.PaymentStatus;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
public class StripeApi {

    public PaymentStatus performPayment() {
        return PaymentStatus.SUCCESS;
    }

}
