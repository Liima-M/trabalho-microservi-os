package br.edu.udesc.ecommerce.auth.application;

import br.edu.udesc.ecommerce.auth.application.dto.AuthResponse;
import br.edu.udesc.ecommerce.auth.application.dto.RegisterRequest;
import br.edu.udesc.ecommerce.auth.application.usecase.RegisterUserService;
import br.edu.udesc.ecommerce.auth.domain.model.User;
import br.edu.udesc.ecommerce.auth.domain.model.UserType;
import br.edu.udesc.ecommerce.auth.domain.port.out.PasswordEncoder;
import br.edu.udesc.ecommerce.auth.domain.port.out.TokenGenerator;
import br.edu.udesc.ecommerce.auth.domain.port.out.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterUserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private TokenGenerator tokenGenerator;

    private RegisterUserService service;

    @BeforeEach
    void setUp() {
        service = new RegisterUserService(userRepository, passwordEncoder, tokenGenerator);
    }

    @Test
    void shouldRegisterCompradorSuccessfully() {
        RegisterRequest req = new RegisterRequest();
        req.setFirstName("Ana"); req.setLastName("Costa");
        req.setEmail("ana@email.com"); req.setPassword("Ana@1234");
        req.setPhone("47999999999"); req.setUserType(UserType.COMPRADOR);

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("hash");
        when(userRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(tokenGenerator.generateAccessToken(any())).thenReturn("access");
        when(tokenGenerator.generateRefreshToken(any())).thenReturn("refresh");

        AuthResponse response = service.execute(req);

        assertEquals("access", response.getAccessToken());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldRejectDuplicateEmail() {
        RegisterRequest req = new RegisterRequest();
        req.setEmail("dup@email.com"); req.setPassword("Abc12345");
        req.setUserType(UserType.COMPRADOR);

        when(userRepository.existsByEmail("dup@email.com")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.execute(req));
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldRejectVendedorWithoutCnpj() {
        RegisterRequest req = new RegisterRequest();
        req.setEmail("v@email.com"); req.setPassword("Abc12345");
        req.setUserType(UserType.VENDEDOR);
        req.setCnpj(null);

        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> service.execute(req));
    }
}
