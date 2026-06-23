package br.edu.udesc.ecommerce.catalog.infrastructure.persistence.repository;

import br.edu.udesc.ecommerce.catalog.domain.model.Product;
import br.edu.udesc.ecommerce.catalog.domain.port.out.ProductRepository;
import br.edu.udesc.ecommerce.catalog.infrastructure.persistence.entity.ProductJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JpaProductRepository implements ProductRepository {

    private final ProductJpaRepository jpaRepository;

    @Override
    public Product save(Product product) {
        return jpaRepository.save(ProductJpaEntity.from(product)).toDomain();
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return jpaRepository.findById(id).map(ProductJpaEntity::toDomain);
    }

    @Override
    public List<Product> findByNameContaining(String name, int page, int size) {
        return jpaRepository.searchByName(name, PageRequest.of(page, size))
                .stream().map(ProductJpaEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Product> findByCategoryAndPriceRange(UUID categoryId, BigDecimal min, BigDecimal max, int page, int size) {
        return jpaRepository.findByCategoryIdAndPriceBetween(categoryId, min, max, PageRequest.of(page, size))
                .stream().map(ProductJpaEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }
}
