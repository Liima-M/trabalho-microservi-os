package br.edu.udesc.ecommerce.shipping.domain.port.out;

import br.edu.udesc.ecommerce.shipping.domain.model.Shipment;
import java.util.Optional;
import java.util.UUID;

public interface ShipmentRepository {
    Shipment save(Shipment shipment);
    Optional<Shipment> findByTrackingCode(String trackingCode);
    Optional<Shipment> findById(UUID id);
}
