package org.example.warehouse.types;

import com.mongodb.lang.NonNull;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Product {
    @Id
    @NonNull
    private String name;
    private List<ProductContainsArticles> containArticles;
}
