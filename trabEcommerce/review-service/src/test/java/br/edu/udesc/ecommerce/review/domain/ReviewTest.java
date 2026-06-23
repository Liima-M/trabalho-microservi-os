package br.edu.udesc.ecommerce.review.domain;

import br.edu.udesc.ecommerce.review.domain.model.Review;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {

    @Test
    void shouldCreateValidReview() {
        Review r = Review.create(UUID.randomUUID(), UUID.randomUUID(), 5,
                "Produto excelente, superou as expectativas!");
        assertNotNull(r.getId());
        assertEquals(5, r.getRating());
    }

    @Test
    void shouldRejectRatingBelowOne() {
        assertThrows(IllegalArgumentException.class,
                () -> Review.create(UUID.randomUUID(), UUID.randomUUID(), 0, "Comentário válido ok"));
    }

    @Test
    void shouldRejectRatingAboveFive() {
        assertThrows(IllegalArgumentException.class,
                () -> Review.create(UUID.randomUUID(), UUID.randomUUID(), 6, "Comentário válido ok"));
    }

    @Test
    void shouldRejectShortComment() {
        assertThrows(IllegalArgumentException.class,
                () -> Review.create(UUID.randomUUID(), UUID.randomUUID(), 3, "Curto"));
    }

    @Test
    void shouldRejectCommentWithMoreThan500Chars() {
        String longComment = "A".repeat(501);
        assertThrows(IllegalArgumentException.class,
                () -> Review.create(UUID.randomUUID(), UUID.randomUUID(), 3, longComment));
    }
}
