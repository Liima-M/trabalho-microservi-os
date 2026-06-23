package br.edu.udesc.ecommerce.order.domain.port.in;

import br.edu.udesc.ecommerce.order.domain.model.Order;
import br.edu.udesc.ecommerce.order.domain.model.OrderItem;
import br.edu.udesc.ecommerce.order.domain.model.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface CreateOrderUseCase {
    Order execute(UUID buyerId, List<OrderItem> items, String deliveryAddress,
                  PaymentMethod paymentMethod, BigDecimal freightValue);
}
