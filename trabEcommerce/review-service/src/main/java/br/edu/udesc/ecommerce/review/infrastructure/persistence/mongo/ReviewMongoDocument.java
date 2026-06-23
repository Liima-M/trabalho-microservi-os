package br.edu.udesc.ecommerce.review.infrastructure.persistence.mongo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document(collection = "review_projections")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ReviewMongoDocument {
    @Id
    private String id; // productId
    private UUID productId;
    private double averageRating;
    private int totalReviews;
    private List<ReviewSummaryDoc> latestReviews;
    private LocalDateTime lastUpdated;

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class ReviewSummaryDoc {
        private UUID reviewId;
        private int rating;
        private String comment;
        private LocalDateTime createdAt;
    }
}
