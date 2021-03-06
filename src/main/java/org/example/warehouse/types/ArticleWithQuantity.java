package org.example.warehouse.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.lang.NonNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "API model for specifying current quantity of article having an id")
public class ArticleWithQuantity {
    @Id
    @NonNull
    @JsonProperty("art_id")
    private long artId;
    @JsonProperty("amount_of")
    private int stock;
}
