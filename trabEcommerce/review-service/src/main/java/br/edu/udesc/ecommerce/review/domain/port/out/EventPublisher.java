package br.edu.udesc.ecommerce.review.domain.port.out;

public interface EventPublisher {
    void publish(String exchange, String routingKey, Object event);
}
