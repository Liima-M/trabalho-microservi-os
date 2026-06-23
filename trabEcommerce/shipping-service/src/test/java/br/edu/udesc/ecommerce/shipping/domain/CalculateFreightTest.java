package br.edu.udesc.ecommerce.shipping.domain;

import br.edu.udesc.ecommerce.shipping.application.usecase.CalculateFreightService;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class CalculateFreightTest {

    private final CalculateFreightService service = new CalculateFreightService();

    @Test
    void shouldReturnZeroForOrderAbove200() {
        // RN12: frete grátis acima de R$ 200
        BigDecimal result = service.execute("89000-000", BigDecimal.valueOf(2), BigDecimal.valueOf(250));
        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    void shouldCalculateFreightForSmallOrder() {
        BigDecimal result = service.execute("89000-000", BigDecimal.valueOf(1), BigDecimal.valueOf(50));
        assertTrue(result.compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void shouldApplyMinimumFreight() {
        // Peso muito pequeno — mínimo de R$ 10
        BigDecimal result = service.execute("89000-000", BigDecimal.valueOf(0.1), BigDecimal.valueOf(30));
        assertTrue(result.compareTo(BigDecimal.valueOf(10)) >= 0);
    }
}
