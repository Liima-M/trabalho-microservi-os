package br.edu.udesc.ecommerce.notification.domain.port.in;

import br.edu.udesc.ecommerce.notification.domain.model.Notification;
import java.util.List;
import java.util.UUID;

public interface GetNotificationsUseCase {
    List<Notification> execute(UUID userId);
}
