package com.prateleiravirtual.api.model.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe criada para evitar expor o modelo do domínio utilizando o padrão de
 * projeto DTO (Data Transfer Object). Esta classe é utilizada para a API servir
 * as informações equivalente à classe Autor, localizada no pacote domain.model.
 *
 * @author Jhansen Barreto
 */
@Getter
@Setter
@Schema(description = "Representação de um autor. Dados servidos na solicitação de um recurso.")
public class AutorOutput {

    @Schema(description = "ID (identificador) do autor", example = "1")
    private Long id;

    @Schema(description = "Nome do autor", example = "STEPHEN KING")
    private String nome;

    @Schema(description = "URL de acesso à imagem do autor", example = "https://app.box.com/shared/static/exemplo.png")
    private String imagem;
}
