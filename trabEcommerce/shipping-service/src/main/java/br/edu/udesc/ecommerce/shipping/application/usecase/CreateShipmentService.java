package br.edu.udesc.ecommerce.shipping.application.usecase;

import br.edu.udesc.ecommerce.shipping.domain.model.Shipment;
import br.edu.udesc.ecommerce.shipping.domain.port.in.CreateShipmentUseCase;
import br.edu.udesc.ecommerce.shipping.domain.port.out.EventPublisher;
import br.edu.udesc.ecommerce.shipping.domain.port.out.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateShipmentService implements CreateShipmentUseCase {

    private final ShipmentRepository shipmentRepository;
    private final EventPublisher eventPublisher;

    @Override
    @Transactional
    public Shipment execute(UUID orderId, String destinationZip, BigDecimal weight, BigDecimal freightValue) {
        Shipment shipment = Shipment.create(orderId, destinationZip, weight, freightValue);
        Shipment saved = shipmentRepository.save(shipment);
        eventPublisher.publish("shipping.exchange", "envio.criado", saved);
        return saved;
    }
}
