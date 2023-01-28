package com.prateleiravirtual.api.controller.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

/**
 * Interface criada para separar da implementação do código, a documentação
 * utilizando Swagger UI com as especificações do OpenAPI 3.0
 *
 * @author Jhansen Barreto
 */
@Tag(name = "Obras e Autores", description = "Gerencia a vinculação e desvinculação entre autores e obras")
public interface ObraAutorControllerOpenAPI {

    @Operation(summary = "Vincula um autor a uma obra")
    public void vincularAutor(
            @Parameter(
                    description = "ID de uma obra",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long obraId,
            @Parameter(
                    description = "ID de um autor",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long autorId);

    @Operation(summary = "Vincula um ou mais autores a uma obra")
    public void vincularAutores(
            @Parameter(
                    description = "ID de uma obra",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long obraId,
            @RequestBody(
                    description = "Coleção com ID's de autores"
            ) List<Long> ids);

    @Operation(summary = "Desvincula um autor de uma obra")
    public void desvincularAutor(
            @Parameter(
                    description = "ID de uma obra",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long obraId,
            @Parameter(
                    description = "ID de um autor",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long autorId);

    @Operation(summary = "Desvincula um ou mais autores de uma obra")
    public void desvincularAutores(
            @Parameter(
                    description = "ID de uma obra",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long obraId,
            @RequestBody(
                    description = "Coleção com ID's de autores"
            ) List<Long> ids);
}
