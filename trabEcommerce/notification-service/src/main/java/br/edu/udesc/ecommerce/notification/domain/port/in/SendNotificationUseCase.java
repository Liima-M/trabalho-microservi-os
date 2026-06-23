package br.edu.udesc.ecommerce.notification.domain.port.in;

import br.edu.udesc.ecommerce.notification.domain.model.Notification;
import br.edu.udesc.ecommerce.notification.domain.model.NotificationType;
import java.util.UUID;

public interface SendNotificationUseCase {
    Notification execute(UUID userId, NotificationType type, String title, String message);
}
