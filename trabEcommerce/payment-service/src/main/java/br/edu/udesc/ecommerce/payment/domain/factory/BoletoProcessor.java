package br.edu.udesc.ecommerce.payment.domain.factory;

import br.edu.udesc.ecommerce.payment.domain.model.Payment;
import org.springframework.stereotype.Component;
import java.util.UUID;

/** Processador Boleto — gera código com vencimento em 3 dias úteis. RN13. */
@Component("BOLETO")
public class BoletoProcessor implements PaymentProcessor {

    @Override
    public PaymentResult process(Payment payment) {
        String codigoBoleto = "34191.75001 01043.510046 91020.150008 9 95670000050000";
        return PaymentResult.builder()
                .success(true)
                .externalReference(codigoBoleto)
                .message("Boleto gerado. Vencimento em 3 dias úteis.")
                .build();
    }

    @Override
    public PaymentResult refund(Payment payment) {
        return PaymentResult.builder()
                .success(true)
                .externalReference("REF-BOL-" + payment.getId())
                .message("Estorno de boleto processado.")
                .build();
    }
}
