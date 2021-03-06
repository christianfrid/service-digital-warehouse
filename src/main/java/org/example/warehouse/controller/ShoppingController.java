package org.example.warehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.service.ShoppingService;
import org.example.warehouse.types.ProductStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/v1", produces = "application/json")
public class ShoppingController {

    @Autowired
    private ShoppingService shoppingService;

    @Operation(summary = "Sell one product having name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Wrong input data"),
            @ApiResponse(responseCode = "404", description = "Cannot find data"),
            @ApiResponse(responseCode = "500", description = "Wrong input data")
    })
    @PutMapping("/shop/sell")
    public ResponseEntity<String> sellProduct(
            @RequestParam(value = "name") final String name
    ) {
        return ResponseEntity.ok(shoppingService.sellProduct(name));
    }

    @Operation(summary = "Get stock of all products given current inventory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Wrong input data"),
            @ApiResponse(responseCode = "404", description = "Cannot find data"),
            @ApiResponse(responseCode = "500", description = "Wrong input data")
    })
    @PutMapping("/shop/stock")
    public ResponseEntity<List<ProductStock>> getProductStock() {
        return ResponseEntity.ok(shoppingService.getProductStock());
    }
}
