package br.edu.udesc.ecommerce.payment.infrastructure.circuitbreaker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ResilientRestConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
