package org.example.warehouse.types;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class Products {
    private List<Product> products;
}
