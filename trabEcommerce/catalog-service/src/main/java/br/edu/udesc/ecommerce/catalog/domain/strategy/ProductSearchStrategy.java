package br.edu.udesc.ecommerce.catalog.domain.strategy;

import br.edu.udesc.ecommerce.catalog.domain.model.Product;
import java.util.List;

/**
 * Interface do padrão Strategy (GoF) para busca de produtos.
 * Permite adicionar novas estratégias sem modificar o contexto (OCP do SOLID).
 */
public interface ProductSearchStrategy {
    List<Product> search(ProductSearchCriteria criteria);
}
