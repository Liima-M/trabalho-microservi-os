package br.edu.udesc.ecommerce.payment.domain.factory;

import br.edu.udesc.ecommerce.payment.domain.model.Payment;
import org.springframework.stereotype.Component;
import java.util.UUID;

/** Processador de cartão de crédito — aprovação imediata (simulada). RN13. */
@Component("CARTAO_CREDITO")
public class CreditCardProcessor implements PaymentProcessor {

    @Override
    public PaymentResult process(Payment payment) {
        // Simulação: aprova automaticamente
        return PaymentResult.builder()
                .success(true)
                .externalReference("CC-" + UUID.randomUUID())
                .message("Pagamento aprovado no cartão de crédito.")
                .build();
    }

    @Override
    public PaymentResult refund(Payment payment) {
        return PaymentResult.builder()
                .success(true)
                .externalReference("REF-CC-" + payment.getId())
                .message("Estorno realizado com sucesso.")
                .build();
    }
}
