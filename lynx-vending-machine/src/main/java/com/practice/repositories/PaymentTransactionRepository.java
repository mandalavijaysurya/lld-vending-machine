package com.practice.repositories;

import com.practice.models.PaymentTransaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        if(!paymentTransactions.containsKey(paymentTransaction.getTransactionId())) {

            count++;
            paymentTransaction.setTransactionId(count);

        }

        paymentTransactions.put(count, paymentTransaction);

        return paymentTransaction;

    }

    public PaymentTransaction getPaymentTransactionById(int id) {

        return paymentTransactions.get(id);

    }

    public List<PaymentTransaction> getAllPaymentTransactions() {

        return new ArrayList<>(paymentTransactions.values());

    }

}
