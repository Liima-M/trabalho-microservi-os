package br.edu.udesc.ecommerce.cart.infrastructure.persistence.repository;

import br.edu.udesc.ecommerce.cart.infrastructure.persistence.entity.CartJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface CartJpaRepository extends JpaRepository<CartJpaEntity, UUID> {
    Optional<CartJpaEntity> findByUserId(UUID userId);
}
