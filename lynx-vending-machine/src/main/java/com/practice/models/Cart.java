package com.practice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    private int id;
    private List<CartProduct> cartProducts;
    private double netPrice;

}
