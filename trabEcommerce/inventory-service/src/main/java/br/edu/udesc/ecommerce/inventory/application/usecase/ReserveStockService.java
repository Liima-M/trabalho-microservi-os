package br.edu.udesc.ecommerce.inventory.application.usecase;

import br.edu.udesc.ecommerce.inventory.domain.model.*;
import br.edu.udesc.ecommerce.inventory.domain.port.in.ReserveStockUseCase;
import br.edu.udesc.ecommerce.inventory.domain.port.out.StockRepository;
import br.edu.udesc.ecommerce.inventory.domain.port.out.StockReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Reserva estoque durante checkout. RN05: validade de 15 minutos.
 */
@Service
@RequiredArgsConstructor
public class ReserveStockService implements ReserveStockUseCase {

    private final StockRepository stockRepository;
    private final StockReservationRepository reservationRepository;

    @Override
    @Transactional
    public void execute(UUID productId, UUID orderId, int quantity) {
        Stock stock = stockRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("Estoque não encontrado para produto: " + productId));

        if (!stock.isAvailable(quantity)) {
            throw new IllegalStateException(
                    "Estoque insuficiente. Disponível: " + stock.getAvailableQuantity());
        }

        StockReservation reservation = StockReservation.builder()
                .id(UUID.randomUUID()).productId(productId).orderId(orderId)
                .quantity(quantity).status(ReservationStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15)) // RN05
                .build();

        reservationRepository.save(reservation);
    }
}
