package br.edu.udesc.ecommerce.catalog.application.usecase;

import br.edu.udesc.ecommerce.catalog.domain.model.Product;
import br.edu.udesc.ecommerce.catalog.domain.port.in.SearchProductUseCase;
import br.edu.udesc.ecommerce.catalog.domain.strategy.ProductSearchCriteria;
import br.edu.udesc.ecommerce.catalog.domain.strategy.ProductSearchStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Contexto do padrão Strategy (GoF).
 * Delega a busca para a estratégia adequada em tempo de execução.
 */
@Service
@RequiredArgsConstructor
public class SearchProductService implements SearchProductUseCase {

    private final Map<String, ProductSearchStrategy> strategies;

    @Override
    public List<Product> execute(ProductSearchCriteria criteria) {
        String strategyKey = resolveStrategy(criteria);
        ProductSearchStrategy strategy = strategies.getOrDefault(strategyKey,
                strategies.get("searchByName"));
        return strategy.search(criteria);
    }

    private String resolveStrategy(ProductSearchCriteria criteria) {
        if (criteria.getCategoryId() != null
                || criteria.getMinPrice() != null
                || criteria.getMaxPrice() != null) {
            return "searchByCategoryAndPrice";
        }
        return "searchByName";
    }
}
