package com.prateleiravirtual.api.model.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe de entrada de dados que contém os atributos necessários para o
 * cadastro de uma editora.
 *
 * @author Jhansen Barreto
 */
@Getter
@Setter
@Schema(description = "Representação dos dados de entrada para cadastro ou atualização de uma editora")
public class EditoraInput {

    @NotBlank
    @Schema(description = "Nome da editora", example = "GLOBO LIVROS")
    private String nome;
}
