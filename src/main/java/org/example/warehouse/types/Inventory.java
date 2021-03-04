package org.example.warehouse.types;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class Inventory {
    private List<Article> inventory;
}
