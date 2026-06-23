package br.edu.udesc.ecommerce.shipping.domain.port.out;

public interface EventPublisher {
    void publish(String exchange, String routingKey, Object event);
}
