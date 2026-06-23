package br.edu.udesc.ecommerce.payment.domain.port.in;

import br.edu.udesc.ecommerce.payment.domain.model.Payment;
import br.edu.udesc.ecommerce.payment.domain.model.PaymentMethod;
import java.math.BigDecimal;
import java.util.UUID;

public interface ProcessPaymentUseCase {
    Payment execute(UUID orderId, BigDecimal amount, PaymentMethod method);
}
