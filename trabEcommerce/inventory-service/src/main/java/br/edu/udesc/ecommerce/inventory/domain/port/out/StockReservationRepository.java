package br.edu.udesc.ecommerce.inventory.domain.port.out;

import br.edu.udesc.ecommerce.inventory.domain.model.StockReservation;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StockReservationRepository {
    StockReservation save(StockReservation reservation);
    Optional<StockReservation> findByOrderId(UUID orderId);
    List<StockReservation> findExpiredPending();
}
