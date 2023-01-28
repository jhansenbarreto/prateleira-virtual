package com.prateleiravirtual.api.model.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe criada para evitar expor o modelo do domínio utilizando o padrão de
 * projeto DTO (Data Transfer Object). Esta classe é utilizada para a API servir
 * as informações equivalente à classe Usuario, localizada no pacote
 * domain.model.
 *
 * @author Jhansen Barreto
 */
@Getter
@Setter
@Schema(description = "Representação de um usuário. Dados servidos na solicitação de um recurso.")
public class UsuarioOutput {

    @Schema(description = "ID (identificador) do usuário", example = "1")
    private Long id;

    @Schema(description = "Username do usuário", example = "usuario123")
    private String username;

    @Schema(description = "E-mail do usuário", example = "usuario@exemplo.com")
    private String email;

    @Schema(description = "Status do usuário", example = "false")
    private Boolean ativo;

    @Schema(description = "URL de acesso à imagem do usuário", example = "https://app.box.com/shared/static/exemplo.png")
    private String imagem;
}
