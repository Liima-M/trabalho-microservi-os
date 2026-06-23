package br.edu.udesc.ecommerce.notification.domain;

import br.edu.udesc.ecommerce.notification.domain.model.Notification;
import br.edu.udesc.ecommerce.notification.domain.model.NotificationType;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    @Test
    void shouldCreateNotificationSuccessfully() {
        Notification n = Notification.create(UUID.randomUUID(),
                NotificationType.PEDIDO_CONFIRMADO,
                "Pedido confirmado!", "Seu pedido #123 foi confirmado.");
        assertNotNull(n.getId());
        assertFalse(n.isSent());
        assertEquals(NotificationType.PEDIDO_CONFIRMADO, n.getType());
    }
}
