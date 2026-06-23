package br.edu.udesc.ecommerce.order.infrastructure.persistence.entity;

import br.edu.udesc.ecommerce.order.domain.model.OrderItem;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_items")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class OrderItemJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderJpaEntity order;

    @Column(name = "product_id", nullable = false, columnDefinition = "uuid")
    private UUID productId;

    @Column(name = "product_name", nullable = false, length = 255)
    private String productName;

    @Column(name = "unit_price", nullable = false, precision = 14, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private int quantity;

    public static OrderItemJpaEntity from(OrderItem item, OrderJpaEntity order) {
        return OrderItemJpaEntity.builder()
                .order(order).productId(item.getProductId())
                .productName(item.getProductName()).unitPrice(item.getUnitPrice())
                .quantity(item.getQuantity()).build();
    }

    public OrderItem toDomain() {
        return OrderItem.builder().productId(productId).productName(productName)
                .unitPrice(unitPrice).quantity(quantity).build();
    }
}
