package br.edu.udesc.ecommerce.notification.domain.port.out;

import br.edu.udesc.ecommerce.notification.domain.model.Notification;
import java.util.List;
import java.util.UUID;

public interface NotificationRepository {
    Notification save(Notification notification);
    List<Notification> findByUserId(UUID userId);
    boolean isEventAlreadyProcessed(String eventId); // idempotência (RN16)
    void markEventAsProcessed(String eventId);
}
