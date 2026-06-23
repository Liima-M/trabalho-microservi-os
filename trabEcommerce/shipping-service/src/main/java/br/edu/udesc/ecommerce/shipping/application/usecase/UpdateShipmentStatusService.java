package br.edu.udesc.ecommerce.shipping.application.usecase;

import br.edu.udesc.ecommerce.shipping.domain.model.Shipment;
import br.edu.udesc.ecommerce.shipping.domain.model.ShipmentStatus;
import br.edu.udesc.ecommerce.shipping.domain.port.in.UpdateShipmentStatusUseCase;
import br.edu.udesc.ecommerce.shipping.domain.port.out.EventPublisher;
import br.edu.udesc.ecommerce.shipping.domain.port.out.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateShipmentStatusService implements UpdateShipmentStatusUseCase {

    private final ShipmentRepository shipmentRepository;
    private final EventPublisher eventPublisher;

    @Override
    @Transactional
    public void execute(UUID shipmentId, ShipmentStatus newStatus) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new IllegalArgumentException("Envio não encontrado: " + shipmentId));
        shipment.updateStatus(newStatus);
        shipmentRepository.save(shipment);
        if (newStatus == ShipmentStatus.ENTREGUE) {
            eventPublisher.publish("shipping.exchange", "pedido.entregue", shipment);
        }
    }
}
