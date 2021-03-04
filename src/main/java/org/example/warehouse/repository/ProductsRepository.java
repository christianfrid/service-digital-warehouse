package org.example.warehouse.repository;

import org.example.warehouse.types.Products;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductsRepository extends MongoRepository<Products, String> {
}
