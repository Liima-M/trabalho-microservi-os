package br.edu.udesc.ecommerce.review.domain.port.in;

import br.edu.udesc.ecommerce.review.domain.model.Review;
import java.util.UUID;

public interface CreateReviewUseCase {
    Review execute(UUID productId, UUID buyerId, int rating, String comment);
}
