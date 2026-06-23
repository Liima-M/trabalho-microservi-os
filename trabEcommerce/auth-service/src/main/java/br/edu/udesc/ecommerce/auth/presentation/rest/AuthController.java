package br.edu.udesc.ecommerce.auth.presentation.rest;

import br.edu.udesc.ecommerce.auth.application.dto.AuthResponse;
import br.edu.udesc.ecommerce.auth.application.dto.LoginRequest;
import br.edu.udesc.ecommerce.auth.application.dto.RegisterRequest;
import br.edu.udesc.ecommerce.auth.domain.port.in.LoginUseCase;
import br.edu.udesc.ecommerce.auth.domain.port.in.RegisterUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Cadastro e login de usuários")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar novo usuário (comprador ou vendedor)")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registerUserUseCase.execute(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuário e obter tokens JWT")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(loginUseCase.execute(request));
    }
}
