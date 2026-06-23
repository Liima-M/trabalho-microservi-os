package br.edu.udesc.ecommerce.cart.presentation.rest;

import br.edu.udesc.ecommerce.cart.domain.model.Cart;
import br.edu.udesc.ecommerce.cart.domain.port.in.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Tag(name = "Carrinho de Compras")
public class CartController {

    private final GetCartUseCase getCartUseCase;
    private final AddItemToCartUseCase addItemUseCase;
    private final RemoveItemFromCartUseCase removeItemUseCase;
    private final ClearCartUseCase clearCartUseCase;

    @GetMapping("/{userId}")
    @Operation(summary = "Visualizar carrinho do usuário com subtotal")
    public ResponseEntity<Cart> getCart(@PathVariable UUID userId) {
        return ResponseEntity.ok(getCartUseCase.execute(userId));
    }

    @PostMapping("/{userId}/items")
    @Operation(summary = "Adicionar item ao carrinho")
    public ResponseEntity<Void> addItem(
            @PathVariable UUID userId,
            @RequestParam UUID productId,
            @RequestParam String productName,
            @RequestParam BigDecimal unitPrice,
            @RequestParam int quantity) {
        addItemUseCase.execute(userId, productId, productName, unitPrice, quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/items/{productId}")
    @Operation(summary = "Remover item do carrinho")
    public ResponseEntity<Void> removeItem(
            @PathVariable UUID userId,
            @PathVariable UUID productId) {
        removeItemUseCase.execute(userId, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Esvaziar carrinho (após confirmação de pedido)")
    public ResponseEntity<Void> clearCart(@PathVariable UUID userId) {
        clearCartUseCase.execute(userId);
        return ResponseEntity.ok().build();
    }
}
