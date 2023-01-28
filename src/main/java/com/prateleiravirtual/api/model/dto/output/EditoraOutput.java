package com.prateleiravirtual.api.model.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe criada para evitar expor o modelo do domínio utilizando o padrão de
 * projeto DTO (Data Transfer Object). Esta classe é utilizada para a API servir
 * as informações equivalente à classe Editora, localizada no pacote
 * domain.model.
 *
 * @author Jhansen Barreto
 */
@Getter
@Setter
@Schema(description = "Representação de uma editora. Dados servidos na solicitação de um recurso.")
public class EditoraOutput {

    @Schema(description = "ID (identificador) da editora", example = "1")
    private Long id;

    @Schema(description = "Nome da editora", example = "SUMA")
    private String nome;
}
