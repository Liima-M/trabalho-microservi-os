package br.edu.udesc.ecommerce.auth.domain.port.in;

import br.edu.udesc.ecommerce.auth.application.dto.AuthResponse;

public interface RefreshTokenUseCase {
    AuthResponse execute(String refreshToken);
}
