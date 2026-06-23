package br.edu.udesc.ecommerce.payment.domain.factory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder @AllArgsConstructor
public class PaymentResult {
    private boolean success;
    private String externalReference;
    private String message;
}
