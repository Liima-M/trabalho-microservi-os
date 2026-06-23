package br.edu.udesc.ecommerce.order.domain.saga;

/**
 * Representa um passo da Saga de Checkout.
 * Cada passo possui uma ação principal e uma compensação.
 */
public enum SagaStep {
    RESERVE_INVENTORY,
    PROCESS_PAYMENT,
    CREATE_SHIPMENT,
    COMPLETE
}
