package org.example.warehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/v1", produces = "application/json")
public class WarehouseController {

    @Operation(summary = "Upload inventory")
    @GetMapping("/inventory")
    public ResponseEntity<String> uploadInventory() {
        return ResponseEntity.ok("Ok");
    }

    @Operation(summary = "Upload products")
    @GetMapping("/products")
    public ResponseEntity<String> uploadProducts() {
        return ResponseEntity.ok("Ok");
    }

}
