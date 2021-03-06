package org.example.warehouse.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.example.warehouse.repository.ProductRepository;
import org.example.warehouse.types.Product;
import org.example.warehouse.types.UpdateProductSpec;
import org.example.warehouse.types.input.Products;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class ProductService {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.objectMapper = new ObjectMapper();
    }

    public Products getProducts() {
        return Products.builder()
                .products(productRepository.findAll())
                .build();
    }

    public void saveProducts(MultipartFile productsFile) {
        try {
            String json = IOUtils.toString(productsFile.getInputStream(), StandardCharsets.UTF_8.name());
            Products products = objectMapper.readValue(json, Products.class);
            products.getProducts().forEach(productRepository::save);
        } catch (IOException e) {
            log.error("Couldn't read input file:", e);
        }
    }

    public Product getProduct(String name) {
        return productRepository.findByName(name);
    }

    public void updateProduct(String name, UpdateProductSpec updateProductSpec) {
        Product product = productRepository.findByName(name);
        product.setPrice(updateProductSpec.getPrice());
        productRepository.save(product);
    }
}
