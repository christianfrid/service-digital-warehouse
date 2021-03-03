package org.example.warehouse.types;

import lombok.Data;

import java.util.List;

@Data
public class Inventory {
    private List<Article> articles;
}
