package com.practice.repositories;

import com.practice.models.Product;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

public class ProductRepository {

    private final HashMap<String, Product> products;
    private int count;

    public ProductRepository() {
        products = new HashMap<>();
    }

    public Product save(Product product) {

        count++;
        product.setId(count);
        products.put(product.getUniqueId(),product);

        return product;

    }

    public Product getProductByUniqueId(String uniqueId) {

        return products.get(uniqueId);

    }

    public List<Product> getAllProducts() {

        return new ArrayList<>(products.values());

    }

}
