package br.edu.udesc.ecommerce.cart.application.usecase;

import br.edu.udesc.ecommerce.cart.domain.model.Cart;
import br.edu.udesc.ecommerce.cart.domain.port.in.ClearCartUseCase;
import br.edu.udesc.ecommerce.cart.domain.port.out.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClearCartService implements ClearCartUseCase {

    private final CartRepository cartRepository;

    @Override
    @Transactional
    public void execute(UUID userId) {
        cartRepository.findByUserId(userId).ifPresent(cart -> {
            cart.clear();
            cartRepository.save(cart);
        });
    }
}
