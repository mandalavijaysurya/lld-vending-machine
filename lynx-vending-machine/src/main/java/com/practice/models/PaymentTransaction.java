package com.practice.models;

import com.practice.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PaymentTransaction {

    private int transactionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private PaymentStatus paymentStatus;
    private double paymentAmount;
    private String description;

}
