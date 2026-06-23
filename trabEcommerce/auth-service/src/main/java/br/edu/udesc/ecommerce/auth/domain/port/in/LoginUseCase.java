package br.edu.udesc.ecommerce.auth.domain.port.in;

import br.edu.udesc.ecommerce.auth.application.dto.AuthResponse;
import br.edu.udesc.ecommerce.auth.application.dto.LoginRequest;

public interface LoginUseCase {
    AuthResponse execute(LoginRequest request);
}
