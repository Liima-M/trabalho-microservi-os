package br.edu.udesc.ecommerce.auth.domain;

import br.edu.udesc.ecommerce.auth.domain.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void shouldCreateUserSuccessfully() {
        User user = User.create("João", "Silva", "joao@email.com",
                "hashedPwd", "47999999999",
                br.edu.udesc.ecommerce.auth.domain.model.UserType.COMPRADOR, null);

        assertNotNull(user.getId());
        assertEquals("joao@email.com", user.getEmail());
        assertTrue(user.isActive());
    }

    @Test
    void shouldRejectWeakPassword() {
        assertThrows(IllegalArgumentException.class,
                () -> User.validatePasswordPolicy("abc"));
    }

    @Test
    void shouldAcceptStrongPassword() {
        assertDoesNotThrow(() -> User.validatePasswordPolicy("Abc12345"));
    }

    @Test
    void shouldRejectPasswordWithoutUppercase() {
        assertThrows(IllegalArgumentException.class,
                () -> User.validatePasswordPolicy("abc12345"));
    }

    @Test
    void shouldRejectPasswordWithoutDigit() {
        assertThrows(IllegalArgumentException.class,
                () -> User.validatePasswordPolicy("AbcDefGh"));
    }
}
