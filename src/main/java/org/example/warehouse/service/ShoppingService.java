package org.example.warehouse.service;

import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.repository.ArticleRepository;
import org.example.warehouse.repository.ProductRepository;
import org.example.warehouse.types.Article;
import org.example.warehouse.types.ArticleWithQuantity;
import org.example.warehouse.types.Product;
import org.example.warehouse.types.ProductStock;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ShoppingService {

    public static final String COULD_NOT_SELL = "Could not sell";
    public static final String COULD_NOT_FIND_PRODUCT = "Product name didn't exist";

    private final ArticleRepository articleRepository;
    private final ProductRepository productRepository;

    public ShoppingService(ArticleRepository articleRepository, ProductRepository productRepository) {
        this.articleRepository = articleRepository;
        this.productRepository = productRepository;
    }

    public List<ProductStock> getProductStock() {
        // Extract full inventory
        Map<Long, Integer> inventoryStock = articleRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Article::getArtId, Article::getStock));

        // Loop all products and calculate available stock
        return productRepository.findAll()
                .stream()
                .map(product -> toProductStock(product, inventoryStock))
                .collect(Collectors.toList());
    }

    /**
     * protected method is package private. Then it's testable.
     */
    protected ProductStock toProductStock(Product product, Map<Long, Integer> inventoryStock) {
        Map<Long, Integer> neededStock = product.getArticlesWithQuantities()
                .stream()
                .collect(Collectors.toMap(ArticleWithQuantity::getArtId, ArticleWithQuantity::getStock));

        int calculatedProductStock = neededStock.entrySet().stream()
                .map(articleStock -> divide(articleStock.getValue(), inventoryStock.get(articleStock.getKey())))
                .min(Integer::compareTo)
                .orElse(0);

        return ProductStock.builder()
                .name(product.getName())
                .stock(calculatedProductStock)
                .build();
    }

    /**
     * protected method is package private. Then it's testable.
     */
    protected int divide(int neededStock, int availableStock) {
        if (neededStock <= 0 || availableStock <= 0) {
            log.info("Stock was zero or negative. neededStock={}, availableStock={}", neededStock, availableStock);
            return 0;
        }
        BigDecimal divided = new BigDecimal(availableStock / neededStock);
        divided = divided.setScale(0, RoundingMode.FLOOR);
        return divided.intValueExact();
    }

    public String sellProduct(String productName) {
        Product product = productRepository.findByName(productName);
        if (product == null) {
            log.info("Product name didn't exist");
            return COULD_NOT_FIND_PRODUCT;
        }

        // Get how many of each article type is needed
        Map<Long, Integer> neededStock = product.getArticlesWithQuantities()
                .stream()
                .collect(Collectors.toMap(ArticleWithQuantity::getArtId, ArticleWithQuantity::getStock));

        // Get current stock for each needed article
        Map<Long, Integer> currentStock = neededStock.keySet()
                .stream()
                .map(articleRepository::findByArtId)
                .collect(Collectors.toMap(Article::getArtId, Article::getStock));

        if (canSell(neededStock, currentStock)) {

            // Get needed articles from inventory
            List<Article> articles = neededStock.keySet()
                    .stream()
                    .map(articleRepository::findByArtId)
                    .collect(Collectors.toList());

            // Update stock (newStock = current - needed)
            articles.forEach(article ->
                    article.setStock(currentStock.get(article.getArtId()) - neededStock.get(article.getArtId()))
            );

            // Save articles to inventory
            articles.forEach(articleRepository::save);
            log.info("Sold 1 of {}. New article stock in db is: {}", productName, articles);

        } else {
            log.info("Not enough articles in stock for product: {}", productName);
            return COULD_NOT_SELL;
        }

        return "Sold 1 of " + productName;
    }

    /**
     * Verify all requested articles are in (enough) stock.
     * protected method is package private. Then it's testable.
     */
    protected boolean canSell(Map<Long, Integer> neededStock, Map<Long, Integer> currentStock) {
        return !neededStock.isEmpty() &&
                !currentStock.isEmpty() &&
                currentStock.entrySet()
                        .stream()
                        .allMatch(articleStock -> articleStock.getValue() >= neededStock.get(articleStock.getKey()));
    }
}
