package br.edu.udesc.ecommerce.order.domain.model;

/**
 * Estados do ciclo de vida de um pedido.
 * Implementa o padrão State (GoF) através das transições controladas.
 * RN06: cancelamento permitido até EM_PREPARACAO.
 */
public enum OrderStatus {
    PENDENTE,
    AGUARDANDO_PAGAMENTO,
    PAGO,
    EM_PREPARACAO,
    DESPACHADO,
    ENTREGUE,
    CANCELADO
}
