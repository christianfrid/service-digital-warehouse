package org.example.warehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.service.ProductService;
import org.example.warehouse.types.Product;
import org.example.warehouse.types.UpdateProductSpec;
import org.example.warehouse.types.input.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/v1", produces = "application/json")
public class ProductContoller {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Get all current products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Cannot find data")
    })
    @GetMapping("/products")
    public ResponseEntity<Products> getProducts() {
        Products products = productService.getProducts();
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Get product by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Wrong input data"),
            @ApiResponse(responseCode = "404", description = "Cannot find data"),
            @ApiResponse(responseCode = "500", description = "Wrong input data")
    })
    @GetMapping("/products/product")
    public ResponseEntity<Product> getProduct(
            @RequestParam(value = "name") final String name
    ) {
        return ResponseEntity.ok(productService.getProduct(name));
    }

    @Operation(summary = "Update product spec having name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Wrong input data"),
            @ApiResponse(responseCode = "404", description = "Cannot find data"),
            @ApiResponse(responseCode = "500", description = "Wrong input data")
    })
    @PutMapping("/products/product/update")
    public ResponseEntity<Void> updateProduct(
            @RequestParam(value = "name") final String name,
            @RequestBody final UpdateProductSpec updateProductSpec
    ) {
        productService.updateProduct(name, updateProductSpec);
        return ResponseEntity.ok().build();
    }
}
