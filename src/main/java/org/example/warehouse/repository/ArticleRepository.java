package org.example.warehouse.repository;


import org.example.warehouse.types.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {
    Article findByArtId(long artId);
}