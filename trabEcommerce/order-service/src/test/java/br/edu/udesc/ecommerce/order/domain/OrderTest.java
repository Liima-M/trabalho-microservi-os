package br.edu.udesc.ecommerce.order.domain;

import br.edu.udesc.ecommerce.order.domain.model.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Order buildOrder() {
        OrderItem item = OrderItem.builder().productId(UUID.randomUUID())
                .productName("Produto").unitPrice(BigDecimal.valueOf(100)).quantity(2).build();
        return Order.create(UUID.randomUUID(), List.of(item), "Rua A, 123",
                PaymentMethod.PIX, BigDecimal.valueOf(15));
    }

    @Test
    void shouldComputeTotalCorrectly() {
        Order order = buildOrder();
        assertEquals(BigDecimal.valueOf(215), order.getTotal());
    }

    @Test
    void shouldStartAsPendente() {
        assertEquals(OrderStatus.PENDENTE, buildOrder().getStatus());
    }

    @Test
    void shouldBeCancellableBeforeDispatch() {
        Order o = buildOrder();
        assertTrue(o.isCancellable());
        o.transitionTo(OrderStatus.DESPACHADO);
        assertFalse(o.isCancellable());
    }

    @Test
    void shouldNotTransitionFromTerminalState() {
        Order o = buildOrder();
        o.transitionTo(OrderStatus.CANCELADO);
        assertThrows(IllegalStateException.class,
                () -> o.transitionTo(OrderStatus.PAGO));
    }
}
