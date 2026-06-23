package br.edu.udesc.ecommerce.order.presentation.rest;

import br.edu.udesc.ecommerce.order.domain.model.Order;
import br.edu.udesc.ecommerce.order.domain.port.in.CancelOrderUseCase;
import br.edu.udesc.ecommerce.order.domain.port.in.GetOrderUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Pedidos")
public class OrderController {

    private final GetOrderUseCase getOrderUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;

    @GetMapping("/{orderId}")
    @Operation(summary = "Acompanhar status de um pedido")
    public ResponseEntity<Order> getOrder(@PathVariable UUID orderId) {
        return ResponseEntity.ok(getOrderUseCase.execute(orderId));
    }

    @GetMapping("/buyer/{buyerId}")
    @Operation(summary = "Histórico de pedidos do comprador")
    public ResponseEntity<List<Order>> getByBuyer(@PathVariable UUID buyerId) {
        return ResponseEntity.ok(getOrderUseCase.findByBuyer(buyerId));
    }

    @DeleteMapping("/{orderId}/cancel")
    @Operation(summary = "Cancelar pedido (RN06: apenas até EM_PREPARACAO)")
    public ResponseEntity<Void> cancel(
            @PathVariable UUID orderId,
            @RequestHeader("X-User-Id") UUID requesterId) {
        cancelOrderUseCase.execute(orderId, requesterId);
        return ResponseEntity.ok().build();
    }
}
