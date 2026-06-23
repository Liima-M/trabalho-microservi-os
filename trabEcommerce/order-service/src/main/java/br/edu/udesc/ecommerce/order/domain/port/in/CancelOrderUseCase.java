package br.edu.udesc.ecommerce.order.domain.port.in;

import java.util.UUID;

public interface CancelOrderUseCase {
    void execute(UUID orderId, UUID requesterId);
}
