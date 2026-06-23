package br.edu.udesc.ecommerce.cart.infrastructure.persistence.entity;

import br.edu.udesc.ecommerce.cart.domain.model.Cart;
import br.edu.udesc.ecommerce.cart.domain.model.CartItem;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "carts")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class CartJpaEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "user_id", nullable = false, unique = true, columnDefinition = "uuid")
    private UUID userId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CartItemJpaEntity> items;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static CartJpaEntity from(Cart c) {
        CartJpaEntity entity = CartJpaEntity.builder()
                .id(c.getId()).userId(c.getUserId()).updatedAt(c.getUpdatedAt())
                .build();
        List<CartItemJpaEntity> itemEntities = c.getItems().stream()
                .map(i -> CartItemJpaEntity.from(i, entity))
                .collect(Collectors.toList());
        entity.setItems(itemEntities);
        return entity;
    }

    public Cart toDomain() {
        List<CartItem> domainItems = items == null ? List.of() :
                items.stream().map(CartItemJpaEntity::toDomain).collect(Collectors.toList());
        return Cart.builder().id(id).userId(userId)
                .items(new java.util.ArrayList<>(domainItems)).updatedAt(updatedAt).build();
    }
}
