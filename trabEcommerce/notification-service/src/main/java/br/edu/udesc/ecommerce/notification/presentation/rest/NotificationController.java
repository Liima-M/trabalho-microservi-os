package br.edu.udesc.ecommerce.notification.presentation.rest;

import br.edu.udesc.ecommerce.notification.domain.model.Notification;
import br.edu.udesc.ecommerce.notification.domain.port.in.GetNotificationsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Tag(name = "Notificações")
public class NotificationController {

    private final GetNotificationsUseCase getNotificationsUseCase;

    @GetMapping("/{userId}")
    @Operation(summary = "Histórico de notificações do usuário (RF46)")
    public ResponseEntity<List<Notification>> getByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(getNotificationsUseCase.execute(userId));
    }
}
