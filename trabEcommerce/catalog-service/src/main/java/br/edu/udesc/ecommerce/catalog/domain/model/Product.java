package br.edu.udesc.ecommerce.catalog.domain.model;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class Product {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private UUID categoryId;
    private UUID sellerId;
    private List<String> imageUrls; // RN19: ao menos uma imagem (URL)
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Product create(String name, String description, BigDecimal price,
                                  UUID categoryId, UUID sellerId, List<String> imageUrls) {
        if (imageUrls == null || imageUrls.isEmpty()) {
            throw new IllegalArgumentException("Produto deve ter ao menos uma imagem. (RN19)");
        }
        return Product.builder()
                .id(UUID.randomUUID()).name(name).description(description)
                .price(price).categoryId(categoryId).sellerId(sellerId)
                .imageUrls(imageUrls).active(true)
                .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now())
                .build();
    }
}
