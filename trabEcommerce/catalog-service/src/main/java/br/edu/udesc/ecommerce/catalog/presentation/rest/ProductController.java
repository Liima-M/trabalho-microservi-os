package br.edu.udesc.ecommerce.catalog.presentation.rest;

import br.edu.udesc.ecommerce.catalog.domain.model.Product;
import br.edu.udesc.ecommerce.catalog.domain.port.in.CreateProductUseCase;
import br.edu.udesc.ecommerce.catalog.domain.port.in.GetProductUseCase;
import br.edu.udesc.ecommerce.catalog.domain.port.in.SearchProductUseCase;
import br.edu.udesc.ecommerce.catalog.domain.strategy.ProductSearchCriteria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/catalog/products")
@RequiredArgsConstructor
@Tag(name = "Catálogo de Produtos")
public class ProductController {

    private final GetProductUseCase getProductUseCase;
    private final SearchProductUseCase searchProductUseCase;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID")
    public ResponseEntity<Product> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(getProductUseCase.execute(id));
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar produtos (Strategy: por nome, categoria, faixa de preço)")
    public ResponseEntity<List<Product>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        ProductSearchCriteria criteria = ProductSearchCriteria.builder()
                .name(name).categoryId(categoryId)
                .minPrice(minPrice).maxPrice(maxPrice)
                .page(page).size(size).build();

        return ResponseEntity.ok(searchProductUseCase.execute(criteria));
    }
}
