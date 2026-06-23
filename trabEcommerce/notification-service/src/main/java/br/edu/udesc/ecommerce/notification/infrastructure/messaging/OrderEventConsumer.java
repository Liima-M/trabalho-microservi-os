package br.edu.udesc.ecommerce.notification.infrastructure.messaging;

import br.edu.udesc.ecommerce.notification.domain.model.NotificationType;
import br.edu.udesc.ecommerce.notification.domain.port.in.SendNotificationUseCase;
import br.edu.udesc.ecommerce.notification.domain.port.out.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * Consumidor de eventos de pedido.
 * Implementa o papel de Observer (GoF): reage a eventos publicados por outros serviços
 * sem acoplamento direto. Idempotência via tabela processed_events (RN16).
 * RN17: notificações disparadas em PedidoConfirmado, PagamentoAprovado, etc.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final SendNotificationUseCase sendNotification;
    private final NotificationRepository notificationRepository;

    @RabbitListener(queues = "pedido.confirmado.queue")
    public void onPedidoConfirmado(Map<String, Object> event) {
        processEvent(event, "pedido-confirmado-",
                NotificationType.PEDIDO_CONFIRMADO,
                "Pedido confirmado!",
                "Seu pedido foi confirmado e está sendo processado.");
    }

    @RabbitListener(queues = "pagamento.aprovado.queue")
    public void onPagamentoAprovado(Map<String, Object> event) {
        processEvent(event, "pagamento-aprovado-",
                NotificationType.PAGAMENTO_APROVADO,
                "Pagamento aprovado!",
                "Seu pagamento foi aprovado com sucesso.");
    }

    @RabbitListener(queues = "pagamento.recusado.queue")
    public void onPagamentoRecusado(Map<String, Object> event) {
        processEvent(event, "pagamento-recusado-",
                NotificationType.PAGAMENTO_RECUSADO,
                "Pagamento recusado",
                "Não foi possível processar seu pagamento. Tente novamente.");
    }

    @RabbitListener(queues = "pedido.cancelado.queue")
    public void onPedidoCancelado(Map<String, Object> event) {
        processEvent(event, "pedido-cancelado-",
                NotificationType.PEDIDO_CANCELADO,
                "Pedido cancelado",
                "Seu pedido foi cancelado. O estorno será processado em breve.");
    }

    @RabbitListener(queues = "pedido.entregue.queue")
    public void onPedidoEntregue(Map<String, Object> event) {
        processEvent(event, "pedido-entregue-",
                NotificationType.PEDIDO_ENTREGUE,
                "Pedido entregue!",
                "Seu pedido foi entregue. Que tal avaliar sua compra?");
    }

    private void processEvent(Map<String, Object> event, String prefix,
                               NotificationType type, String title, String message) {
        String eventId = prefix + event.get("id");
        if (notificationRepository.isEventAlreadyProcessed(eventId)) {
            log.info("[NOTIFICATION] Evento {} já processado. Ignorando (idempotência).", eventId);
            return;
        }
        UUID userId = UUID.fromString(event.get("buyerId").toString());
        sendNotification.execute(userId, type, title, message);
        notificationRepository.markEventAsProcessed(eventId);
    }
}
