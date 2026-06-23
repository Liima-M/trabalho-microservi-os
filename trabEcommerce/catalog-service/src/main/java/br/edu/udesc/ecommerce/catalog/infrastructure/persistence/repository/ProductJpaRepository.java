package br.edu.udesc.ecommerce.catalog.infrastructure.persistence.repository;

import br.edu.udesc.ecommerce.catalog.infrastructure.persistence.entity.ProductJpaEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, UUID> {

    @Query("SELECT p FROM ProductJpaEntity p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<ProductJpaEntity> searchByName(@Param("name") String name, Pageable pageable);

    List<ProductJpaEntity> findByCategoryIdAndPriceBetween(UUID categoryId, BigDecimal min, BigDecimal max, Pageable pageable);
}
