package br.edu.udesc.ecommerce.cart.domain.port.in;

import java.util.UUID;

public interface ClearCartUseCase {
    void execute(UUID userId);
}
