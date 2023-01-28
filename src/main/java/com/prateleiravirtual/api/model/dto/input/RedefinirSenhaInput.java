package com.prateleiravirtual.api.model.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe de entrada de dados que contém os atributos necessários para redefinir
 * a senha do usuário quando esquecida.
 *
 * @author Jhansen Barreto
 */
@Getter
@Setter
@Schema(description = "Representação dos dados de entrada para redefinição de senha esquecida de um usuário.")
public class RedefinirSenhaInput {

    @NotBlank
    @Schema(description = "Código de redefinição enviado no e-mail do usuário. Sequência com letras, números e símbolos.")
    private String codigo;

    @NotBlank
    @Schema(description = "Nova senha", example = "exemplo#123")
    private String novaSenha;

    @NotBlank
    @Schema(description = "Confirmação de senha (precisa ser igual à nova senha)", example = "exemplo#123")
    private String confirmacaoSenha;
}
