package br.edu.udesc.ecommerce.review.application.usecase;

import br.edu.udesc.ecommerce.review.domain.model.ReviewProjection;
import br.edu.udesc.ecommerce.review.domain.port.in.GetReviewsUseCase;
import br.edu.udesc.ecommerce.review.domain.port.out.ReviewProjectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

/**
 * Query CQRS: serve leitura diretamente da projeção MongoDB,
 * sem tocar no banco de comando (PostgreSQL).
 */
@Service
@RequiredArgsConstructor
public class GetReviewsService implements GetReviewsUseCase {

    private final ReviewProjectionRepository projectionRepository;

    @Override
    public ReviewProjection execute(UUID productId) {
        return projectionRepository.findByProductId(productId)
                .orElse(ReviewProjection.builder()
                        .productId(productId).averageRating(0.0)
                        .totalReviews(0).build());
    }
}
