package com.prateleiravirtual.api.model.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe de entrada de dados que contém os atributos necessários para o
 * cadastro de um gênero de obra.
 *
 * @author Jhansen Barreto
 */
@Getter
@Setter
@Schema(description = "Representação dos dados de entrada para cadastro ou atualização de um gênero de obra")
public class GeneroObraInput {

    @NotBlank
    @Schema(description = "Descrição do gênero", example = "ROMANCE")
    private String descricao;
}
