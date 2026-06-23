package br.edu.udesc.ecommerce.catalog.infrastructure.persistence.repository;

import br.edu.udesc.ecommerce.catalog.infrastructure.persistence.entity.CategoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CategoryJpaRepository extends JpaRepository<CategoryJpaEntity, UUID> {}
