package br.edu.udesc.ecommerce.auth.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidade de domínio User. Não depende de JPA nem de nenhum framework.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private String phone;
    private UserType userType;
    private String cnpj; // obrigatório apenas para VENDEDOR (RN02)
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static User create(String firstName, String lastName, String email,
                               String passwordHash, String phone, UserType userType, String cnpj) {
        return User.builder()
                .id(UUID.randomUUID())
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .passwordHash(passwordHash)
                .phone(phone)
                .userType(userType)
                .cnpj(cnpj)
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    /** RN04 — valida requisitos mínimos de senha (domínio puro, sem framework). */
    public static void validatePasswordPolicy(String rawPassword) {
        if (rawPassword == null || rawPassword.length() < 8) {
            throw new IllegalArgumentException("Senha deve ter no mínimo 8 caracteres.");
        }
        boolean hasUpper  = rawPassword.chars().anyMatch(Character::isUpperCase);
        boolean hasLower  = rawPassword.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit  = rawPassword.chars().anyMatch(Character::isDigit);
        if (!hasUpper || !hasLower || !hasDigit) {
            throw new IllegalArgumentException(
                    "Senha deve conter ao menos uma letra maiúscula, uma minúscula e um número.");
        }
    }
}
