package com.prateleiravirtual.api.model.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe de entrada de dados que contém os atributos necessários para a
 * atualização de um usuário.
 *
 * @author Jhansen Barreto
 */
@Getter
@Setter
@Schema(description = "Representação dos dados de entrada para atualização de um usuário.")
public class UsuarioUpdateInput {

    @NotBlank
    @Schema(description = "Nome de usuário", example = "usuario123")
    private String username;

    @Email
    @NotBlank
    @Schema(description = "E-mail do usuário", example = "usuario@exemplo.com")
    private String email;
}
