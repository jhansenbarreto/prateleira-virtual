package com.prateleiravirtual.api.controller.openapi;

import com.prateleiravirtual.api.model.dto.input.TipoObraInput;
import com.prateleiravirtual.api.model.dto.output.ResumoObraOutput;
import com.prateleiravirtual.api.model.dto.output.TipoObraOutput;

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
@Tag(name = "Tipos de Obras", description = "Gerencia tipos de obras")
public interface TipoObraControllerOpenAPI {

    @Operation(summary = "Lista todas as obras de um tipo por ID")
    public List<ResumoObraOutput> listarObras(
            @Parameter(
                    description = "ID de um tipo",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long id);

    @Operation(summary = "Lista todos os tipos cadastrados")
    public List<TipoObraOutput> listar();

    @Operation(summary = "Busca um tipo por ID")
    public TipoObraOutput buscar(
            @Parameter(
                    description = "ID de um tipo",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long id);

    @Operation(summary = "Lista todos os tipos pela descrição ou parte da mesma")
    public List<TipoObraOutput> pesquisar(
            @Parameter(
                    description = "Descrição completa de um tipo ou parte da mesma",
                    example = "a"
            ) String descricao);

    @Operation(summary = "Cadastra um tipo")
    public TipoObraOutput adicionar(
            @RequestBody(
                    description = "Representação de um novo tipo"
            ) TipoObraInput input);

    @Operation(summary = "Atualiza um tipo por ID")
    public TipoObraOutput atualizar(
            @Parameter(
                    description = "ID de um tipo",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long id,
            @RequestBody(
                    description = "Representação de um tipo com os novos dados"
            ) TipoObraInput input);

    @Operation(summary = "Exclui um tipo por ID")
    public void remover(
            @Parameter(
                    description = "ID de um tipo",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long id);
}
