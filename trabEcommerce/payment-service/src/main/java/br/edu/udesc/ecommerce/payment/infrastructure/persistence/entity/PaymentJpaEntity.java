package br.edu.udesc.ecommerce.payment.infrastructure.persistence.entity;

import br.edu.udesc.ecommerce.payment.domain.model.Payment;
import br.edu.udesc.ecommerce.payment.domain.model.PaymentMethod;
import br.edu.udesc.ecommerce.payment.domain.model.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class PaymentJpaEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "order_id", nullable = false, unique = true, columnDefinition = "uuid")
    private UUID orderId;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus status;

    @Column(name = "external_reference", columnDefinition = "TEXT")
    private String externalReference;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static PaymentJpaEntity from(Payment p) {
        return PaymentJpaEntity.builder()
                .id(p.getId()).orderId(p.getOrderId()).amount(p.getAmount())
                .method(p.getMethod()).status(p.getStatus())
                .externalReference(p.getExternalReference())
                .createdAt(p.getCreatedAt()).updatedAt(p.getUpdatedAt())
                .build();
    }

    public Payment toDomain() {
        return Payment.builder().id(id).orderId(orderId).amount(amount)
                .method(method).status(status).externalReference(externalReference)
                .createdAt(createdAt).updatedAt(updatedAt).build();
    }
}
