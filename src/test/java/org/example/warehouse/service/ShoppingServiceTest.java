package org.example.warehouse.service;

import org.example.warehouse.repository.ArticleRepository;
import org.example.warehouse.repository.ProductRepository;
import org.example.warehouse.types.Article;
import org.example.warehouse.types.ArticleWithQuantity;
import org.example.warehouse.types.Product;
import org.example.warehouse.types.ProductStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.example.warehouse.service.ShoppingService.COULD_NOT_FIND_PRODUCT;
import static org.example.warehouse.service.ShoppingService.COULD_NOT_SELL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShoppingServiceTest {

    private ArticleRepository articleRepository;
    private ProductRepository productRepository;

    private ShoppingService shoppingService;

    @BeforeEach
    void init() {
        articleRepository = mock(ArticleRepository.class);
        productRepository = mock(ProductRepository.class);
        shoppingService = spy(new ShoppingService(
                articleRepository,
                productRepository
        ));
    }

    @Test
    void verifyCanSellWithEnoughStock() {
        when(articleRepository.findByArtId(1L))
                .thenReturn(Article.builder()
                        .artId(1L)
                        .name("article_1")
                        .stock(1000)
                        .build());
        when(articleRepository.findByArtId(2L))
                .thenReturn(Article.builder()
                        .artId(2L)
                        .name("article_2")
                        .stock(10)
                        .build());
        when(productRepository.findByName("product_1"))
                .thenReturn(Product.builder()
                        .name("product_1")
                        .price(BigDecimal.valueOf(100.1))
                        .articlesWithQuantities(List.of(
                                ArticleWithQuantity.builder()
                                        .artId(1L)
                                        .stock(100)
                                        .build(),
                                ArticleWithQuantity.builder()
                                        .artId(2L)
                                        .stock(5)
                                        .build()
                        ))
                        .build());

        String returnStatement = shoppingService.sellProduct("product_1");
        assertEquals("Sold 1 of product_1", returnStatement);
    }

    @Test
    void verifyCannotSellWhenNotEnoughStock() {
        when(articleRepository.findByArtId(1L))
                .thenReturn(Article.builder()
                        .artId(1L)
                        .name("article_1")
                        .stock(1000)
                        .build());
        when(articleRepository.findByArtId(2L))
                .thenReturn(Article.builder()
                        .artId(2L)
                        .name("article_2")
                        .stock(0) // <-- 0 in stock!
                        .build());
        when(productRepository.findByName("product_1"))
                .thenReturn(Product.builder()
                        .name("product_1")
                        .price(BigDecimal.valueOf(100.1))
                        .articlesWithQuantities(List.of(
                                ArticleWithQuantity.builder()
                                        .artId(1L)
                                        .stock(100)
                                        .build(),
                                ArticleWithQuantity.builder()
                                        .artId(2L)
                                        .stock(5)
                                        .build()
                        ))
                        .build());

        String returnStatement = shoppingService.sellProduct("product_1");
        assertEquals(COULD_NOT_SELL, returnStatement);
    }

    @Test
    void verifyCannotSellNonExistingProduct() {
        when(productRepository.findByName("product_1"))
                .thenReturn(Product.builder()
                        .name("product_1")
                        .build());
        String returnStatement = shoppingService.sellProduct("product_2");
        verify(articleRepository, times(0)).findByArtId(anyLong());
        verify(productRepository, times(0)).save(any());
        assertEquals(COULD_NOT_FIND_PRODUCT, returnStatement);
    }

    @Test
    void verifyCanSell() {
        // There's enough stock of articles
        Map<Long, Integer> neededArtIdStock = Map.of(
                1L, 7,
                2L, 12
        );
        Map<Long, Integer> currentArtIdStock = Map.of(
                1L, 7,
                2L, 12
        );
        assertTrue(shoppingService.canSell(neededArtIdStock, currentArtIdStock));

        // There's not enough stock of articles
        neededArtIdStock = Map.of(
                1L, 7,
                2L, 12
        );
        currentArtIdStock = Map.of(
                1L, 1,
                2L, 12
        );
        assertFalse(shoppingService.canSell(neededArtIdStock, currentArtIdStock));

        // Article 2L is missing from inventory
        neededArtIdStock = Map.of(
                1L, 7,
                2L, 12
        );
        currentArtIdStock = Map.of(
                1L, 1
        );
        assertFalse(shoppingService.canSell(neededArtIdStock, currentArtIdStock));
    }

    @Test
    void verifyGetAvailableProductStockWithDivison() {
        int evenNumbers = shoppingService.divide(3, 3);
        assertEquals(1, evenNumbers);

        int hasDecimalsIfDouble = shoppingService.divide(3, 7);
        assertEquals(2, hasDecimalsIfDouble);

        int divideWithZeroGivesZero = shoppingService.divide(3, 0);
        assertEquals(0, divideWithZeroGivesZero);

        int impossibleStockDoesntCrash = shoppingService.divide(0, -1);
        assertEquals(0, impossibleStockDoesntCrash);
    }

    @Test
    void verifyCreateProductStockResponse() {
        Product product = Product.builder()
                .name("product_1")
                .articlesWithQuantities(List.of(
                        ArticleWithQuantity.builder()
                                .artId(1L)
                                .stock(3)
                        .build(),
                        ArticleWithQuantity.builder()
                                .artId(2L)
                                .stock(4)
                                .build()))
                .build();
        Map<Long, Integer> inventory = Map.of(
                1L, 7,
                2L, 12
        );

        ProductStock productStock = shoppingService.toProductStock(product, inventory);
        assertNotNull(productStock);
        assertEquals(2, productStock.getStock());
        assertEquals("product_1", productStock.getName());
    }
}
