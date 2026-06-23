package br.edu.udesc.ecommerce.order.application.usecase;

import br.edu.udesc.ecommerce.order.domain.model.Order;
import br.edu.udesc.ecommerce.order.domain.model.OrderStatus;
import br.edu.udesc.ecommerce.order.domain.port.in.CancelOrderUseCase;
import br.edu.udesc.ecommerce.order.domain.port.out.EventPublisher;
import br.edu.udesc.ecommerce.order.domain.port.out.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CancelOrderService implements CancelOrderUseCase {

    private final OrderRepository orderRepository;
    private final EventPublisher eventPublisher;

    @Override
    @Transactional
    public void execute(UUID orderId, UUID requesterId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado."));

        if (!order.getBuyerId().equals(requesterId)) {
            throw new IllegalStateException("Acesso negado: pedido pertence a outro usuário.");
        }

        if (!order.isCancellable()) { // RN06
            throw new IllegalStateException(
                    "Pedido não pode ser cancelado no status: " + order.getStatus());
        }

        order.transitionTo(OrderStatus.CANCELADO);
        orderRepository.save(order);

        // RN07 — dispara estorno e liberação de estoque via evento
        eventPublisher.publish("order.exchange", "pedido.cancelado", order);
    }
}
