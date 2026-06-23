package br.edu.udesc.ecommerce.review.domain.port.out;

import br.edu.udesc.ecommerce.review.domain.model.Review;
import java.util.Optional;
import java.util.UUID;

/** Porta de saída para o lado COMMAND (PostgreSQL). */
public interface ReviewRepository {
    Review save(Review review);
    Optional<Review> findById(UUID id);
    boolean existsByBuyerIdAndProductId(UUID buyerId, UUID productId);
}
