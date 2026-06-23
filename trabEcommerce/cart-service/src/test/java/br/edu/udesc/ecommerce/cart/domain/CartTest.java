package br.edu.udesc.ecommerce.cart.domain;

import br.edu.udesc.ecommerce.cart.domain.model.Cart;
import br.edu.udesc.ecommerce.cart.domain.model.CartItem;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    @Test
    void shouldCalculateSubtotalCorrectly() {
        Cart cart = Cart.createEmpty(UUID.randomUUID());
        cart.addItem(CartItem.builder().productId(UUID.randomUUID())
                .productName("Produto A").unitPrice(BigDecimal.valueOf(100)).quantity(2).build());
        cart.addItem(CartItem.builder().productId(UUID.randomUUID())
                .productName("Produto B").unitPrice(BigDecimal.valueOf(50)).quantity(1).build());
        assertEquals(BigDecimal.valueOf(250), cart.getSubtotal());
    }

    @Test
    void shouldMergeItemWhenProductAlreadyInCart() {
        Cart cart = Cart.createEmpty(UUID.randomUUID());
        UUID pid = UUID.randomUUID();
        cart.addItem(CartItem.builder().productId(pid).productName("A").unitPrice(BigDecimal.TEN).quantity(2).build());
        cart.addItem(CartItem.builder().productId(pid).productName("A").unitPrice(BigDecimal.TEN).quantity(3).build());
        assertEquals(1, cart.getItems().size());
        assertEquals(5, cart.getItems().get(0).getQuantity());
    }

    @Test
    void shouldClearCart() {
        Cart cart = Cart.createEmpty(UUID.randomUUID());
        cart.addItem(CartItem.builder().productId(UUID.randomUUID()).productName("A").unitPrice(BigDecimal.ONE).quantity(1).build());
        cart.clear();
        assertTrue(cart.getItems().isEmpty());
    }
}
