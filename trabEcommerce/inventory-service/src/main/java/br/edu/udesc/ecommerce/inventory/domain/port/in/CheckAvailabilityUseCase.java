package br.edu.udesc.ecommerce.inventory.domain.port.in;

import java.util.UUID;

public interface CheckAvailabilityUseCase {
    int execute(UUID productId);
}
