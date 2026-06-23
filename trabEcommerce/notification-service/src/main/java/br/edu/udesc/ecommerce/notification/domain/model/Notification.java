package br.edu.udesc.ecommerce.notification.domain.model;

import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class Notification {
    private UUID id;
    private UUID userId;
    private NotificationType type;
    private String title;
    private String message;
    private boolean sent;
    private LocalDateTime createdAt;

    public static Notification create(UUID userId, NotificationType type,
                                       String title, String message) {
        return Notification.builder()
                .id(UUID.randomUUID()).userId(userId).type(type)
                .title(title).message(message).sent(false)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
