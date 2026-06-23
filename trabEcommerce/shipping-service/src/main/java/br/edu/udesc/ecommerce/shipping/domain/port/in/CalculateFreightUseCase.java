package br.edu.udesc.ecommerce.shipping.domain.port.in;

import java.math.BigDecimal;

public interface CalculateFreightUseCase {
    /**
     * Calcula o valor do frete com base no CEP e peso. (RF35, RN11, RN12)
     */
    BigDecimal execute(String destinationZip, BigDecimal weightKg, BigDecimal orderTotal);
}
