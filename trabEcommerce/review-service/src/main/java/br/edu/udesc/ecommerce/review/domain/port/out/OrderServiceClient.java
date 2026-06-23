package br.edu.udesc.ecommerce.review.domain.port.out;

import java.util.UUID;

/** Verifica se o comprador efetivamente adquiriu o produto. (RF40, RN08) */
public interface OrderServiceClient {
    boolean hasPurchasedProduct(UUID buyerId, UUID productId);
}
