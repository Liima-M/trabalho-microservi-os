package br.edu.udesc.ecommerce.order.domain.model;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Agregado Order. Controla transições de estado (padrão State).
 */
@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class Order {
    private UUID id;
    private UUID buyerId;
    private List<OrderItem> items;
    private String deliveryAddress;
    private PaymentMethod paymentMethod;
    private OrderStatus status;
    private BigDecimal subtotal;
    private BigDecimal freightValue;
    private BigDecimal total;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Order create(UUID buyerId, List<OrderItem> items,
                                String deliveryAddress, PaymentMethod paymentMethod,
                                BigDecimal freightValue) {
        BigDecimal subtotal = items.stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal total = subtotal.add(freightValue);

        return Order.builder()
                .id(UUID.randomUUID()).buyerId(buyerId).items(items)
                .deliveryAddress(deliveryAddress).paymentMethod(paymentMethod)
                .status(OrderStatus.PENDENTE).subtotal(subtotal)
                .freightValue(freightValue).total(total)
                .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now())
                .build();
    }

    public void transitionTo(OrderStatus newStatus) {
        validateTransition(this.status, newStatus);
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
    }

    /** RN06: cancelamento só antes do despacho. */
    public boolean isCancellable() {
        return status == OrderStatus.PENDENTE
                || status == OrderStatus.AGUARDANDO_PAGAMENTO
                || status == OrderStatus.PAGO
                || status == OrderStatus.EM_PREPARACAO;
    }

    private void validateTransition(OrderStatus from, OrderStatus to) {
        // Transições inválidas são rejeitadas
        if (from == OrderStatus.CANCELADO || from == OrderStatus.ENTREGUE) {
            throw new IllegalStateException(
                    "Pedido em estado terminal não pode ser alterado: " + from);
        }
    }
}
