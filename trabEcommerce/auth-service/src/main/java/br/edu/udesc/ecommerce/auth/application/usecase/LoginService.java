package br.edu.udesc.ecommerce.auth.application.usecase;

import br.edu.udesc.ecommerce.auth.application.dto.AuthResponse;
import br.edu.udesc.ecommerce.auth.application.dto.LoginRequest;
import br.edu.udesc.ecommerce.auth.domain.model.User;
import br.edu.udesc.ecommerce.auth.domain.port.in.LoginUseCase;
import br.edu.udesc.ecommerce.auth.domain.port.out.PasswordEncoder;
import br.edu.udesc.ecommerce.auth.domain.port.out.TokenGenerator;
import br.edu.udesc.ecommerce.auth.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Caso de uso: Realizar login.
 * Mensagem genérica para evitar enumeração de usuários (CU12.2 E1/E2).
 */
@Service
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerator tokenGenerator;

    @Override
    public AuthResponse execute(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("E-mail ou senha incorretos."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("E-mail ou senha incorretos.");
        }

        return AuthResponse.of(
                tokenGenerator.generateAccessToken(user),
                tokenGenerator.generateRefreshToken(user)
        );
    }
}
