package org.example.warehouse.repository;

import org.example.warehouse.types.Inventory;
import org.example.warehouse.types.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InventoryRepository extends MongoRepository<Inventory, String> {
}