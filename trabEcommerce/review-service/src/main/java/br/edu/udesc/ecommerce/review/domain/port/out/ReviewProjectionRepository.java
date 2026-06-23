package br.edu.udesc.ecommerce.review.domain.port.out;

import br.edu.udesc.ecommerce.review.domain.model.ReviewProjection;
import java.util.Optional;
import java.util.UUID;

/** Porta de saída para o lado QUERY (MongoDB). */
public interface ReviewProjectionRepository {
    ReviewProjection save(ReviewProjection projection);
    Optional<ReviewProjection> findByProductId(UUID productId);
}
