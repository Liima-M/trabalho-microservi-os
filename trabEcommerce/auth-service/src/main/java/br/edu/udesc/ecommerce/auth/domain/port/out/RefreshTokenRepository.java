package br.edu.udesc.ecommerce.auth.domain.port.out;

import br.edu.udesc.ecommerce.auth.domain.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);
    Optional<RefreshToken> findByToken(String token);
    void revokeAllByUserId(java.util.UUID userId);
}
