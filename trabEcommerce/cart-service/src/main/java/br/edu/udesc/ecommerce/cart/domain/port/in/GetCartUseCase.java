package br.edu.udesc.ecommerce.cart.domain.port.in;

import br.edu.udesc.ecommerce.cart.domain.model.Cart;
import java.util.UUID;

public interface GetCartUseCase {
    Cart execute(UUID userId);
}
