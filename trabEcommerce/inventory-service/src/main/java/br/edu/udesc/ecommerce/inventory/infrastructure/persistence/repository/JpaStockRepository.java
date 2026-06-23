package br.edu.udesc.ecommerce.inventory.infrastructure.persistence.repository;

import br.edu.udesc.ecommerce.inventory.domain.model.Stock;
import br.edu.udesc.ecommerce.inventory.domain.port.out.StockRepository;
import br.edu.udesc.ecommerce.inventory.infrastructure.persistence.entity.StockJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaStockRepository implements StockRepository {

    private final StockJpaRepository jpaRepository;

    @Override
    public Stock save(Stock stock) {
        return jpaRepository.save(StockJpaEntity.from(stock)).toDomain();
    }

    @Override
    public Optional<Stock> findByProductId(UUID productId) {
        return jpaRepository.findByProductId(productId).map(StockJpaEntity::toDomain);
    }
}
