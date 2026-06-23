package br.edu.udesc.ecommerce.shipping.infrastructure.persistence.repository;

import br.edu.udesc.ecommerce.shipping.infrastructure.persistence.entity.ShipmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ShipmentJpaRepository extends JpaRepository<ShipmentJpaEntity, UUID> {
    Optional<ShipmentJpaEntity> findByTrackingCode(String trackingCode);
}
