package com.practice.repositories;

import com.practice.models.Cart;

import java.util.HashMap;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

public class CartRepository {

    private final HashMap<Integer, Cart> carts;
    private int count;

    public CartRepository() {
        this.carts = new HashMap<>();
    }

    public Cart save(Cart cart) {

        count++;
        cart.setId(count);
        carts.put(cart.getId(), cart);

        return cart;
    }

    public Cart findById(int id) {

        return carts.get(id);

    }

}
