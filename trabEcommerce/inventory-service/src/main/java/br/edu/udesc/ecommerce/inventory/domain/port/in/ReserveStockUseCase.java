package br.edu.udesc.ecommerce.inventory.domain.port.in;

import java.util.UUID;

public interface ReserveStockUseCase {
    void execute(UUID productId, UUID orderId, int quantity);
}
