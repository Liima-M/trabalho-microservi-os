package br.edu.udesc.ecommerce.cart.application.usecase;

import br.edu.udesc.ecommerce.cart.domain.model.Cart;
import br.edu.udesc.ecommerce.cart.domain.port.in.GetCartUseCase;
import br.edu.udesc.ecommerce.cart.domain.port.out.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetCartService implements GetCartUseCase {

    private final CartRepository cartRepository;

    @Override
    public Cart execute(UUID userId) {
        return cartRepository.findByUserId(userId)
                .orElse(Cart.createEmpty(userId));
    }
}
