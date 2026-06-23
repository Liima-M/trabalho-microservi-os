package br.edu.udesc.ecommerce.payment.domain.port.out;

import br.edu.udesc.ecommerce.payment.domain.model.Payment;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {
    Payment save(Payment payment);
    Optional<Payment> findByOrderId(UUID orderId);
}
