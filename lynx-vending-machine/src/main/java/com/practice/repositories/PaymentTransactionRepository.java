package com.practice.repositories;

import com.practice.models.PaymentTransaction;

import java.util.HashMap;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

public class PaymentTransactionRepository {

    private final HashMap<Integer, PaymentTransaction> paymentTransactions;
    private int count;


    public PaymentTransactionRepository() {
        this.paymentTransactions = new HashMap<>();
    }

    public PaymentTransaction save(PaymentTransaction paymentTransaction) {

        count++;
        paymentTransaction.setTransactionId(count);
        paymentTransactions.put(count, paymentTransaction);

        return paymentTransaction;

    }

    public PaymentTransaction getPaymentTransactionById(int id) {

        return paymentTransactions.get(id);

    }

}
