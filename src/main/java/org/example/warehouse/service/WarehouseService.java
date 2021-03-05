package org.example.warehouse.service;

import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.repository.InventoryRepository;
import org.example.warehouse.repository.ProductsRepository;
import org.example.warehouse.types.Inventory;
import org.example.warehouse.types.Products;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WarehouseService {

    private InventoryRepository inventoryRepository;
    private ProductsRepository productsRepository;

    public WarehouseService(InventoryRepository inventoryRepository, ProductsRepository productsRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productsRepository = productsRepository;
    }

    public Inventory getInventory() {
        return inventoryRepository.findAll()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public void saveInventory(Inventory inventory) {
        inventoryRepository.save(inventory);
    }

    public Products getProducts() {
        return productsRepository.findAll()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public void saveProducts(Products products) {
        productsRepository.save(products);
    }

}
