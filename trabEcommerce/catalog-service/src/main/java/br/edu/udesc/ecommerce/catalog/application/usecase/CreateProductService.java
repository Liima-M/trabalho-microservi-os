package br.edu.udesc.ecommerce.catalog.application.usecase;

import br.edu.udesc.ecommerce.catalog.domain.model.Product;
import br.edu.udesc.ecommerce.catalog.domain.port.in.CreateProductUseCase;
import br.edu.udesc.ecommerce.catalog.domain.port.out.CategoryRepository;
import br.edu.udesc.ecommerce.catalog.domain.port.out.EventPublisher;
import br.edu.udesc.ecommerce.catalog.domain.port.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateProductService implements CreateProductUseCase {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final EventPublisher eventPublisher;

    @Override
    @Transactional
    public Product execute(String name, String description, BigDecimal price,
                           UUID categoryId, UUID sellerId, List<String> imageUrls) {

        // RN20 — categoria deve existir
        if (!categoryRepository.existsById(categoryId)) {
            throw new IllegalArgumentException("Categoria inválida. (RN20)");
        }

        Product product = Product.create(name, description, price, categoryId, sellerId, imageUrls);
        Product saved = productRepository.save(product);

        // Publica evento para inventory-service criar registro de estoque inicial
        eventPublisher.publish("catalog.exchange", "produto.criado", saved);

        return saved;
    }
}
