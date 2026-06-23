package br.edu.udesc.ecommerce.inventory.domain.model;

import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class StockReservation {
    private UUID id;
    private UUID productId;
    private UUID orderId;
    private int quantity;
    private ReservationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt; // RN05: 15 min

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}
