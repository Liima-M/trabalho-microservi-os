package br.edu.udesc.ecommerce.cart.domain.port.in;

import java.math.BigDecimal;
import java.util.UUID;

public interface AddItemToCartUseCase {
    void execute(UUID userId, UUID productId, String productName, BigDecimal unitPrice, int quantity);
}
