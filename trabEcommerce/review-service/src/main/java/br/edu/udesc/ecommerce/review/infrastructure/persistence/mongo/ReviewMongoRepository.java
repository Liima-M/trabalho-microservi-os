package br.edu.udesc.ecommerce.review.infrastructure.persistence.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import java.util.UUID;

public interface ReviewMongoRepository extends MongoRepository<ReviewMongoDocument, String> {
    Optional<ReviewMongoDocument> findByProductId(UUID productId);
}
