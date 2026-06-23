package br.edu.udesc.ecommerce.notification.domain.model;

/**
 * RN17: eventos que disparam notificações automáticas.
 */
public enum NotificationType {
    PEDIDO_CONFIRMADO,
    PAGAMENTO_APROVADO,
    PAGAMENTO_RECUSADO,
    PEDIDO_ENVIADO,
    PEDIDO_ENTREGUE,
    PEDIDO_CANCELADO
}
