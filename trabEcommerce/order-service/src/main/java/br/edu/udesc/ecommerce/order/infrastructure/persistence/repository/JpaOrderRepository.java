package br.edu.udesc.ecommerce.order.infrastructure.persistence.repository;

import br.edu.udesc.ecommerce.order.domain.model.Order;
import br.edu.udesc.ecommerce.order.domain.port.out.OrderRepository;
import br.edu.udesc.ecommerce.order.infrastructure.persistence.entity.OrderJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JpaOrderRepository implements OrderRepository {

    private final OrderJpaRepository jpaRepository;

    @Override
    public Order save(Order order) {
        return jpaRepository.save(OrderJpaEntity.from(order)).toDomain();
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return jpaRepository.findById(id).map(OrderJpaEntity::toDomain);
    }

    @Override
    public List<Order> findByBuyerId(UUID buyerId) {
        return jpaRepository.findByBuyerId(buyerId).stream()
                .map(OrderJpaEntity::toDomain).collect(Collectors.toList());
    }
}
