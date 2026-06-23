package br.edu.udesc.ecommerce.catalog.domain.model;

import lombok.*;
import java.util.UUID;

@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class Category {
    private UUID id;
    private String name;
    private String description;
}
