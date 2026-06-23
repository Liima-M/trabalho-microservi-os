package br.edu.udesc.ecommerce.payment.domain.port.out;

public interface EventPublisher {
    void publish(String exchange, String routingKey, Object event);
}
