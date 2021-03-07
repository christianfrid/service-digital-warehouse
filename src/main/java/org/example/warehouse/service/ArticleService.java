package org.example.warehouse.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.example.warehouse.repository.ArticleRepository;
import org.example.warehouse.types.Article;
import org.example.warehouse.types.input.Inventory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ObjectMapper objectMapper;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
        this.objectMapper = new ObjectMapper();
    }

    public Inventory getInventory() {
        return Inventory.builder()
                .inventory(articleRepository.findAll())
                .build();
    }

    public void saveInventory(MultipartFile inventoryFile) {
        try {
            String json = IOUtils.toString(inventoryFile.getInputStream(), StandardCharsets.UTF_8.name());
            Inventory inventory = objectMapper.readValue(json, Inventory.class);
            inventory.getInventory().forEach(articleRepository::save);
        } catch (IOException e) {
            log.error("Couldn't read input file:", e);
        }
    }

    public Article getArticle(long artId) {
        return articleRepository.findByArtId(artId);
    }
}
