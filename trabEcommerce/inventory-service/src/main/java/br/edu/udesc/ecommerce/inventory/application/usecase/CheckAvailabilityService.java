package br.edu.udesc.ecommerce.inventory.application.usecase;

import br.edu.udesc.ecommerce.inventory.domain.model.Stock;
import br.edu.udesc.ecommerce.inventory.domain.port.in.CheckAvailabilityUseCase;
import br.edu.udesc.ecommerce.inventory.domain.port.out.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckAvailabilityService implements CheckAvailabilityUseCase {

    private final StockRepository stockRepository;

    @Override
    public int execute(UUID productId) {
        return stockRepository.findByProductId(productId)
                .map(Stock::getAvailableQuantity)
                .orElse(0);
    }
}
