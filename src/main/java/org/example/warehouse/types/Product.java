package org.example.warehouse.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.lang.NonNull;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Product {
    @Id
    @NonNull
    private String name;
    private BigDecimal price;
    @JsonProperty("contain_articles")
    private List<ArticleWithQuantity> articlesWithQuantities;
}
