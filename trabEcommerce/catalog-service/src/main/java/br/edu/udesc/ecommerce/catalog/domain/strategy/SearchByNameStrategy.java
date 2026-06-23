package br.edu.udesc.ecommerce.catalog.domain.strategy;

import br.edu.udesc.ecommerce.catalog.domain.model.Product;
import br.edu.udesc.ecommerce.catalog.domain.port.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

/** Estratégia de busca por nome. */
@Component("searchByName")
@RequiredArgsConstructor
public class SearchByNameStrategy implements ProductSearchStrategy {

    private final ProductRepository productRepository;

    @Override
    public List<Product> search(ProductSearchCriteria criteria) {
        return productRepository.findByNameContaining(criteria.getName(),
                criteria.getPage(), criteria.getSize());
    }
}
