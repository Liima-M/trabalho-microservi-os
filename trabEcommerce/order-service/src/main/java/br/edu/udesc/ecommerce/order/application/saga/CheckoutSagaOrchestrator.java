package br.edu.udesc.ecommerce.order.application.saga;

import br.edu.udesc.ecommerce.order.domain.model.*;
import br.edu.udesc.ecommerce.order.domain.port.out.EventPublisher;
import br.edu.udesc.ecommerce.order.domain.port.out.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

/**
 * Orquestra a Saga de Checkout (padrão Saga — Orchestration).
 *
 * Fluxo feliz:
 *   1. Reservar estoque (inventory-service)
 *   2. Processar pagamento (payment-service)
 *   3. Criar envio (shipping-service)
 *   4. Confirmar pedido e esvaziar carrinho
 *
 * Em caso de falha em qualquer passo, executa compensações na ordem inversa.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CheckoutSagaOrchestrator {

    private final OrderRepository orderRepository;
    private final EventPublisher eventPublisher;
    private final RestTemplate restTemplate;

    public void execute(Order order) {
        log.info("[SAGA] Iniciando checkout para pedido {}", order.getId());

        try {
            // Passo 1: Reservar estoque
            reserveInventory(order);
            order.transitionTo(OrderStatus.AGUARDANDO_PAGAMENTO);
            orderRepository.save(order);

            // Passo 2: Processar pagamento
            processPayment(order);
            order.transitionTo(OrderStatus.PAGO);
            orderRepository.save(order);

            // Passo 3: Criar envio
            createShipment(order);
            order.transitionTo(OrderStatus.EM_PREPARACAO);
            orderRepository.save(order);

            // Saga concluída com sucesso
            eventPublisher.publish("order.exchange", "pedido.confirmado", order);
            log.info("[SAGA] Checkout concluído para pedido {}", order.getId());

        } catch (Exception e) {
            log.error("[SAGA] Falha no checkout do pedido {}. Iniciando compensações. Motivo: {}",
                    order.getId(), e.getMessage());
            compensate(order);
        }
    }

    private void reserveInventory(Order order) {
        // Chamada síncrona ao inventory-service para reservar cada item
        for (OrderItem item : order.getItems()) {
            String url = "http://inventory-service:8083/inventory/products/{productId}/reserve"
                    + "?orderId={orderId}&quantity={qty}";
            restTemplate.postForEntity(url, null, Void.class,
                    item.getProductId(), order.getId(), item.getQuantity());
        }
    }

    private void processPayment(Order order) {
        // Chamada ao payment-service — protegida por Circuit Breaker no próprio serviço
        String url = "http://payment-service:8086/payments";
        restTemplate.postForEntity(url, order, Void.class);
    }

    private void createShipment(Order order) {
        String url = "http://shipping-service:8087/shipping";
        restTemplate.postForEntity(url, order, Void.class);
    }

    private void compensate(Order order) {
        try {
            // Compensação: liberar reservas de estoque
            String url = "http://inventory-service:8083/inventory/orders/{orderId}/release";
            restTemplate.delete(url, order.getId());
        } catch (Exception ex) {
            log.error("[SAGA] Falha na compensação de estoque para pedido {}", order.getId(), ex);
        }

        order.transitionTo(OrderStatus.CANCELADO);
        orderRepository.save(order);

        eventPublisher.publish("order.exchange", "pedido.cancelado", order);
        log.info("[SAGA] Pedido {} cancelado com compensações aplicadas.", order.getId());
    }
}
