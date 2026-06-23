package br.edu.udesc.ecommerce.review.domain.port.in;

import br.edu.udesc.ecommerce.review.domain.model.ReviewProjection;
import java.util.UUID;

public interface GetReviewsUseCase {
    ReviewProjection execute(UUID productId);
}
