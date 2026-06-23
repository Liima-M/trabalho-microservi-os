package br.edu.udesc.ecommerce.cart.application.usecase;

import br.edu.udesc.ecommerce.cart.domain.model.*;
import br.edu.udesc.ecommerce.cart.domain.port.in.AddItemToCartUseCase;
import br.edu.udesc.ecommerce.cart.domain.port.out.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddItemToCartService implements AddItemToCartUseCase {

    private final CartRepository cartRepository;

    @Override
    @Transactional
    public void execute(UUID userId, UUID productId, String productName,
                        BigDecimal unitPrice, int quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElse(Cart.createEmpty(userId));

        CartItem item = CartItem.builder()
                .productId(productId).productName(productName)
                .unitPrice(unitPrice).quantity(quantity).build();

        cart.addItem(item);
        cartRepository.save(cart);
    }
}
