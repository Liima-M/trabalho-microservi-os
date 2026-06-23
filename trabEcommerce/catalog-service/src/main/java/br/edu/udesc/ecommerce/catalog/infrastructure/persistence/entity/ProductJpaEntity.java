package br.edu.udesc.ecommerce.catalog.infrastructure.persistence.entity;

import br.edu.udesc.ecommerce.catalog.domain.model.Product;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class ProductJpaEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal price;

    @Column(name = "category_id", nullable = false, columnDefinition = "uuid")
    private UUID categoryId;

    @Column(name = "seller_id", nullable = false, columnDefinition = "uuid")
    private UUID sellerId;

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "url", columnDefinition = "TEXT")
    @OrderColumn(name = "position")
    private List<String> imageUrls;

    @Column(nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static ProductJpaEntity from(Product p) {
        return ProductJpaEntity.builder()
                .id(p.getId()).name(p.getName()).description(p.getDescription())
                .price(p.getPrice()).categoryId(p.getCategoryId()).sellerId(p.getSellerId())
                .imageUrls(p.getImageUrls()).active(p.isActive())
                .createdAt(p.getCreatedAt()).updatedAt(p.getUpdatedAt())
                .build();
    }

    public Product toDomain() {
        return Product.builder()
                .id(id).name(name).description(description).price(price)
                .categoryId(categoryId).sellerId(sellerId).imageUrls(imageUrls)
                .active(active).createdAt(createdAt).updatedAt(updatedAt)
                .build();
    }
}
