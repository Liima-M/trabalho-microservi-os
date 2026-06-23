package br.edu.udesc.ecommerce.inventory.infrastructure.persistence.entity;

import br.edu.udesc.ecommerce.inventory.domain.model.ReservationStatus;
import br.edu.udesc.ecommerce.inventory.domain.model.StockReservation;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "stock_reservations")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class StockReservationJpaEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "product_id", nullable = false, columnDefinition = "uuid")
    private UUID productId;

    @Column(name = "order_id", nullable = false, unique = true, columnDefinition = "uuid")
    private UUID orderId;

    @Column(nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReservationStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    public static StockReservationJpaEntity from(StockReservation r) {
        return StockReservationJpaEntity.builder()
                .id(r.getId()).productId(r.getProductId()).orderId(r.getOrderId())
                .quantity(r.getQuantity()).status(r.getStatus())
                .createdAt(r.getCreatedAt()).expiresAt(r.getExpiresAt())
                .build();
    }

    public StockReservation toDomain() {
        return StockReservation.builder()
                .id(id).productId(productId).orderId(orderId)
                .quantity(quantity).status(status)
                .createdAt(createdAt).expiresAt(expiresAt)
                .build();
    }
}
