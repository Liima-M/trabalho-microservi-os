package br.edu.udesc.ecommerce.catalog.application.usecase;

import br.edu.udesc.ecommerce.catalog.domain.model.Product;
import br.edu.udesc.ecommerce.catalog.domain.port.in.GetProductUseCase;
import br.edu.udesc.ecommerce.catalog.domain.port.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetProductService implements GetProductUseCase {

    private final ProductRepository productRepository;

    @Override
    public Product execute(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
    }
}
