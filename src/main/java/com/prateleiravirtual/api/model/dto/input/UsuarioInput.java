package com.prateleiravirtual.api.model.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe de entrada de dados que contém os atributos necessários para o
 * cadastro de um usuário.
 *
 * @author Jhansen Barreto
 */
@Getter
@Setter
@Schema(description = "Representação dos dados de entrada para cadastro de um usuário")
public class UsuarioInput {

    @NotBlank
    @Schema(description = "Nome de usuário", example = "usuario123")
    private String username;
    
    @NotBlank
    @Schema(description = "E-mail do usuário", example = "usuario@exemplo.com")
    private String email;
    
    @NotBlank
    @Schema(description = "Senha do usuário", example = "exemplo#123")
    private String senha;
    
    @NotBlank
    @Schema(description = "Confirmação de senha (precisa ser igual à senha)", example = "exemplo#123")
    private String confirmacaoSenha;
}
