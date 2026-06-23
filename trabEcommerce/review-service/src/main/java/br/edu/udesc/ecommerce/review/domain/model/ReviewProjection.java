package br.edu.udesc.ecommerce.review.domain.model;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Projeção de leitura — lado QUERY do CQRS.
 * Persistida em MongoDB, otimizada para consultas do catálogo.
 * Atualizada assincronamente via eventos RabbitMQ. (RN15: latência máx. 5s)
 */
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ReviewProjection {
    private String id;           // productId como string (MongoDB _id)
    private UUID productId;
    private double averageRating;
    private int totalReviews;
    private List<ReviewSummary> latestReviews;
    private LocalDateTime lastUpdated;

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class ReviewSummary {
        private UUID reviewId;
        private int rating;
        private String comment;
        private LocalDateTime createdAt;
    }
}
