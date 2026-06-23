package br.edu.udesc.ecommerce.cart.domain.model;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class Cart {
    private UUID id;
    private UUID userId;
    private List<CartItem> items;
    private LocalDateTime updatedAt;

    public static Cart createEmpty(UUID userId) {
        return Cart.builder().id(UUID.randomUUID()).userId(userId)
                .items(new ArrayList<>()).updatedAt(LocalDateTime.now()).build();
    }

    public BigDecimal getSubtotal() {
        return items.stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addItem(CartItem newItem) {
        items.stream().filter(i -> i.getProductId().equals(newItem.getProductId()))
                .findFirst().ifPresentOrElse(
                        existing -> existing.increaseQuantity(newItem.getQuantity()),
                        () -> items.add(newItem));
    }

    public void removeItem(UUID productId) {
        items.removeIf(i -> i.getProductId().equals(productId));
    }

    public void clear() {
        items.clear();
    }
}
