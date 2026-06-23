package br.edu.udesc.ecommerce.shipping.application.usecase;

import br.edu.udesc.ecommerce.shipping.domain.port.in.CalculateFreightUseCase;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

/**
 * Cálculo simulado de frete. (RF35, RN11, RN12)
 * RN12: frete grátis para pedidos acima de R$ 200.
 */
@Service
public class CalculateFreightService implements CalculateFreightUseCase {

    private static final BigDecimal FREE_FREIGHT_THRESHOLD = BigDecimal.valueOf(200);
    private static final BigDecimal BASE_RATE_PER_KG = BigDecimal.valueOf(8.50);

    @Override
    public BigDecimal execute(String destinationZip, BigDecimal weightKg, BigDecimal orderTotal) {
        // RN12: frete grátis para pedidos acima de R$ 200
        if (orderTotal.compareTo(FREE_FREIGHT_THRESHOLD) > 0) {
            return BigDecimal.ZERO;
        }
        // Simulação: taxa base x peso (em produção: integração com Correios/transportadora)
        return BASE_RATE_PER_KG.multiply(weightKg).max(BigDecimal.valueOf(10));
    }
}
