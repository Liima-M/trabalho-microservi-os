package br.edu.udesc.ecommerce.catalog.domain;

import br.edu.udesc.ecommerce.catalog.domain.model.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void shouldCreateProductSuccessfully() {
        Product p = Product.create("Notebook", "Notebook gamer", BigDecimal.valueOf(4999.99),
                UUID.randomUUID(), UUID.randomUUID(), List.of("https://img.com/nb.jpg"));

        assertNotNull(p.getId());
        assertTrue(p.isActive());
        assertEquals("Notebook", p.getName());
    }

    @Test
    void shouldRejectProductWithoutImage() {
        assertThrows(IllegalArgumentException.class,
                () -> Product.create("Notebook", "Desc", BigDecimal.TEN,
                        UUID.randomUUID(), UUID.randomUUID(), List.of()));
    }

    @Test
    void shouldRejectNullImageList() {
        assertThrows(IllegalArgumentException.class,
                () -> Product.create("Notebook", "Desc", BigDecimal.TEN,
                        UUID.randomUUID(), UUID.randomUUID(), null));
    }
}
