package br.edu.udesc.ecommerce.cart.infrastructure.persistence.repository;

import br.edu.udesc.ecommerce.cart.domain.model.Cart;
import br.edu.udesc.ecommerce.cart.domain.port.out.CartRepository;
import br.edu.udesc.ecommerce.cart.infrastructure.persistence.entity.CartJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaCartRepository implements CartRepository {

    private final CartJpaRepository jpaRepository;

    @Override
    public Cart save(Cart cart) {
        return jpaRepository.save(CartJpaEntity.from(cart)).toDomain();
    }

    @Override
    public Optional<Cart> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId).map(CartJpaEntity::toDomain);
    }
}
