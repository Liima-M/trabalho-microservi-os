package br.edu.udesc.ecommerce.review.presentation.rest;

import br.edu.udesc.ecommerce.review.domain.model.Review;
import br.edu.udesc.ecommerce.review.domain.model.ReviewProjection;
import br.edu.udesc.ecommerce.review.domain.port.in.CreateReviewUseCase;
import br.edu.udesc.ecommerce.review.domain.port.in.GetReviewsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Tag(name = "Avaliações (CQRS)")
public class ReviewController {

    private final CreateReviewUseCase createReviewUseCase;
    private final GetReviewsUseCase getReviewsUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar avaliação (COMMAND — escrita em PostgreSQL + evento para MongoDB)")
    public ResponseEntity<Review> create(
            @RequestParam UUID productId,
            @RequestHeader("X-User-Id") UUID buyerId,
            @RequestParam int rating,
            @RequestParam String comment) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createReviewUseCase.execute(productId, buyerId, rating, comment));
    }

    @GetMapping("/products/{productId}")
    @Operation(summary = "Listar avaliações de um produto (QUERY — leitura do MongoDB)")
    public ResponseEntity<ReviewProjection> getByProduct(@PathVariable UUID productId) {
        return ResponseEntity.ok(getReviewsUseCase.execute(productId));
    }
}
