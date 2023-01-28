package com.prateleiravirtual.api.model.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe de entrada de dados que representa o e-mail de um usuário cadastrado
 * que pode ser solicitado para alguma tarefa específica, como alterar uma senha
 * que foi esquecida, por exemplo.
 *
 * @author Jhansen Barreto
 */
@Getter
@Setter
@Schema(description = "Representação do e-mail de um usuário cadastrado como dado de entrada para envio de e-mails")
public class EmailInput {

    @NotBlank
    @Schema(description = "E-mail do usuário", example = "usuario@exemplo.com")
    private String email;
}
