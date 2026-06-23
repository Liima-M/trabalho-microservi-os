package br.edu.udesc.ecommerce.auth.application.usecase;

import br.edu.udesc.ecommerce.auth.application.dto.AuthResponse;
import br.edu.udesc.ecommerce.auth.application.dto.RegisterRequest;
import br.edu.udesc.ecommerce.auth.domain.model.User;
import br.edu.udesc.ecommerce.auth.domain.model.UserType;
import br.edu.udesc.ecommerce.auth.domain.port.in.RegisterUserUseCase;
import br.edu.udesc.ecommerce.auth.domain.port.out.PasswordEncoder;
import br.edu.udesc.ecommerce.auth.domain.port.out.TokenGenerator;
import br.edu.udesc.ecommerce.auth.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Caso de uso: Cadastrar usuário.
 * Aplica regras de negócio: RN02, RN03, RN04.
 */
@Service
@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerator tokenGenerator;

    @Override
    @Transactional
    public AuthResponse execute(RegisterRequest request) {
        // RN03 — e-mail único
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado no sistema.");
        }

        // RN02 — CNPJ obrigatório para vendedor
        if (UserType.VENDEDOR.equals(request.getUserType())
                && (request.getCnpj() == null || request.getCnpj().isBlank())) {
            throw new IllegalArgumentException("CNPJ é obrigatório para cadastro de vendedor.");
        }

        // RN04 — política de senha
        User.validatePasswordPolicy(request.getPassword());

        String hash = passwordEncoder.encode(request.getPassword());
        User user = User.create(
                request.getFirstName(), request.getLastName(),
                request.getEmail(), hash,
                request.getPhone(), request.getUserType(), request.getCnpj());

        User saved = userRepository.save(user);

        String accessToken  = tokenGenerator.generateAccessToken(saved);
        String refreshToken = tokenGenerator.generateRefreshToken(saved);

        return AuthResponse.of(accessToken, refreshToken);
    }
}
