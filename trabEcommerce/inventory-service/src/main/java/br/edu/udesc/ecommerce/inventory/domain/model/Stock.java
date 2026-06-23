package br.edu.udesc.ecommerce.inventory.domain.model;

import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class Stock {
    private UUID id;
    private UUID productId;
    private int quantity;
    private int reservedQuantity;
    private LocalDateTime updatedAt;

    public int getAvailableQuantity() {
        return quantity - reservedQuantity;
    }

    public boolean isAvailable(int requested) {
        return getAvailableQuantity() >= requested;
    }

    /** RN18 — produto com estoque zerado fica "indisponível" mas visível. */
    public boolean isOutOfStock() {
        return getAvailableQuantity() <= 0;
    }
}
