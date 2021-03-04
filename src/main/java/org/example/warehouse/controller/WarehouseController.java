package org.example.warehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.example.warehouse.service.WarehouseService;
import org.example.warehouse.types.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping(value = "/v1", produces = "application/json")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @Operation(summary = "Get current inventory")
    @GetMapping("/inventory")
    public ResponseEntity<Inventory> getInventory() {
        log.info("Getting all products...");
        Inventory inventory = warehouseService.getInventory();
        log.info("Got response " + inventory);
        return ResponseEntity.ok(inventory);
    }

    @PostMapping(value = "/inventory/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadInventory(
            @RequestParam("file") MultipartFile file
    ) {
        Inventory inventory = null;
        try {
            String json = IOUtils.toString(file.getInputStream(), StandardCharsets.UTF_8.name());
            ObjectMapper objectMapper = new ObjectMapper();
            inventory = objectMapper.readValue(json, Inventory.class);
            warehouseService.saveInventory(inventory);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }
}
