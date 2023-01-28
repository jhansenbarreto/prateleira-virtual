package com.prateleiravirtual.api.model.dto.input.id;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa o ID (identificador) de GeneroObra, classe do modelo do
 * domínio. Utilizada apenas para vincular GeneroObra com Obra, através da
 * classe de entrada de dados que representa a classe Obra: ObraInput. Assim, é
 * mantida a maneira como os objetos são aninhados no modelo do domínio também
 * no modelo de entrada (input).
 *
 * @author Jhansen Barreto
 */
@Getter
@Setter
@Schema(description = "Representação dos dados de entrada para associação de Gênero de Obra a uma Obra por ID.")
public class GeneroObraId {

    @NotNull
    @Schema(description = "ID (identificador) do gênero", example = "1")
    private Long id;
}
