package br.edu.udesc.ecommerce.inventory.domain.port.in;

import java.util.UUID;

public interface ReleaseReservationUseCase {
    void execute(UUID orderId);
}
