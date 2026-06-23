package br.edu.udesc.ecommerce.payment.presentation.rest;

import br.edu.udesc.ecommerce.payment.domain.model.Payment;
import br.edu.udesc.ecommerce.payment.domain.model.PaymentMethod;
import br.edu.udesc.ecommerce.payment.domain.port.in.ProcessPaymentUseCase;
import br.edu.udesc.ecommerce.payment.domain.port.in.RefundPaymentUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Tag(name = "Pagamentos")
public class PaymentController {

    private final ProcessPaymentUseCase processPayment;
    private final RefundPaymentUseCase refundPayment;

    @PostMapping
    @Operation(summary = "Processar pagamento (cartão, PIX ou boleto) com Circuit Breaker")
    public ResponseEntity<Payment> process(
            @RequestParam UUID orderId,
            @RequestParam BigDecimal amount,
            @RequestParam PaymentMethod method) {
        return ResponseEntity.ok(processPayment.execute(orderId, amount, method));
    }

    @PostMapping("/{orderId}/refund")
    @Operation(summary = "Estornar pagamento (usado pela Saga em caso de cancelamento)")
    public ResponseEntity<Void> refund(@PathVariable UUID orderId) {
        refundPayment.execute(orderId);
        return ResponseEntity.ok().build();
    }
}
