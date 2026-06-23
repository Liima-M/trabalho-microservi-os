package br.edu.udesc.ecommerce.payment.domain.factory;

import br.edu.udesc.ecommerce.payment.domain.model.Payment;

/**
 * Interface Product do padrão Factory Method (GoF).
 * Cada método de pagamento implementa seu próprio processador.
 * OCP: novos métodos de pagamento são adicionados sem modificar código existente.
 */
public interface PaymentProcessor {
    PaymentResult process(Payment payment);
    PaymentResult refund(Payment payment);
}
