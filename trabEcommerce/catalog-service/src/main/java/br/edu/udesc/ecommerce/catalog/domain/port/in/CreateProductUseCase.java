package br.edu.udesc.ecommerce.catalog.domain.port.in;

import br.edu.udesc.ecommerce.catalog.domain.model.Product;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface CreateProductUseCase {
    Product execute(String name, String description, BigDecimal price,
                    UUID categoryId, UUID sellerId, List<String> imageUrls);
}
