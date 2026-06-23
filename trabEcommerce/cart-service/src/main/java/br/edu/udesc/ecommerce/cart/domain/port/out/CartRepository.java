package br.edu.udesc.ecommerce.cart.domain.port.out;

import br.edu.udesc.ecommerce.cart.domain.model.Cart;
import java.util.Optional;
import java.util.UUID;

public interface CartRepository {
    Cart save(Cart cart);
    Optional<Cart> findByUserId(UUID userId);
}
