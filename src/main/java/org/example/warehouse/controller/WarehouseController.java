package org.example.warehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.service.WarehouseService;
import org.example.warehouse.types.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/v1", produces = "application/json")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @Operation(summary = "Get all products")
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        log.info("Getting all products...");
        List<Product> products = warehouseService.getProducts();
        log.info("Got response " + products);
        return ResponseEntity.ok(products);
    }

}
