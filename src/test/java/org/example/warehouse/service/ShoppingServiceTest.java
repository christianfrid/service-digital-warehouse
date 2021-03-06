package org.example.warehouse.service;

import org.example.warehouse.repository.ArticleRepository;
import org.example.warehouse.repository.ProductRepository;
import org.example.warehouse.types.Article;
import org.example.warehouse.types.ArticleWithQuantity;
import org.example.warehouse.types.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.example.warehouse.service.ShoppingService.COULD_NOT_FIND_PRODUCT;
import static org.example.warehouse.service.ShoppingService.COULD_NOT_SELL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
}
