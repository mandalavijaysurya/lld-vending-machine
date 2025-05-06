package com.practice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CartProduct {

    private int id;
    private Product product;
    private int quantity;

}
