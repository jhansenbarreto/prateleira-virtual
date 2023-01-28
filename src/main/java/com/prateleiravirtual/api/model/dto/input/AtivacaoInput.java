package com.prateleiravirtual.api.model.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe de entrada de dados que representa o código para ativação de um
 * usuário.
 *
 * @author Jhansen Barreto
 */
@Getter
@Setter
@Schema(description = "Representação dos dados de entrada para ativação de um usuário")
public class AtivacaoInput {

    @NotBlank
    @Schema(description = "Código de ativação enviado no e-mail do usuário. Sequência com letras, números e símbolos")
    private String codigo;
}
