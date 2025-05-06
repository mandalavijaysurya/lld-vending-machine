package com.practice.models;


import com.practice.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private int id;
    private String uniqueId;
    private String name;
    private Category category;
    private double price;

}
