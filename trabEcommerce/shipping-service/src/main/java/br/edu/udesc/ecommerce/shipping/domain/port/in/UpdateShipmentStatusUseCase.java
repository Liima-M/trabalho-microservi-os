package br.edu.udesc.ecommerce.shipping.domain.port.in;

import br.edu.udesc.ecommerce.shipping.domain.model.ShipmentStatus;
import java.util.UUID;

public interface UpdateShipmentStatusUseCase {
    void execute(UUID shipmentId, ShipmentStatus newStatus);
}
