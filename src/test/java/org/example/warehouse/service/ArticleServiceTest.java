package org.example.warehouse.service;

import lombok.SneakyThrows;
import org.example.warehouse.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import static org.example.warehouse.TestUtils.createInventoryTestFile;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ArticleServiceTest {
    private ArticleRepository articleRepository;

    private ArticleService articleService;

    @BeforeEach
    void init() {
        articleRepository = mock(ArticleRepository.class);
        articleService = spy(new ArticleService(
                articleRepository
        ));
    }

    @Test
    @SneakyThrows
    void verifyUploadTwoArticles() {
        MultipartFile file = createInventoryTestFile();
        articleService.saveInventory(file);
        verify(articleRepository, times(4)).save(any());
    }
}
