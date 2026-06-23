package br.edu.udesc.ecommerce.order.domain.port.in;

import br.edu.udesc.ecommerce.order.domain.model.Order;
import java.util.List;
import java.util.UUID;

public interface GetOrderUseCase {
    Order execute(UUID orderId);
    List<Order> findByBuyer(UUID buyerId);
}
