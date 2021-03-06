package org.example.warehouse.types;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "API model for specification for updating a product")
public class UpdateProductSpec {
    private BigDecimal price;
}
