package org.example.warehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.service.ArticleService;
import org.example.warehouse.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping(value = "/v1", produces = "application/json")
public class UploadController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ArticleService articleService;

    @Operation(summary = "Upload products from file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Wrong input data"),
            @ApiResponse(responseCode = "500", description = "Wrong input data")
    })
    @PostMapping(value = "/products/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadProducts(
            @RequestParam("file") MultipartFile file
    ) {
        productService.saveProducts(file);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Upload inventory from file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Wrong input data"),
            @ApiResponse(responseCode = "500", description = "Wrong input data")
    })
    @PostMapping(value = "/inventory/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadInventory(
            @RequestParam("file") MultipartFile file
    ) {
        articleService.saveInventory(file);
        return ResponseEntity.ok().build();
    }
}
