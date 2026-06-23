package br.edu.udesc.ecommerce.order.application;

import br.edu.udesc.ecommerce.order.application.usecase.CancelOrderService;
import br.edu.udesc.ecommerce.order.domain.model.*;
import br.edu.udesc.ecommerce.order.domain.port.out.EventPublisher;
import br.edu.udesc.ecommerce.order.domain.port.out.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelOrderServiceTest {

    @Mock private OrderRepository orderRepository;
    @Mock private EventPublisher eventPublisher;

    private CancelOrderService service;
    private UUID buyerId;

    @BeforeEach
    void setUp() {
        service = new CancelOrderService(orderRepository, eventPublisher);
        buyerId = UUID.randomUUID();
    }

    private Order buildOrder(UUID buyer) {
        OrderItem item = OrderItem.builder().productId(UUID.randomUUID())
                .productName("P").unitPrice(BigDecimal.TEN).quantity(1).build();
        return Order.create(buyer, List.of(item), "Rua", PaymentMethod.PIX, BigDecimal.ZERO);
    }

    @Test
    void shouldCancelOrderSuccessfully() {
        Order order = buildOrder(buyerId);
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(orderRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        service.execute(order.getId(), buyerId);

        assertEquals(OrderStatus.CANCELADO, order.getStatus());
        verify(eventPublisher).publish(anyString(), eq("pedido.cancelado"), any());
    }

    @Test
    void shouldRejectCancellationByWrongUser() {
        Order order = buildOrder(buyerId);
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        assertThrows(IllegalStateException.class,
                () -> service.execute(order.getId(), UUID.randomUUID()));
    }
}
