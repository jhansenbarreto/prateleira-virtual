package com.prateleiravirtual.api.model.dto.input;

import com.prateleiravirtual.api.model.dto.input.id.GeneroObraId;
import com.prateleiravirtual.api.model.dto.input.id.EditoraId;
import com.prateleiravirtual.api.model.dto.input.id.TipoObraId;
import com.prateleiravirtual.core.validation.annotation.AnoValido;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe de entrada de dados que contém os atributos necessários para o
 * cadastro de uma obra.
 *
 * @author Jhansen Barreto
 */
@Getter
@Setter
@Schema(description = "Representação dos dados de entrada para cadastro ou atualização de uma obra")
public class ObraInput {

    @NotBlank
    @Schema(description = "Título da obra", example = "Memórias da Princesa")
    private String titulo;

    @Schema(description = "Subtítulo da obra", example = "Os diários de Carrie Fisher")
    private String subtitulo;
    
    @AnoValido(min = 1500)
    @NotNull
    @Schema(description = "Ano de publicação da obra", example = "2016")
    private Integer ano;

    @NotBlank
    @Schema(description = "Breve descrição da obra", example = "Uma snopse da obra")
    private String descricao;

    @Min(1)
    @NotNull
    @Schema(description = "Quantidade de páginas da obra", example = "222")
    private Integer paginas;

    @Valid
    @NotNull
    //Descrição na própria classe
    private TipoObraId tipo;
    
    @Valid
    @NotNull
    //Descrição na própria classe
    private GeneroObraId genero;

    @Valid
    @NotNull
    //Descrição na própria classe
    private EditoraId editora;
    
    @Schema(description = "Coleção com ID's (identificadores) dos autores da obra", example = "[1, 2, 3]")
    private List<Long> autores;
}
