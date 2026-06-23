package br.edu.udesc.ecommerce.payment.infrastructure.persistence.repository;

import br.edu.udesc.ecommerce.payment.domain.model.Payment;
import br.edu.udesc.ecommerce.payment.domain.port.out.PaymentRepository;
import br.edu.udesc.ecommerce.payment.infrastructure.persistence.entity.PaymentJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaPaymentRepository implements PaymentRepository {

    private final PaymentJpaRepository jpaRepository;

    @Override
    public Payment save(Payment payment) {
        return jpaRepository.save(PaymentJpaEntity.from(payment)).toDomain();
    }

    @Override
    public Optional<Payment> findByOrderId(UUID orderId) {
        return jpaRepository.findByOrderId(orderId).map(PaymentJpaEntity::toDomain);
    }
}
