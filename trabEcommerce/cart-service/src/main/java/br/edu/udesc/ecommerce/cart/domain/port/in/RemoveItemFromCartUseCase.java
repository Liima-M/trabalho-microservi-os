package br.edu.udesc.ecommerce.cart.domain.port.in;

import java.util.UUID;

public interface RemoveItemFromCartUseCase {
    void execute(UUID userId, UUID productId);
}
