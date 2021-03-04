package org.example.warehouse.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.repository.InventoryRepository;
import org.example.warehouse.types.Inventory;
import org.example.warehouse.types.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@NoArgsConstructor
public class WarehouseService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public Inventory getInventory() {
        return inventoryRepository.findAll()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public void saveInventory(Inventory inventory) {
        inventoryRepository.save(inventory);
    }

}
