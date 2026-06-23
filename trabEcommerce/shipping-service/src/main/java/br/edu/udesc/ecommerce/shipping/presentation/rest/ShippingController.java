package br.edu.udesc.ecommerce.shipping.presentation.rest;

import br.edu.udesc.ecommerce.shipping.domain.model.Shipment;
import br.edu.udesc.ecommerce.shipping.domain.model.ShipmentStatus;
import br.edu.udesc.ecommerce.shipping.domain.port.in.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/shipping")
@RequiredArgsConstructor
@Tag(name = "Frete e Rastreamento")
public class ShippingController {

    private final CalculateFreightUseCase calculateFreight;
    private final CreateShipmentUseCase createShipment;
    private final TrackShipmentUseCase trackShipment;
    private final UpdateShipmentStatusUseCase updateStatus;

    @GetMapping("/calculate")
    @Operation(summary = "Calcular frete por CEP e peso (RN11, RN12)")
    public ResponseEntity<BigDecimal> calculate(
            @RequestParam String zipCode,
            @RequestParam BigDecimal weightKg,
            @RequestParam BigDecimal orderTotal) {
        return ResponseEntity.ok(calculateFreight.execute(zipCode, weightKg, orderTotal));
    }

    @PostMapping
    @Operation(summary = "Criar envio e gerar código de rastreio")
    public ResponseEntity<Shipment> create(
            @RequestParam UUID orderId,
            @RequestParam String destinationZip,
            @RequestParam BigDecimal weight,
            @RequestParam BigDecimal freightValue) {
        return ResponseEntity.ok(createShipment.execute(orderId, destinationZip, weight, freightValue));
    }

    @GetMapping("/track/{trackingCode}")
    @Operation(summary = "Rastrear envio pelo código")
    public ResponseEntity<Shipment> track(@PathVariable String trackingCode) {
        return ResponseEntity.ok(trackShipment.execute(trackingCode));
    }

    @PatchMapping("/{shipmentId}/status")
    @Operation(summary = "Atualizar status de entrega (POSTADO, EM_TRANSITO, ENTREGUE)")
    public ResponseEntity<Void> updateStatus(
            @PathVariable UUID shipmentId,
            @RequestParam ShipmentStatus status) {
        updateStatus.execute(shipmentId, status);
        return ResponseEntity.ok().build();
    }
}
