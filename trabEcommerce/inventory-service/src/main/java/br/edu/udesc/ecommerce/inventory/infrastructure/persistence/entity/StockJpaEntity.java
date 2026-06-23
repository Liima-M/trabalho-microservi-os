package br.edu.udesc.ecommerce.inventory.infrastructure.persistence.entity;

import br.edu.udesc.ecommerce.inventory.domain.model.Stock;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "stocks")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class StockJpaEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "product_id", nullable = false, unique = true, columnDefinition = "uuid")
    private UUID productId;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "reserved_quantity", nullable = false)
    private int reservedQuantity;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static StockJpaEntity from(Stock s) {
        return StockJpaEntity.builder()
                .id(s.getId()).productId(s.getProductId())
                .quantity(s.getQuantity()).reservedQuantity(s.getReservedQuantity())
                .updatedAt(s.getUpdatedAt())
                .build();
    }

    public Stock toDomain() {
        return Stock.builder()
                .id(id).productId(productId).quantity(quantity)
                .reservedQuantity(reservedQuantity).updatedAt(updatedAt)
                .build();
    }
}
