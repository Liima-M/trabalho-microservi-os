package br.edu.udesc.ecommerce.inventory.domain;

import br.edu.udesc.ecommerce.inventory.domain.model.Stock;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class StockTest {

    private Stock buildStock(int qty, int reserved) {
        return Stock.builder().id(UUID.randomUUID()).productId(UUID.randomUUID())
                .quantity(qty).reservedQuantity(reserved)
                .updatedAt(LocalDateTime.now()).build();
    }

    @Test
    void shouldReturnCorrectAvailableQty() {
        Stock s = buildStock(10, 3);
        assertEquals(7, s.getAvailableQuantity());
    }

    @Test
    void shouldBeAvailableWhenSufficient() {
        assertTrue(buildStock(5, 0).isAvailable(5));
    }

    @Test
    void shouldBeUnavailableWhenInsufficient() {
        assertFalse(buildStock(5, 3).isAvailable(3));
    }

    @Test
    void shouldDetectOutOfStock() {
        assertTrue(buildStock(3, 3).isOutOfStock());
        assertFalse(buildStock(3, 2).isOutOfStock());
    }
}
