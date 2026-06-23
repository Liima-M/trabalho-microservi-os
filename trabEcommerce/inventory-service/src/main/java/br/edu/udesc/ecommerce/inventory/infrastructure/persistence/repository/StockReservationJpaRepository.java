package br.edu.udesc.ecommerce.inventory.infrastructure.persistence.repository;

import br.edu.udesc.ecommerce.inventory.domain.model.ReservationStatus;
import br.edu.udesc.ecommerce.inventory.infrastructure.persistence.entity.StockReservationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StockReservationJpaRepository extends JpaRepository<StockReservationJpaEntity, UUID> {
    Optional<StockReservationJpaEntity> findByOrderId(UUID orderId);

    @Query("SELECT r FROM StockReservationJpaEntity r WHERE r.status = 'PENDING' AND r.expiresAt < :now")
    List<StockReservationJpaEntity> findExpiredPending(@org.springframework.data.repository.query.Param("now") LocalDateTime now);
}
