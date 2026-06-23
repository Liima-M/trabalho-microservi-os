package br.edu.udesc.ecommerce.notification.application.usecase;

import br.edu.udesc.ecommerce.notification.domain.model.Notification;
import br.edu.udesc.ecommerce.notification.domain.model.NotificationType;
import br.edu.udesc.ecommerce.notification.domain.port.in.SendNotificationUseCase;
import br.edu.udesc.ecommerce.notification.domain.port.out.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendNotificationService implements SendNotificationUseCase {

    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public Notification execute(UUID userId, NotificationType type, String title, String message) {
        Notification notification = Notification.create(userId, type, title, message);
        Notification saved = notificationRepository.save(notification);
        // Em produção: enviar e-mail via JavaMailSender / serviço externo
        log.info("[NOTIFICATION] Enviando {} para usuário {}: {}", type, userId, title);
        return saved;
    }
}
