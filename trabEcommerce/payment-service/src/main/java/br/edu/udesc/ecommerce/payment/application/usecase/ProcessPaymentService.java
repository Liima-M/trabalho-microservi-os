package br.edu.udesc.ecommerce.payment.application.usecase;

import br.edu.udesc.ecommerce.payment.domain.factory.PaymentProcessor;
import br.edu.udesc.ecommerce.payment.domain.factory.PaymentResult;
import br.edu.udesc.ecommerce.payment.domain.model.*;
import br.edu.udesc.ecommerce.payment.domain.port.in.ProcessPaymentUseCase;
import br.edu.udesc.ecommerce.payment.domain.port.out.EventPublisher;
import br.edu.udesc.ecommerce.payment.domain.port.out.PaymentRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

/**
 * Usa Factory Method para selecionar o processador correto
 * e Circuit Breaker (Resilience4j) para proteger chamadas ao gateway. (RF34, RN14)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessPaymentService implements ProcessPaymentUseCase {

    private final PaymentRepository paymentRepository;
    private final EventPublisher eventPublisher;
    private final Map<String, PaymentProcessor> processors; // injetado pelo Spring por nome do bean

    @Override
    @Transactional
    @CircuitBreaker(name = "payment-gateway", fallbackMethod = "fallbackProcess")
    public Payment execute(UUID orderId, BigDecimal amount, PaymentMethod method) {
        Payment payment = Payment.create(orderId, amount, method);

        // Factory Method: seleciona o processador pelo nome do enum
        PaymentProcessor processor = processors.get(method.name());
        if (processor == null) {
            throw new IllegalArgumentException("Método de pagamento não suportado: " + method);
        }

        PaymentResult result = processor.process(payment);

        if (result.isSuccess()) {
            payment.approve(result.getExternalReference());
            eventPublisher.publish("payment.exchange", "pagamento.aprovado", payment);
        } else {
            payment.reject();
            eventPublisher.publish("payment.exchange", "pagamento.recusado", payment);
        }

        return paymentRepository.save(payment);
    }

    /**
     * Fallback do Circuit Breaker: rejeita o pagamento quando o gateway está indisponível.
     * RN14: CB abre após 5 falhas em 60s; rejeita por 30s antes de ir a SEMI-ABERTO.
     */
    public Payment fallbackProcess(UUID orderId, BigDecimal amount, PaymentMethod method, Throwable t) {
        log.error("[CIRCUIT BREAKER] Gateway indisponível para pedido {}. Motivo: {}", orderId, t.getMessage());
        Payment payment = Payment.create(orderId, amount, method);
        payment.reject();
        eventPublisher.publish("payment.exchange", "pagamento.recusado", payment);
        return paymentRepository.save(payment);
    }
}
