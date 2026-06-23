package br.edu.udesc.ecommerce.payment.domain;

import br.edu.udesc.ecommerce.payment.domain.model.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    void shouldStartAsPending() {
        Payment p = Payment.create(UUID.randomUUID(), BigDecimal.valueOf(100), PaymentMethod.PIX);
        assertEquals(PaymentStatus.PENDING, p.getStatus());
    }

    @Test
    void shouldApprovePayment() {
        Payment p = Payment.create(UUID.randomUUID(), BigDecimal.valueOf(100), PaymentMethod.PIX);
        p.approve("QR-123");
        assertEquals(PaymentStatus.APPROVED, p.getStatus());
        assertEquals("QR-123", p.getExternalReference());
    }

    @Test
    void shouldRefundApprovedPayment() {
        Payment p = Payment.create(UUID.randomUUID(), BigDecimal.valueOf(100), PaymentMethod.CARTAO_CREDITO);
        p.approve("CC-456");
        p.refund();
        assertEquals(PaymentStatus.REFUNDED, p.getStatus());
    }

    @Test
    void shouldRejectRefundOnNonApprovedPayment() {
        Payment p = Payment.create(UUID.randomUUID(), BigDecimal.valueOf(100), PaymentMethod.BOLETO);
        assertThrows(IllegalStateException.class, p::refund);
    }
}
