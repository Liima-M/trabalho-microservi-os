package br.edu.udesc.ecommerce.cart.infrastructure.persistence.entity;

import br.edu.udesc.ecommerce.cart.domain.model.CartItem;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "cart_items")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class CartItemJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private CartJpaEntity cart;

    @Column(name = "product_id", nullable = false, columnDefinition = "uuid")
    private UUID productId;

    @Column(name = "product_name", nullable = false, length = 255)
    private String productName;

    @Column(name = "unit_price", nullable = false, precision = 14, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private int quantity;

    public static CartItemJpaEntity from(CartItem item, CartJpaEntity cart) {
        return CartItemJpaEntity.builder()
                .cart(cart).productId(item.getProductId())
                .productName(item.getProductName()).unitPrice(item.getUnitPrice())
                .quantity(item.getQuantity())
                .build();
    }

    public CartItem toDomain() {
        return CartItem.builder().productId(productId).productName(productName)
                .unitPrice(unitPrice).quantity(quantity).build();
    }
}
