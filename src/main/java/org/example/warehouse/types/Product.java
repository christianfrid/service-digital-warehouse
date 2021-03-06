package org.example.warehouse.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.lang.NonNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "API model for a product")
public class Product {
    @Id
    @NonNull
    private String name;
    private BigDecimal price;
    @JsonProperty("contain_articles")
    private List<ArticleWithQuantity> articlesWithQuantities;
}
