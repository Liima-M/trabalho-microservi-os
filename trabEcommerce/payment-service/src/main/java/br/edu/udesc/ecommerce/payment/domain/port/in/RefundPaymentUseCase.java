package br.edu.udesc.ecommerce.payment.domain.port.in;

import java.util.UUID;

public interface RefundPaymentUseCase {
    void execute(UUID orderId);
}
