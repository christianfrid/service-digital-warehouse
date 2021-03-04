package org.example.warehouse.types;

import com.mongodb.lang.NonNull;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class ArticleWithQuantity {
    @Id
    @NonNull
    private long artId;
    private int amountOf;
}
