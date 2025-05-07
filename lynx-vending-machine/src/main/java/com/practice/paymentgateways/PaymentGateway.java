package com.practice.paymentgateways;

import com.practice.models.PaymentTransaction;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
public interface PaymentGateway {

    PaymentTransaction performPayment(PaymentTransaction paymentTransaction);
}
