package com.practice.repositories;

import com.practice.models.Inventory;
import com.practice.models.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        if(!inventories.containsKey(inventory.getId())) {

            count++;
            inventory.setId(count);

        }

        inventories.put(inventory.getId(), inventory);

        return inventory;

    }

    public void saveAll(List<Inventory> inventories) {

        inventories.forEach(this::save);

    }

    public void saveAll(Inventory... inventories) {

        for (Inventory inventory : inventories) {
            this.save(inventory);
        }

    }

    public Inventory findById(int id) {

        return inventories.get(id);

    }

    public Optional<Inventory> findByProductUniqueId(String productUniqueId) {

        return inventories.values().stream().filter(inventory -> inventory.getProduct().getUniqueId().equals(productUniqueId)).findFirst();

    }

    public List<Inventory> getAllInventories () {
        return inventories.values().stream().toList();
    }



}
