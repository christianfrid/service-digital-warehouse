package org.example.warehouse.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.lang.NonNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "API model for an article")
public class Article {
    @Id
    @NonNull
    @JsonProperty("art_id")
    private long artId;
    private String name;
    private int stock;
}
