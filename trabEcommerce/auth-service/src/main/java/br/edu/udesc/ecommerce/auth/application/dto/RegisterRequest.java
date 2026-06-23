package br.edu.udesc.ecommerce.auth.application.dto;

import br.edu.udesc.ecommerce.auth.domain.model.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank private String firstName;
    @NotBlank private String lastName;
    @Email @NotBlank private String email;
    @NotBlank private String password;
    @NotBlank private String phone;
    @NotNull  private UserType userType;
    private String cnpj; // obrigatório se userType == VENDEDOR
}
