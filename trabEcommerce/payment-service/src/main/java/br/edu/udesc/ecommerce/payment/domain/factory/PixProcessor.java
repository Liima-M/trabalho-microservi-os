package br.edu.udesc.ecommerce.payment.domain.factory;

import br.edu.udesc.ecommerce.payment.domain.model.Payment;
import org.springframework.stereotype.Component;
import java.util.UUID;

/** Processador PIX — gera QR Code e aguarda confirmação assíncrona. RN13. */
@Component("PIX")
public class PixProcessor implements PaymentProcessor {

    @Override
    public PaymentResult process(Payment payment) {
        String qrCode = "00020126580014BR.GOV.BCB.PIX-" + UUID.randomUUID();
        return PaymentResult.builder()
                .success(true)
                .externalReference(qrCode)
                .message("QR Code PIX gerado. Aguardando confirmação assíncrona.")
                .build();
    }

    @Override
    public PaymentResult refund(Payment payment) {
        return PaymentResult.builder()
                .success(true)
                .externalReference("REF-PIX-" + payment.getId())
                .message("Devolução PIX processada.")
                .build();
    }
}
