package br.edu.udesc.ecommerce.auth.domain.port.out;

import br.edu.udesc.ecommerce.auth.domain.model.User;

public interface TokenGenerator {
    String generateAccessToken(User user);
    String generateRefreshToken(User user);
    String extractUserId(String token);
    boolean isValid(String token);
}
