package br.edu.udesc.ecommerce.review.application.usecase;

import br.edu.udesc.ecommerce.review.domain.model.Review;
import br.edu.udesc.ecommerce.review.domain.port.in.CreateReviewUseCase;
import br.edu.udesc.ecommerce.review.domain.port.out.EventPublisher;
import br.edu.udesc.ecommerce.review.domain.port.out.OrderServiceClient;
import br.edu.udesc.ecommerce.review.domain.port.out.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

/**
 * Comando CQRS: persiste avaliação e publica evento para atualizar projeção.
 * RN08: valida compra prévia via order-service.
 */
@Service
@RequiredArgsConstructor
public class CreateReviewService implements CreateReviewUseCase {

    private final ReviewRepository reviewRepository;
    private final OrderServiceClient orderServiceClient;
    private final EventPublisher eventPublisher;

    @Override
    @Transactional
    public Review execute(UUID productId, UUID buyerId, int rating, String comment) {
        // RN08 — apenas compradores com pedido ENTREGUE podem avaliar
        if (!orderServiceClient.hasPurchasedProduct(buyerId, productId)) {
            throw new IllegalStateException(
                    "Apenas compradores com pedido entregue podem avaliar este produto. (RN08)");
        }

        // Um comprador, uma avaliação por produto
        if (reviewRepository.existsByBuyerIdAndProductId(buyerId, productId)) {
            throw new IllegalStateException("Você já avaliou este produto.");
        }

        Review review = Review.create(productId, buyerId, rating, comment);
        Review saved = reviewRepository.save(review);

        // Publica evento para o Projector atualizar a projeção MongoDB (padrão Observer)
        eventPublisher.publish("review.exchange", "review.criada", saved);

        return saved;
    }
}
