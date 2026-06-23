package br.edu.udesc.ecommerce.cart.domain.model;

import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class CartItem {
    private UUID productId;
    private String productName;
    private BigDecimal unitPrice;
    private int quantity;

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        this.quantity = quantity;
    }
}
