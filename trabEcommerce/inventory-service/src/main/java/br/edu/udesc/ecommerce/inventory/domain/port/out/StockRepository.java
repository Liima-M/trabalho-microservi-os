package br.edu.udesc.ecommerce.inventory.domain.port.out;

import br.edu.udesc.ecommerce.inventory.domain.model.Stock;
import java.util.Optional;
import java.util.UUID;

public interface StockRepository {
    Stock save(Stock stock);
    Optional<Stock> findByProductId(UUID productId);
}
