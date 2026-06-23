package br.edu.udesc.ecommerce.shipping.infrastructure.persistence.repository;

import br.edu.udesc.ecommerce.shipping.domain.model.Shipment;
import br.edu.udesc.ecommerce.shipping.domain.port.out.ShipmentRepository;
import br.edu.udesc.ecommerce.shipping.infrastructure.persistence.entity.ShipmentJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaShipmentRepository implements ShipmentRepository {

    private final ShipmentJpaRepository jpaRepository;

    @Override
    public Shipment save(Shipment shipment) {
        return jpaRepository.save(ShipmentJpaEntity.from(shipment)).toDomain();
    }

    @Override
    public Optional<Shipment> findByTrackingCode(String trackingCode) {
        return jpaRepository.findByTrackingCode(trackingCode).map(ShipmentJpaEntity::toDomain);
    }

    @Override
    public Optional<Shipment> findById(UUID id) {
        return jpaRepository.findById(id).map(ShipmentJpaEntity::toDomain);
    }
}
