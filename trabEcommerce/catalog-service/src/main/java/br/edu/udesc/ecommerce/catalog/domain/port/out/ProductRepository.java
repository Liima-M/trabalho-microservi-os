package br.edu.udesc.ecommerce.catalog.domain.port.out;

import br.edu.udesc.ecommerce.catalog.domain.model.Product;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(UUID id);
    List<Product> findByNameContaining(String name, int page, int size);
    List<Product> findByCategoryAndPriceRange(UUID categoryId, BigDecimal min, BigDecimal max, int page, int size);
    boolean existsById(UUID id);
}
