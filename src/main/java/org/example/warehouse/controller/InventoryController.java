package org.example.warehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.service.ArticleService;
import org.example.warehouse.types.Article;
import org.example.warehouse.types.input.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/v1", produces = "application/json")
public class InventoryController {

    @Autowired
    private ArticleService articleService;

    @Operation(summary = "Get whole current inventory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Cannot find data")
    })
    @GetMapping("/inventory")
    public ResponseEntity<Inventory> getInventory() {
        Inventory inventory = articleService.getInventory();
        return ResponseEntity.ok(inventory);
    }

    @Operation(summary = "Get article by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Wrong input data"),
            @ApiResponse(responseCode = "404", description = "Cannot find data"),
            @ApiResponse(responseCode = "500", description = "Wrong input data")
    })
    @GetMapping("/inventory/article")
    public ResponseEntity<Article> getArticle(
            @RequestParam(value = "art_id") final long artId
    ) {
        return ResponseEntity.ok(articleService.getArticle(artId));
    }
}
