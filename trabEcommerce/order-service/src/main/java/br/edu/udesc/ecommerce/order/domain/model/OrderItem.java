package br.edu.udesc.ecommerce.order.domain.model;

import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class OrderItem {
    private UUID productId;
    private String productName;
    private BigDecimal unitPrice;
    private int quantity;
}
