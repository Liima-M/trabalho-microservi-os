package br.edu.udesc.ecommerce.auth.application.usecase;

import br.edu.udesc.ecommerce.auth.domain.model.User;
import br.edu.udesc.ecommerce.auth.domain.port.in.ChangePasswordUseCase;
import br.edu.udesc.ecommerce.auth.domain.port.out.PasswordEncoder;
import br.edu.udesc.ecommerce.auth.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChangePasswordService implements ChangePasswordUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void execute(UUID userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        if (!passwordEncoder.matches(currentPassword, user.getPasswordHash())) {
            throw new IllegalArgumentException("Senha atual incorreta.");
        }

        User.validatePasswordPolicy(newPassword); // RN04
        // A persistência atualizaria o hash — simplificada aqui para foco no domínio
    }
}
