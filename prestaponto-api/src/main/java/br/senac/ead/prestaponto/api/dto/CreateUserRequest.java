package br.senac.ead.prestaponto.api.dto;

import br.senac.ead.prestaponto.api.entity.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "O campo 'name' é obrigatório.")
    @Schema(example = "João Silva", description = "Nome completo do usuário")
    private String name;

    @NotBlank(message = "O campo 'email' é obrigatório.")
    @Email(message = "O e-mail deve ser um endereço válido")
    @Schema(example = "joao.silva@example.com", description = "Endereço de email do usuário")
    private String email;
    
    @NotBlank(message = "O campo 'password' é obrigatório.")
    @Schema(example = "senha123", description = "Senha do usuário")
    private String password;

    @NotNull(message = "O campo 'userType' é obrigatório.")
    @Schema(example = "PRESTADOR", description = "Tipo do usuário")
    private UserType userType;
}
