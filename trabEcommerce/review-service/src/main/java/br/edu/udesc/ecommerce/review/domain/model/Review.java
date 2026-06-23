package br.edu.udesc.ecommerce.review.domain.model;

import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidade de domínio — lado COMMAND do CQRS.
 * Persistida em PostgreSQL (modelo normalizado e auditável).
 */
@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class Review {
    private UUID id;
    private UUID productId;
    private UUID buyerId;
    private int rating;      // RN10: 1–5
    private String comment;  // RN10: 10–500 chars
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Review create(UUID productId, UUID buyerId, int rating, String comment) {
        validateRating(rating);
        validateComment(comment);
        return Review.builder()
                .id(UUID.randomUUID()).productId(productId).buyerId(buyerId)
                .rating(rating).comment(comment)
                .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now())
                .build();
    }

    public void update(int rating, String comment) {
        // RN09: apenas dentro de 30 dias
        if (LocalDateTime.now().isAfter(this.createdAt.plusDays(30))) {
            throw new IllegalStateException("Avaliação não pode ser editada após 30 dias. (RN09)");
        }
        validateRating(rating);
        validateComment(comment);
        this.rating = rating;
        this.comment = comment;
        this.updatedAt = LocalDateTime.now();
    }

    private static void validateRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Nota deve ser entre 1 e 5. (RN10)");
        }
    }

    private static void validateComment(String comment) {
        if (comment == null || comment.length() < 10 || comment.length() > 500) {
            throw new IllegalArgumentException("Comentário deve ter entre 10 e 500 caracteres. (RN10)");
        }
    }
}
