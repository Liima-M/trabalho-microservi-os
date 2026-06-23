package br.edu.udesc.ecommerce.order.infrastructure.persistence.entity;

import br.edu.udesc.ecommerce.order.domain.model.*;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "orders")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class OrderJpaEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "buyer_id", nullable = false, columnDefinition = "uuid")
    private UUID buyerId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderItemJpaEntity> items;

    @Column(name = "delivery_address", nullable = false, columnDefinition = "TEXT")
    private String deliveryAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false, length = 20)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OrderStatus status;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "freight_value", nullable = false, precision = 14, scale = 2)
    private BigDecimal freightValue;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal total;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static OrderJpaEntity from(Order o) {
        OrderJpaEntity entity = OrderJpaEntity.builder()
                .id(o.getId()).buyerId(o.getBuyerId())
                .deliveryAddress(o.getDeliveryAddress()).paymentMethod(o.getPaymentMethod())
                .status(o.getStatus()).subtotal(o.getSubtotal())
                .freightValue(o.getFreightValue()).total(o.getTotal())
                .createdAt(o.getCreatedAt()).updatedAt(o.getUpdatedAt())
                .build();
        List<OrderItemJpaEntity> itemEntities = o.getItems().stream()
                .map(i -> OrderItemJpaEntity.from(i, entity)).collect(Collectors.toList());
        entity.setItems(itemEntities);
        return entity;
    }

    public Order toDomain() {
        List<OrderItem> domainItems = items == null ? List.of() :
                items.stream().map(OrderItemJpaEntity::toDomain).collect(Collectors.toList());
        return Order.builder().id(id).buyerId(buyerId)
                .items(new java.util.ArrayList<>(domainItems))
                .deliveryAddress(deliveryAddress).paymentMethod(paymentMethod)
                .status(status).subtotal(subtotal).freightValue(freightValue).total(total)
                .createdAt(createdAt).updatedAt(updatedAt).build();
    }
}
