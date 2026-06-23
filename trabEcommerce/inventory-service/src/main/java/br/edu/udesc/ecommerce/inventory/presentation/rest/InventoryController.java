package br.edu.udesc.ecommerce.inventory.presentation.rest;

import br.edu.udesc.ecommerce.inventory.domain.port.in.CheckAvailabilityUseCase;
import br.edu.udesc.ecommerce.inventory.domain.port.in.ReserveStockUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
@Tag(name = "Estoque")
public class InventoryController {

    private final CheckAvailabilityUseCase checkAvailability;
    private final ReserveStockUseCase reserveStock;

    @GetMapping("/products/{productId}/availability")
    @Operation(summary = "Verificar quantidade disponível em estoque")
    public ResponseEntity<Map<String, Integer>> checkAvailability(@PathVariable UUID productId) {
        return ResponseEntity.ok(Map.of("available", checkAvailability.execute(productId)));
    }

    @PostMapping("/products/{productId}/reserve")
    @Operation(summary = "Reservar estoque para checkout (usado pela Saga)")
    public ResponseEntity<Void> reserve(
            @PathVariable UUID productId,
            @RequestParam UUID orderId,
            @RequestParam int quantity) {
        reserveStock.execute(productId, orderId, quantity);
        return ResponseEntity.ok().build();
    }
}
