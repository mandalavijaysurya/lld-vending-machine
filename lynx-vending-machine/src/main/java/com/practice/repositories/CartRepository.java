package com.practice.repositories;

import com.practice.models.Cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        if(!carts.containsKey(cart.getId())) {

            count++;
            cart.setId(count);

        }

        carts.put(cart.getId(), cart);

        return cart;
    }

    public Cart findById(int id) {

        return carts.get(id);

    }

    public void delete(int id) {

        carts.remove(id);

    }

    public List<Cart> findAll() {

        return new ArrayList<>(carts.values());

    }

}
