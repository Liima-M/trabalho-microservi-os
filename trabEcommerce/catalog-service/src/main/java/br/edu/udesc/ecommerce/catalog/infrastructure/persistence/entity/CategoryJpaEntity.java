package br.edu.udesc.ecommerce.catalog.infrastructure.persistence.entity;

import br.edu.udesc.ecommerce.catalog.domain.model.Category;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "categories")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class CategoryJpaEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    public static CategoryJpaEntity from(Category c) {
        return CategoryJpaEntity.builder().id(c.getId()).name(c.getName()).description(c.getDescription()).build();
    }

    public Category toDomain() {
        return Category.builder().id(id).name(name).description(description).build();
    }
}
