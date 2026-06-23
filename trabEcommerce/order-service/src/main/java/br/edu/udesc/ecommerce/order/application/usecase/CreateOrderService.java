package br.edu.udesc.ecommerce.order.application.usecase;

import br.edu.udesc.ecommerce.order.application.saga.CheckoutSagaOrchestrator;
import br.edu.udesc.ecommerce.order.domain.model.*;
import br.edu.udesc.ecommerce.order.domain.port.in.CreateOrderUseCase;
import br.edu.udesc.ecommerce.order.domain.port.out.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateOrderService implements CreateOrderUseCase {

    private final OrderRepository orderRepository;
    private final CheckoutSagaOrchestrator sagaOrchestrator;

    @Override
    @Transactional
    public Order execute(UUID buyerId, List<OrderItem> items, String deliveryAddress,
                         PaymentMethod paymentMethod, BigDecimal freightValue) {

        Order order = Order.create(buyerId, items, deliveryAddress, paymentMethod, freightValue);
        Order saved = orderRepository.save(order);

        // Dispara a Saga de forma assíncrona (em produção usaria @Async ou mensageria)
        sagaOrchestrator.execute(saved);

        return saved;
    }
}
