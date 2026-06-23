package br.edu.udesc.ecommerce.catalog.domain.port.out;

import br.edu.udesc.ecommerce.catalog.domain.model.Category;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository {
    Optional<Category> findById(UUID id);
    boolean existsById(UUID id);
}
