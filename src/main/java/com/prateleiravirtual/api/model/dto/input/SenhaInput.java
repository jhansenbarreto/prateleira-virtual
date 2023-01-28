package com.prateleiravirtual.api.model.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe de entrada de dados que contém os atributos necessários para a
 * alteração de senha do usuário.
 *
 * @author Jhansen Barreto
 */
@Getter
@Setter
@Schema(description = "Representação dos dados de entrada referentes a alteração de senha de um usuário.")
public class SenhaInput {

    @NotBlank
    @Schema(description = "Nova senha", example = "exemplo#123")
    private String novaSenha;

    @NotBlank
    @Schema(description = "Confirmação de senha (precisa ser igual à nova senha)", example = "exemplo#123")
    private String confirmacaoSenha;

    @NotBlank
    @Schema(description = "Senha atual (última senha cadastrada pelo usuário)", example = "1a2b3c")
    private String senhaAtual;
}
