package com.practice.repositories;

import com.practice.models.Inventory;

import java.util.HashMap;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

public class InventoryRepository {

    private final HashMap<Integer, Inventory> inventories;
    private int count;

    public InventoryRepository() {
        this.inventories = new HashMap<>();
    }

    public Inventory save(Inventory inventory) {

        count++;
        inventory.setId(count);
        inventories.put(inventory.getId(), inventory);

        return inventory;

    }

    public Inventory findById(int id) {

        return inventories.get(id);

    }

}
