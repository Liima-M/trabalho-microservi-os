package br.edu.udesc.ecommerce.shipping.domain.port.in;

import br.edu.udesc.ecommerce.shipping.domain.model.Shipment;
import java.math.BigDecimal;
import java.util.UUID;

public interface CreateShipmentUseCase {
    Shipment execute(UUID orderId, String destinationZip, BigDecimal weight, BigDecimal freightValue);
}
