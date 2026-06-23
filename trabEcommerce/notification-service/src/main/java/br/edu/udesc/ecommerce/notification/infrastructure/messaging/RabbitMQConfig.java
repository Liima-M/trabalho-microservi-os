package br.edu.udesc.ecommerce.notification.infrastructure.messaging;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração das exchanges, queues e bindings do RabbitMQ para o notification-service.
 * Exchanges do tipo topic para roteamento por tipo de evento (RNF05).
 */
@Configuration
public class RabbitMQConfig {

    // Dead-letter exchange para mensagens com falha (RNF05)
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange("notification.dlx");
    }

    @Bean
    public Queue dlq() {
        return QueueBuilder.durable("notification.dlq").build();
    }

    // ---- Pedido Confirmado ----
    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange("order.exchange");
    }

    @Bean
    public Queue pedidoConfirmadoQueue() {
        return QueueBuilder.durable("pedido.confirmado.queue")
                .withArgument("x-dead-letter-exchange", "notification.dlx")
                .build();
    }

    @Bean
    public Binding pedidoConfirmadoBinding() {
        return BindingBuilder.bind(pedidoConfirmadoQueue())
                .to(orderExchange()).with("pedido.confirmado");
    }

    @Bean
    public Queue pedidoCanceladoQueue() {
        return QueueBuilder.durable("pedido.cancelado.queue")
                .withArgument("x-dead-letter-exchange", "notification.dlx").build();
    }

    @Bean
    public Binding pedidoCanceladoBinding() {
        return BindingBuilder.bind(pedidoCanceladoQueue())
                .to(orderExchange()).with("pedido.cancelado");
    }

    @Bean
    public Queue pedidoEntregueQueue() {
        return QueueBuilder.durable("pedido.entregue.queue")
                .withArgument("x-dead-letter-exchange", "notification.dlx").build();
    }

    @Bean
    public Binding pedidoEntregueBinding() {
        return BindingBuilder.bind(pedidoEntregueQueue())
                .to(orderExchange()).with("pedido.entregue");
    }

    // ---- Pagamento ----
    @Bean
    public TopicExchange paymentExchange() {
        return new TopicExchange("payment.exchange");
    }

    @Bean
    public Queue pagamentoAprovadoQueue() {
        return QueueBuilder.durable("pagamento.aprovado.queue")
                .withArgument("x-dead-letter-exchange", "notification.dlx").build();
    }

    @Bean
    public Binding pagamentoAprovadoBinding() {
        return BindingBuilder.bind(pagamentoAprovadoQueue())
                .to(paymentExchange()).with("pagamento.aprovado");
    }

    @Bean
    public Queue pagamentoRecusadoQueue() {
        return QueueBuilder.durable("pagamento.recusado.queue")
                .withArgument("x-dead-letter-exchange", "notification.dlx").build();
    }

    @Bean
    public Binding pagamentoRecusadoBinding() {
        return BindingBuilder.bind(pagamentoRecusadoQueue())
                .to(paymentExchange()).with("pagamento.recusado");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
