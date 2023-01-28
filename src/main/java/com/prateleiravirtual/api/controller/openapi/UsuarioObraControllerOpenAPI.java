package com.prateleiravirtual.api.controller.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Interface criada para separar da implementação do código, a documentação
 * utilizando Swagger UI com as especificações do OpenAPI 3.0
 *
 * @author Jhansen Barreto
 */
@Tag(name = "Usuários e Obras", description = "Gerencia a vinculação e desvinculação de obras das listas de leitura dos usuários")
public interface UsuarioObraControllerOpenAPI {

    @Operation(summary = "Vincula uma obra na lista de obras lidas de um usuário")
    public void vincularObraLida(
            @Parameter(
                    description = "ID de um usuário",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long usuarioId,
            @Parameter(
                    description = "ID de uma obra",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long obraId);

    @Operation(summary = "Desvincula uma obra da lista de obras lidas de um usuário")
    public void desvincularObraLida(
            @Parameter(
                    description = "ID de um usuário",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long usuarioId,
            @Parameter(
                    description = "ID de uma obra",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long obraId);

    @Operation(summary = "Vincula uma obra na lista de obras que se deseja ler de um usuário")
    public void vincularObraParaLer(
            @Parameter(
                    description = "ID de um usuário",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long usuarioId,
            @Parameter(
                    description = "ID de uma obra",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long obraId);

    @Operation(summary = "Desvincula uma obra da lista de obras que se deseja ler de um usuário")
    public void desvincularObraParaLer(
            @Parameter(
                    description = "ID de um usuário",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long usuarioId,
            @Parameter(
                    description = "ID de uma obra",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long obraId);

    @Operation(summary = "Vincula uma obra na lista de obras que estão sendo lidas de um usuário")
    public void vincularObraEmLeitura(
            @Parameter(
                    description = "ID de um usuário",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long usuarioId,
            @Parameter(
                    description = "ID de uma obra",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long obraId);

    @Operation(summary = "Desvincula uma obra da lista de obras que estão sendo lidas de um usuário")
    public void desvincularObraEmLeitura(
            @Parameter(
                    description = "ID de um usuário",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long usuarioId,
            @Parameter(
                    description = "ID de uma obra",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long obraId);
}
