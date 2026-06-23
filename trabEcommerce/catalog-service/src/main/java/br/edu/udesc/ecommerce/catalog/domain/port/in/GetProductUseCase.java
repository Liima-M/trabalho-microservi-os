package br.edu.udesc.ecommerce.catalog.domain.port.in;

import br.edu.udesc.ecommerce.catalog.domain.model.Product;
import java.util.UUID;

public interface GetProductUseCase {
    Product execute(UUID productId);
}
