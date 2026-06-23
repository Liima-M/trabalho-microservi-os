package br.edu.udesc.ecommerce.catalog.domain.strategy;

import br.edu.udesc.ecommerce.catalog.domain.model.Product;
import br.edu.udesc.ecommerce.catalog.domain.port.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

/** Estratégia de busca combinada por categoria e faixa de preço. */
@Component("searchByCategoryAndPrice")
@RequiredArgsConstructor
public class SearchByCategoryAndPriceStrategy implements ProductSearchStrategy {

    private final ProductRepository productRepository;

    @Override
    public List<Product> search(ProductSearchCriteria criteria) {
        return productRepository.findByCategoryAndPriceRange(
                criteria.getCategoryId(),
                criteria.getMinPrice(),
                criteria.getMaxPrice(),
                criteria.getPage(),
                criteria.getSize());
    }
}
