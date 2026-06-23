package br.edu.udesc.ecommerce.catalog.domain.port.in;

import br.edu.udesc.ecommerce.catalog.domain.model.Product;
import br.edu.udesc.ecommerce.catalog.domain.strategy.ProductSearchCriteria;
import java.util.List;

public interface SearchProductUseCase {
    List<Product> execute(ProductSearchCriteria criteria);
}
