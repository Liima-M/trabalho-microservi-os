package br.edu.udesc.ecommerce.shipping.domain.model;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class Shipment {
    private UUID id;
    private UUID orderId;
    private String trackingCode;
    private String destinationZipCode;
    private BigDecimal weight;
    private BigDecimal freightValue;
    private ShipmentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Shipment create(UUID orderId, String destinationZip,
                                   BigDecimal weight, BigDecimal freightValue) {
        return Shipment.builder()
                .id(UUID.randomUUID()).orderId(orderId)
                .trackingCode("BR" + System.currentTimeMillis() + "SP")
                .destinationZipCode(destinationZip)
                .weight(weight).freightValue(freightValue)
                .status(ShipmentStatus.POSTADO)
                .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now())
                .build();
    }

    public void updateStatus(ShipmentStatus newStatus) {
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
    }
}
