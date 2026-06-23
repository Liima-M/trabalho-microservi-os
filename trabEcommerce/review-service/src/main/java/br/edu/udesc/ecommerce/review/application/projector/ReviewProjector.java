package br.edu.udesc.ecommerce.review.application.projector;

import br.edu.udesc.ecommerce.review.domain.model.Review;
import br.edu.udesc.ecommerce.review.domain.model.ReviewProjection;
import br.edu.udesc.ecommerce.review.domain.port.out.ReviewProjectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Projector CQRS: consome eventos de avaliação e atualiza a projeção MongoDB.
 * Implementa o papel de Observer (GoF) — reage a eventos sem acoplamento com o emissor.
 * RN15: atualização assíncrona com latência máxima esperada de 5 segundos.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewProjector {

    private final ReviewProjectionRepository projectionRepository;

    @RabbitListener(queues = "review.criada.queue")
    public void onReviewCreated(Review review) {
        log.info("[CQRS] Atualizando projeção para produto {} após nova avaliação", review.getProductId());

        ReviewProjection projection = projectionRepository
                .findByProductId(review.getProductId())
                .orElse(ReviewProjection.builder()
                        .id(review.getProductId().toString())
                        .productId(review.getProductId())
                        .totalReviews(0)
                        .averageRating(0.0)
                        .latestReviews(new ArrayList<>())
                        .build());

        // Recalcula média de forma incremental
        double newAvg = ((projection.getAverageRating() * projection.getTotalReviews()) + review.getRating())
                / (projection.getTotalReviews() + 1);

        List<ReviewProjection.ReviewSummary> latest = new ArrayList<>(projection.getLatestReviews());
        latest.add(0, ReviewProjection.ReviewSummary.builder()
                .reviewId(review.getId()).rating(review.getRating())
                .comment(review.getComment()).createdAt(review.getCreatedAt()).build());

        if (latest.size() > 10) latest = latest.subList(0, 10); // mantém as 10 mais recentes

        ReviewProjection updated = ReviewProjection.builder()
                .id(projection.getId())
                .productId(projection.getProductId())
                .averageRating(newAvg)
                .totalReviews(projection.getTotalReviews() + 1)
                .latestReviews(latest)
                .lastUpdated(LocalDateTime.now())
                .build();

        projectionRepository.save(updated);
        log.info("[CQRS] Projeção atualizada: produto={} avgRating={}", review.getProductId(), newAvg);
    }
}
