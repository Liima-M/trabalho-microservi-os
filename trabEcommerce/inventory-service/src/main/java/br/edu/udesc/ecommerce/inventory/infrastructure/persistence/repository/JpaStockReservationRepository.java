package br.edu.udesc.ecommerce.inventory.infrastructure.persistence.repository;

import br.edu.udesc.ecommerce.inventory.domain.model.StockReservation;
import br.edu.udesc.ecommerce.inventory.domain.port.out.StockReservationRepository;
import br.edu.udesc.ecommerce.inventory.infrastructure.persistence.entity.StockReservationJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JpaStockReservationRepository implements StockReservationRepository {

    private final StockReservationJpaRepository jpaRepository;

    @Override
    public StockReservation save(StockReservation reservation) {
        return jpaRepository.save(StockReservationJpaEntity.from(reservation)).toDomain();
    }

    @Override
    public Optional<StockReservation> findByOrderId(UUID orderId) {
        return jpaRepository.findByOrderId(orderId).map(StockReservationJpaEntity::toDomain);
    }

    @Override
    public List<StockReservation> findExpiredPending() {
        return jpaRepository.findExpiredPending(LocalDateTime.now())
                .stream().map(StockReservationJpaEntity::toDomain).collect(Collectors.toList());
    }
}
