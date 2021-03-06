package org.example.warehouse.service;

import lombok.SneakyThrows;
import org.example.warehouse.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import static org.example.warehouse.TestUtils.createProductsTestFile;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    void init() {
        productRepository = mock(ProductRepository.class);
        productService = spy(new ProductService(
                productRepository
        ));
    }

    @Test
    @SneakyThrows
    void verifyUploadTwoProducts() {
        MultipartFile file = createProductsTestFile();
        productService.saveProducts(file);
        verify(productRepository, times(2)).save(any());
    }
}
