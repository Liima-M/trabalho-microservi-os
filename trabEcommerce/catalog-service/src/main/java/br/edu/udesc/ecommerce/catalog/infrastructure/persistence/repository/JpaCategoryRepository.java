package br.edu.udesc.ecommerce.catalog.infrastructure.persistence.repository;

import br.edu.udesc.ecommerce.catalog.domain.model.Category;
import br.edu.udesc.ecommerce.catalog.domain.port.out.CategoryRepository;
import br.edu.udesc.ecommerce.catalog.infrastructure.persistence.entity.CategoryJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaCategoryRepository implements CategoryRepository {

    private final CategoryJpaRepository jpaRepository;

    @Override
    public Optional<Category> findById(UUID id) {
        return jpaRepository.findById(id).map(CategoryJpaEntity::toDomain);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }
}
