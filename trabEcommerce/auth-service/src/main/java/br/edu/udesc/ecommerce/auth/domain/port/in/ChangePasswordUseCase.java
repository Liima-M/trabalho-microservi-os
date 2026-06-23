package br.edu.udesc.ecommerce.auth.domain.port.in;

import java.util.UUID;

public interface ChangePasswordUseCase {
    void execute(UUID userId, String currentPassword, String newPassword);
}
