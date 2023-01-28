package com.prateleiravirtual.api.model.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe de entrada de dados que contém os atributos necessários para o
 * cadastro de um autor sem o arquivo de imagem associado.
 *
 * @author Jhansen Barreto
 */
@Getter
@Setter
@Schema(description = "Representação dos dados de entrada para cadastro ou atualização de um autor")
public class AutorInput {

    @NotBlank
    @Schema(description = "Nome do autor", example = "DAN BROWN")
    private String nome;
}
