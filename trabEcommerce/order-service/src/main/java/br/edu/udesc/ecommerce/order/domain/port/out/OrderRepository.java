package br.edu.udesc.ecommerce.order.domain.port.out;

import br.edu.udesc.ecommerce.order.domain.model.Order;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(UUID id);
    List<Order> findByBuyerId(UUID buyerId);
}
