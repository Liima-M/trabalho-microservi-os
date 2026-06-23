package br.edu.udesc.ecommerce.shipping.infrastructure.persistence.entity;

import br.edu.udesc.ecommerce.shipping.domain.model.Shipment;
import br.edu.udesc.ecommerce.shipping.domain.model.ShipmentStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "shipments")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class ShipmentJpaEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "order_id", nullable = false, unique = true, columnDefinition = "uuid")
    private UUID orderId;

    @Column(name = "tracking_code", nullable = false, unique = true, length = 50)
    private String trackingCode;

    @Column(name = "destination_zip_code", nullable = false, length = 10)
    private String destinationZipCode;

    @Column(nullable = false, precision = 8, scale = 3)
    private BigDecimal weight;

    @Column(name = "freight_value", nullable = false, precision = 14, scale = 2)
    private BigDecimal freightValue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ShipmentStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static ShipmentJpaEntity from(Shipment s) {
        return ShipmentJpaEntity.builder()
                .id(s.getId()).orderId(s.getOrderId()).trackingCode(s.getTrackingCode())
                .destinationZipCode(s.getDestinationZipCode()).weight(s.getWeight())
                .freightValue(s.getFreightValue()).status(s.getStatus())
                .createdAt(s.getCreatedAt()).updatedAt(s.getUpdatedAt())
                .build();
    }

    public Shipment toDomain() {
        return Shipment.builder().id(id).orderId(orderId).trackingCode(trackingCode)
                .destinationZipCode(destinationZipCode).weight(weight)
                .freightValue(freightValue).status(status)
                .createdAt(createdAt).updatedAt(updatedAt).build();
    }
}
