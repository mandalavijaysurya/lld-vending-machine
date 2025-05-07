package com.practice.models;

import lombok.*;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder

public class CartProduct {

    private int id;
    private Product product;
    private int quantity;

}
