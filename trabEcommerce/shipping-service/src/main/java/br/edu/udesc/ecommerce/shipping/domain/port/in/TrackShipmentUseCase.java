package br.edu.udesc.ecommerce.shipping.domain.port.in;

import br.edu.udesc.ecommerce.shipping.domain.model.Shipment;

public interface TrackShipmentUseCase {
    Shipment execute(String trackingCode);
}
