package br.edu.udesc.ecommerce.inventory.infrastructure.persistence.repository;

import br.edu.udesc.ecommerce.inventory.infrastructure.persistence.entity.StockJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface StockJpaRepository extends JpaRepository<StockJpaEntity, UUID> {
    Optional<StockJpaEntity> findByProductId(UUID productId);
}
