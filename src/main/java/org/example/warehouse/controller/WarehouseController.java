package org.example.warehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.example.warehouse.service.WarehouseService;
import org.example.warehouse.types.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

    @PostMapping(value = "/upload/inventory", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadInventory(
            @RequestParam("file") MultipartFile file
    ) {
        String input = null;
        try {
            log.info(file.getContentType());
            log.info(file.getOriginalFilename());
            String json = IOUtils.toString(file.getInputStream(), StandardCharsets.UTF_8.name());
            return ResponseEntity.ok(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body(input);
    }
}
