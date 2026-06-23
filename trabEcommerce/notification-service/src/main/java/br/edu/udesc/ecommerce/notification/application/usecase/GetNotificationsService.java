package br.edu.udesc.ecommerce.notification.application.usecase;

import br.edu.udesc.ecommerce.notification.domain.model.Notification;
import br.edu.udesc.ecommerce.notification.domain.port.in.GetNotificationsUseCase;
import br.edu.udesc.ecommerce.notification.domain.port.out.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetNotificationsService implements GetNotificationsUseCase {

    private final NotificationRepository notificationRepository;

    @Override
    public List<Notification> execute(UUID userId) {
        return notificationRepository.findByUserId(userId);
    }
}
