package com.practice.models;


import com.practice.enums.Category;
import lombok.*;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Product {

    private int id;
    private String uniqueId;
    private String name;
    private Category category;
    private double price;

}
