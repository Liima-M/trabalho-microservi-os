package br.edu.udesc.ecommerce.payment.domain.model;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class Payment {
    private UUID id;
    private UUID orderId;
    private BigDecimal amount;
    private PaymentMethod method;
    private PaymentStatus status;
    private String externalReference; // QR Code PIX, código boleto, etc.
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Payment create(UUID orderId, BigDecimal amount, PaymentMethod method) {
        return Payment.builder()
                .id(UUID.randomUUID()).orderId(orderId).amount(amount)
                .method(method).status(PaymentStatus.PENDING)
                .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now())
                .build();
    }

    public void approve(String externalReference) {
        this.status = PaymentStatus.APPROVED;
        this.externalReference = externalReference;
        this.updatedAt = LocalDateTime.now();
    }

    public void reject() {
        this.status = PaymentStatus.REJECTED;
        this.updatedAt = LocalDateTime.now();
    }

    public void refund() {
        if (this.status != PaymentStatus.APPROVED) {
            throw new IllegalStateException("Apenas pagamentos aprovados podem ser estornados.");
        }
        this.status = PaymentStatus.REFUNDED;
        this.updatedAt = LocalDateTime.now();
    }
}
